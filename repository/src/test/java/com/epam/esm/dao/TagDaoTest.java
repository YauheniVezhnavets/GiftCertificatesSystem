package com.epam.esm.dao;

import com.epam.esm.config.EntityManagerFactoryConfig;
import com.epam.esm.entities.Tag;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DirtiesContext
@ContextConfiguration(classes = { TagDao.class, Paginator.class, EntityManagerFactoryConfig.class},
        loader = AnnotationConfigContextLoader.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class TagDaoTest {

    private List <Tag> EXPECTED_TAGS = List.of(
            new Tag (1L,"Relax"),
            new Tag (2L,"Music"),
            new Tag (3L,"Movie"),
            new Tag (4L,"Shopping"),
            new Tag (5L,"Coffee"),
            new Tag (6L,"Sport"),
            new Tag (7L,"Run"),
            new Tag (8L,"Test"),
            new Tag (9L,"Football"),
            new Tag (10L,"Hockey"));

    @Autowired
    private TagDao tagDao;


    @Test
    public void findByNameTest() {
          Optional<Tag> expected = Optional.of(new Tag(1L,"Relax"));
          Optional<Tag> actual = tagDao.findByName("Relax");
          assertEquals(expected, actual);
    }

    @Test
    public void findById() {
        Optional<Tag> expected = Optional.of(new Tag(1L,"Relax"));
        Optional<Tag> actual = tagDao.findById(1L);
        assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
        List <Tag> actual = tagDao.findAll(1);
        assertEquals(EXPECTED_TAGS, actual);
    }

    @Test
    public void createTagTest() {
        long expected = 11;
        long actual = tagDao.create(new Tag("Run"));
        assertEquals(expected, actual);
    }

    @Test
    public void deleteTagTest() {
        Optional <Tag> createdTag = tagDao.findById(6L);
        tagDao.delete(createdTag.get());
        Optional <Tag> emptyTag = tagDao.findById(6L);
        assertNotEquals(createdTag,emptyTag);
    }
}
