package com.epam.esm.repository;


import com.epam.esm.config.EntityManagerFactoryConfig;
import com.epam.esm.entity.GiftCertificate;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {GiftCertificateRepository.class, EntityManagerFactoryConfig.class},
        loader = AnnotationConfigContextLoader.class)
@DataJpaTest
public class GiftCertificateRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;


    @Test
    public void whenFindById_thenReturnGiftCertificate() {
        // given
        GiftCertificate giftCertificate = new GiftCertificate("test","testDescription",
                new BigDecimal("30.00"),30,LocalDateTime.now(),LocalDateTime.now());
        entityManager.persist(giftCertificate);


        // when
        GiftCertificate foundGiftCertificate = giftCertificateRepository.findById(giftCertificate.getCertificateId()).get();

        // then
        assertEquals(giftCertificate,foundGiftCertificate);
    }


    @Test
    @Transactional
    public void whenDelete_thenReturnNull() {
        // given
        GiftCertificate giftCertificate = new GiftCertificate("test","testDescription",
                new BigDecimal("30.00"),30,LocalDateTime.now(),LocalDateTime.now());
        entityManager.persist(giftCertificate);

        // when
        giftCertificateRepository.delete(giftCertificate);

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(giftCertificate.getCertificateId());

        // then
        assertEquals(Optional.empty(),optionalGiftCertificate);
    }
}