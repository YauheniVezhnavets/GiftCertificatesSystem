package com.epam.esm.repository;

import com.epam.esm.config.EntityManagerFactoryConfig;
import com.epam.esm.entity.Tag;
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

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TagRepository.class, EntityManagerFactoryConfig.class},
        loader = AnnotationConfigContextLoader.class)
@DataJpaTest
public class TagRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TagRepository tagRepository;


    @Test
    public void whenFindById_thenReturnTag() {
        // given
        Tag run = new Tag("Run");
        entityManager.persist(run);


        // when
        Tag foundTag = tagRepository.findById(run.getTagId()).get();

        // then
        assertEquals(run,foundTag);
    }


    @Test
    public void whenFindByName_thenReturnTag() {
        // given
        Tag relax = new Tag("Relax");
        entityManager.persist(relax);

        // when
        Tag foundTag = tagRepository.findByName(relax.getName()).get();

        // then
        assertEquals(relax,foundTag);
    }


    @Test
    @Transactional
    public void whenDelete_thenReturnNull() {
        // given
        Tag relax = new Tag("Relax");
        entityManager.persist(relax);

        // when
        tagRepository.delete(relax);

        Optional <Tag> optionalTag = tagRepository.findByName(relax.getName());

        // then
        assertEquals(Optional.empty(),optionalTag);
    }
}
