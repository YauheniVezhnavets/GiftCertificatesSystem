package com.epam.esm.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.SecurityUser;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.impl.UserDetailServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDetailsServiceImplTest {

    private static User testUser;
    private static Optional<User> optionalUser;


    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserDetailServiceImpl userDetailServiceImpl;

    private final User expectedUser = new User (1L, "Ivan", "Ivanov", "ivan@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O", Role.USER,true);

    private final UserDto expectedUserDto = new UserDto (1L, "Ivan", "Ivanov", "ivan@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O",Role.USER,true);

    private final SecurityUser securityUser = new SecurityUser("ivan@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O", null, true);

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
        testUser = Mockito.mock(User.class);
        optionalUser = Optional.of(testUser);
    }

    @Test
    public void loadUserByUsernameTest(){
        String email = "ivan@mail.ru";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));
        when(userMapper.mapToSecurityUser(expectedUser)).thenReturn(securityUser);

        SecurityUser actualSecurityUser= (SecurityUser) userDetailServiceImpl.loadUserByUsername(email);

        assertNotNull(actualSecurityUser);
    }

    @Test
    public void methodShouldThrowExceptionWhenEmailNotFound() {

        String notExistEmail = "notExist@mail.ru";
        when(userRepository.findByEmail(notExistEmail)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {

           userDetailServiceImpl.loadUserByUsername(notExistEmail);

        });
    }
}
