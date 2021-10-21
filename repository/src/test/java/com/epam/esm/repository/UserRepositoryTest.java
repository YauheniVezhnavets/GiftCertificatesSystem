package com.epam.esm.repository;

import com.epam.esm.config.EntityManagerFactoryConfig;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserRepository.class, EntityManagerFactoryConfig.class},
        loader = AnnotationConfigContextLoader.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void whenFindById_thenReturnUser() {
        // given
        User user = new User( "Ivan", "Ivanov",
                "ivan@mail.ru", "ivan");
        entityManager.persist(user);
    //    entityManager.flush();

        // when
        User foundUser = userRepository.findById(user.getUserId()).get();

        // then
        assertEquals(user,foundUser);
    }


    @Test
    @Transactional
    public void whenDelete_thenReturnNull() {
        // given
        User user = new User( "Ivan", "Ivanov",
                "ivan@mail.ru", "ivan");
        entityManager.persist(user);

        // when
        userRepository.delete(user);
//
        Optional<User> optionalUser = userRepository.findById(user.getUserId());

        // then
        assertEquals(Optional.empty(),optionalUser);
    }
}
