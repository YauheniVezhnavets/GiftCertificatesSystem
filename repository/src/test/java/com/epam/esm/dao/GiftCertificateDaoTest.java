package com.epam.esm.dao;

import com.epam.esm.config.DataSourceConfig;
import com.epam.esm.constructor.QueryConstructor;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.mappers.GiftCertificateMapper;
import org.junit.jupiter.api.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GiftCertificateDaoTest {

    private static final String SELECT_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?";

    private static JdbcTemplate jdbcTemplate;
    private static GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapper();
    private GiftCertificateDao giftCertificateDao;
    private final QueryConstructor queryConstructor = new QueryConstructor();

    private GiftCertificate TEST_GIFT_CERTIFICATE_FIRST = new GiftCertificate(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal(30.00), 30,
            LocalDateTime.parse("2021-08-31T03:12:15.156"), LocalDateTime.parse("2021-08-31T03:12:15.156"));

    private GiftCertificate TEST_GIFT_CERTIFICATE_SECOND = new GiftCertificate(2L, "Bate",
            "You can visit all home matches in season ", new BigDecimal("100"), 270,
           LocalDateTime.parse( "2021-08-30T03:12:15.156Z"),LocalDateTime.parse("2021-08-30T03:12:15.156Z"));
    private Set<Tag> TEST_LIST_OF_TAGS = Set.of(new Tag (1L,"Relax"),
            new Tag(2L,"Music"));

    @BeforeEach
    public void initDatabase() {
        jdbcTemplate = new JdbcTemplate(DataSourceConfig.dataSource);
        giftCertificateDao = new GiftCertificateDao(jdbcTemplate, giftCertificateMapper, queryConstructor);
    }

    @Test
    @Order(1)
    public void methodShouldReturnAllCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificateDao.getGiftCertificates(null, null, null, null, null);

        assertNotNull(giftCertificates);
    }

    @Test
    @Order(2)
    public void methodShouldReturnTrueWhenGiftCertificateIdCorrect() {
        TEST_GIFT_CERTIFICATE_FIRST.setTags(Set.of(new Tag(1L, "Relax"), new Tag(2L, "Music")));
        Optional<GiftCertificate> actual = giftCertificateDao.findById(1L);
        assertEquals(TEST_GIFT_CERTIFICATE_FIRST, actual.get());

    }

    @Test
    @Order(3)
    public void methodShouldReturnEmptyGiftCertificateWhenTagIdIncorrect() {
        TEST_GIFT_CERTIFICATE_FIRST.setTags(TEST_LIST_OF_TAGS);
        Optional<GiftCertificate> expected = Optional.empty();
        Optional<GiftCertificate> actual = giftCertificateDao.findById(100000L);
        assertEquals(expected, actual);
    }


    @Test
    @Order(4)
    public void methodShouldCreateNewGiftCertificate() {

        GiftCertificate testGiftCertificate = new GiftCertificate("test","testDescription",
                new BigDecimal(30),100, null, null);
        Long createdId = giftCertificateDao.create(testGiftCertificate);

        GiftCertificate actual = jdbcTemplate.query(SELECT_CERTIFICATE_BY_ID,
                        new BeanPropertyRowMapper<>(GiftCertificate.class),new Object[]{createdId})
                .stream().findAny().orElse(null);

        Assertions.assertEquals(testGiftCertificate, actual);
    }

    @Test
    @Order(5)
    public void methodShouldReturnEmptyWhenDeleteTag() {

        Long id = 1L;

        giftCertificateDao.delete(id);
        GiftCertificate actual = jdbcTemplate.query(SELECT_CERTIFICATE_BY_ID,
                        new BeanPropertyRowMapper<>(GiftCertificate.class),new Object[]{id}).
                stream().findAny().orElse(null);

        assertNull(actual);
    }

    @Test
    @Order(6)
    public void methodShouldUpdateGiftCertificateWhenDeleteTag() {

        Long id = 1L;

        GiftCertificate testGiftCertificate = new GiftCertificate(0L,"test","testDescription",
                new BigDecimal(30),100, null, null);

        giftCertificateDao.update( id,testGiftCertificate);

        GiftCertificate actual = jdbcTemplate.query(SELECT_CERTIFICATE_BY_ID,
                        new BeanPropertyRowMapper<>(GiftCertificate.class), new Object[]{id})
                .stream().findAny().orElse(null);

        Assertions.assertEquals(testGiftCertificate, actual);
    }
}