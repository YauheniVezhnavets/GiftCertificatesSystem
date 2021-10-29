package com.epam.esm.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.UserDtoMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {


    private static User testUser;
    private static Optional<User> optionalUser;


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDtoMapper userDtoMapper;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
        testUser = Mockito.mock(User.class);
        optionalUser = Optional.of(testUser);
    }

    private final User expectedUser = new User (1L, "Ivan", "Ivanov", "ivan@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O");

    private final User newUser = new User (7L, "test", "testov", "test@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O");

    private final UserDto expectedUserDto = new UserDto (1L, "Ivan", "Ivanov", "ivan@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O");

    private final UserDto newUserDto = new UserDto (7L, "test", "testov", "test@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O");

    @Test
    public void methodShouldReturnUsers() {

        Pageable pageAndResultPerPage = PageRequest.of(0, 1);
        when(userRepository.findAll(pageAndResultPerPage)).thenReturn(new PageImpl<>(List.of(expectedUser)));
        when(userDtoMapper.mapToDto(expectedUser)).thenReturn(expectedUserDto);

        Page <UserDto> users = userServiceImpl.findUsers(0,1);

        assertNotNull(users);
    }

    @Test
    public void methodShouldReturnUser() throws ResourceNotFoundException {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expectedUser));
        when(userDtoMapper.mapToDto(expectedUser)).thenReturn(expectedUserDto);

        UserDto userDto = userServiceImpl.findUser(1L);

        assertEquals(userDto, expectedUserDto);
    }

    @Test
    public void methodShouldReturnUserByEmail(){

        String email = "ivan@mail.ru";

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(expectedUser));

        User actualUser = userServiceImpl.findUserByEmail(email);

        assertEquals(actualUser, expectedUser);
    }



    @Test
    public void methodShouldReturnExceptionWhenUserByIdNotFound(){
        String notExistingEmail = "notExist@mail.ru";

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> { userServiceImpl.findUserByEmail(notExistingEmail);});

        verify(userRepository).findByEmail(notExistingEmail);
    }

    @Test
    public void methodShouldCreateNewUser(){

        when(userDtoMapper.map(any())).thenReturn(newUser);
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(newUser)).thenReturn(any());

        userServiceImpl.registerUser(newUserDto);

        verify(userRepository).save(newUser);

    }

    @Test
    public void methodShouldReturnExceptionWhenUserByEmailNotFound(){
        long notExistingId = 1000;

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> { userServiceImpl.findUser(notExistingId);});

        verify(userRepository).findById(notExistingId);
    }
}
