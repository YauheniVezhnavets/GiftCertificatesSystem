package com.epam.esm.service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entities.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {


    private static User testUser;
    private static Optional<User> optionalUser;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
        testUser = Mockito.mock(User.class);
        optionalUser = Optional.of(testUser);
    }


    @Test
    public void methodShouldReturnListOfUsers() {

        when(userDao.findAll(1)).thenReturn(new ArrayList<>());

        List<User> users = userServiceImpl.findUsers(1);

        assertNotNull(users);
    }

    @Test
    public void methodShouldReturnUser() throws ResourceNotFoundException {

        when(userDao.findById(anyLong())).thenReturn(optionalUser);

        User user= userServiceImpl.findUser(1L);

        assertNotNull(user);
    }
}
