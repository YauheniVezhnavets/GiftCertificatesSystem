package com.epam.esm.dao;


import com.epam.esm.config.EntityManagerFactoryConfig;
import com.epam.esm.constructor.QueryConstructor;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Order;
import com.epam.esm.entities.Tag;
import com.epam.esm.entities.User;
import com.epam.esm.utils.Paginator;
import org.junit.jupiter.api.BeforeEach;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@ExtendWith(SpringExtension.class)
@DirtiesContext
@ContextConfiguration(classes = {GiftCertificateDao.class, Paginator.class, QueryConstructor.class, User.class,
        Order.class,Tag.class,
        EntityManagerFactoryConfig.class},
        loader = AnnotationConfigContextLoader.class)
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class GiftCertificateDaoTest {

    private GiftCertificate certificate;


    @Autowired
    private GiftCertificateDao giftCertificateDao;


    @BeforeEach
    public void init() {
        GiftCertificate certificate = new GiftCertificate(1L, "Golden_Coffee",
                LocalDateTime.of(2021,8,31,6,12,15));
        certificate.setDescription("Taste our best coffee.");
        certificate.setPrice(new BigDecimal("30.00"));
        certificate.setDuration(30);
        certificate.setLastUpdateDate(LocalDateTime.of(2021,8,31,6,12,15));
        certificate.setTags(Set.of(new Tag(1L, "Relax"),new Tag (5L, "Coffee")));
        this.certificate = certificate;
    }

    @Test
    public void findById() {
        Optional<GiftCertificate> expected = Optional.of(certificate);
        Optional<GiftCertificate> actual = giftCertificateDao.findById(1L);
        assertEquals(expected, actual);
    }


    @Test
    public void deleteTagTest() {
        Optional<GiftCertificate> createdGiftCertificate = giftCertificateDao.findById(6L);
        giftCertificateDao.delete(createdGiftCertificate.get());
        Optional<GiftCertificate> emptyGiftCertificate = giftCertificateDao.findById(6L);
        assertNotEquals(createdGiftCertificate, emptyGiftCertificate);
    }
}