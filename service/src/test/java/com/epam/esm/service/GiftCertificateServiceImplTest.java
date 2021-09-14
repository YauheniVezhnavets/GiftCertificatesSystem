package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.mapper.GiftCertificateDtoMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;



public class GiftCertificateServiceImplTest {

    private static GiftCertificateDao giftCertificateDao;
    private static TagDao tagDao;
    private static GiftCertificateTagDao giftCertificateTagDao;
    private static GiftCertificateValidator giftCertificateValidator;
    private static GiftCertificateServiceImpl giftCertificateServiceImpl;
    private static GiftCertificateDtoMapper giftCertificateDtoWrapper;
    private static Tag testTag;
    private static GiftCertificate testGiftCertificate;
    private static Optional<Tag> optionalTag;
    private static Optional<GiftCertificate> optionalGiftCertificate;

    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    private static final Long TEST_ID = 1L;
    private static final Long TEST_CREATION_ID = 3L;

    private GiftCertificate TEST_GIFT_CERTIFICATE = new GiftCertificate(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal(30.00), 30,
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER), LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));

    private GiftCertificateDto TEST_GIFT_CERTIFICATE_DTO = new GiftCertificateDto(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal(30.00), 30,
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER), LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));

    private GiftCertificate TEST_GIFT_CERTIFICATE_NEW = new GiftCertificate(3L,
            "test", "test description", new BigDecimal(10.00), 10,
            LocalDateTime.now(), LocalDateTime.now());


    @BeforeAll
    public static void init() throws ResourceNotFoundException {
        giftCertificateDao = Mockito.mock(GiftCertificateDao.class);
        tagDao = Mockito.mock(TagDao.class);
        giftCertificateTagDao = Mockito.mock(GiftCertificateTagDao.class);
        giftCertificateValidator = Mockito.mock(GiftCertificateValidator.class);
        giftCertificateDtoWrapper = Mockito.mock(GiftCertificateDtoMapper.class);
        giftCertificateServiceImpl = new GiftCertificateServiceImpl(giftCertificateDao, tagDao,
                giftCertificateTagDao, giftCertificateValidator,giftCertificateDtoWrapper);
        testTag = Mockito.mock(Tag.class);
        testGiftCertificate = Mockito.mock(GiftCertificate.class);
        optionalTag = Optional.of(testTag);
        optionalGiftCertificate = Optional.of(testGiftCertificate);
    }

    @Test
    public void methodShouldReturnListOfGiftCertificates() {

        when(giftCertificateDao.getGiftCertificates(any(),any(),any(),any(),any())).thenReturn(new ArrayList<>());

        List<GiftCertificateDto> tags = giftCertificateServiceImpl.getGiftCertificates("Relax",
                null,null,null,null);

        assertNotNull(tags);
    }



    @Test
    public void methodShouldDeleteGiftCertificate() throws ResourceNotFoundException {

        when(giftCertificateDao.findById(anyLong())).thenReturn(optionalGiftCertificate);
        doNothing().when(giftCertificateDao).delete(anyLong());
        doNothing().when(giftCertificateTagDao)
                .unlinkAllTagsFromCertificateAndTagsByCertificateId(anyLong());

        giftCertificateServiceImpl.deleteGiftCertificate(1L);

        verify(giftCertificateDao, times(1)).delete(anyLong());
        verify(giftCertificateTagDao, times(1))
                .unlinkAllTagsFromCertificateAndTagsByCertificateId(anyLong());
    }


    @Test
    public void testShouldGetGiftCertificateById()  {

        Optional<GiftCertificate> giftCertificate = Optional.of(TEST_GIFT_CERTIFICATE);
        Mockito.when(giftCertificateDao.findById(TEST_ID)).thenReturn(giftCertificate);
        List<String> tags = Arrays.asList("Relax", "Music");
        TEST_GIFT_CERTIFICATE_DTO.setTags(tags);
        Mockito.when(giftCertificateDtoWrapper.map(giftCertificate.get())).thenReturn(TEST_GIFT_CERTIFICATE_DTO);

        GiftCertificateDto giftCertificateDtoActual = giftCertificateServiceImpl.getGiftCertificate(TEST_ID);

        assertEquals(TEST_GIFT_CERTIFICATE_DTO, giftCertificateDtoActual);

        Mockito.verify(giftCertificateDao).findById(TEST_ID);

    }
}
