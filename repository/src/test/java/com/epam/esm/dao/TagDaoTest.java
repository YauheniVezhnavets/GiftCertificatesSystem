package com.epam.esm.dao;

//import com.epam.esm.config.DataSourceConfig;
//import com.epam.esm.entities.Tag;
//import org.junit.jupiter.api.*;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.*;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TagDaoTest {

//    private static final String SELECT_BY_ID = "SELECT * FROM tag WHERE id = ";
//    private static final String SELECT_BY_SPECIFIC_ID = "SELECT * FROM tag WHERE id = 1";

//    private static JdbcTemplate jdbcTemplate;
//    private static TagMapper tagMapper = new TagMapper();
//    private TagDao tagDao;
//
//    @BeforeEach
//    public void initDatabase() {
//        jdbcTemplate = new JdbcTemplate(DataSourceConfig.dataSource);
//        tagDao = new TagDao(jdbcTemplate, tagMapper);
//    }
//
//    @Test
//    @Order(1)
//    public void methodShouldReturnAllTags() {
//
//        List <Tag> expected = List.of(new Tag(1L,"Relax"),new Tag(2L,"Music"));
//        List <Tag> actual = tagDao.findAll();
//        assertEquals(expected, actual);
//    }
//
//
//    @Test
//    @Order(2)
//    public void methodShouldReturnTagWhenTagNameCorrect() {
//        Optional<Tag> expected = Optional.of(new Tag("Relax"));
//        Optional<Tag> actual = tagDao.findByName("Relax");
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Order(3)
//    public void methodShouldReturnEmptyWhenTagIdIncorrect() {
//        Optional<Tag> expected = Optional.empty();
//        Optional<Tag> actual = tagDao.findById(10000L);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Order(4)
//    public void methodShouldReturnTagWhenTagIdCorrect() {
//        Optional<Tag> expected = Optional.of(new Tag(1L, "Relax"));
//        Optional<Tag> actual = tagDao.findById(1L);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Order(5)
//    public void methodShouldReturnAllIdThatRelatedToCertificate() {
//        Set <Tag> tags = Set.of(new Tag(1L, "Relax"), new Tag(2L, "Music"));
//        List <Long> expected = List.of(1L, 2L);
//        List <Long> actual = tagDao.getAllTagsIdConnectedWithGiftCertificate(tags);
//        Collections.sort(actual);
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @Order(6)
//    public void methodShouldCreateNewTag() {
//
//        Tag test = new Tag("Funny");
//        Long createdId = tagDao.create(test);
//
//        Tag actual = jdbcTemplate.query(SELECT_BY_ID + createdId, tagMapper)
//                .stream().findAny().orElse(null);
//
//        Assertions.assertEquals(test, actual);
//    }
//
//    @Test
//    @Order(7)
//    public void methodShouldDeleteTag() {
//
//        Long id = 1L;
//
//        tagDao.delete(id);
//        Tag actual = jdbcTemplate.query(SELECT_BY_SPECIFIC_ID, tagMapper)
//                .stream().findAny().orElse(null);
//
//        assertNull(actual);
//    }
}
