package com.epam.esm.dao;

import com.epam.esm.config.EntityManagerFactoryConfig;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.entities.User;
import com.epam.esm.utils.Paginator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DirtiesContext
@ContextConfiguration(classes = {UserDao.class, Paginator.class, EntityManagerFactoryConfig.class},
        loader = AnnotationConfigContextLoader.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")

public class UserDaoTest {

    private List<User> EXPECTED_USER = List.of(
            new User(1L, "Ivan", "Ivanov", "ivan@mail.ru", "ivan"),
            new User(2L, "Petr", "Petrov", "petr@mail.ru", "petr"),
            new User(3L, "Semen", "Semenov", "semen@mail.ru", "semen"),
            new User(4L, "Andrey", "Andreev", "andrey@mail.ru", "andrey"),
            new User(5L, "Nikolay", "Nikolaev", "nikolay@mail.ru", "nikolay"));

    @Autowired
    private UserDao userDao;


    @Test
    public void findById() {
        Optional<User> expected = Optional.of(new User(1L, "Ivan", "Ivanov", "ivan@mail.ru", "ivan"));
        Optional<User> actual = userDao.findById(1L);
        assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
        List<User> actual = userDao.findAll(1);
        assertEquals(EXPECTED_USER, actual);
    }
}
