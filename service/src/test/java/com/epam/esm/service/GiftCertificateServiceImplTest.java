package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.mapper.GiftCertificateDtoMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GiftCertificateServiceImplTest {

    private static Optional<Tag> optionalTag;
    private static Optional<GiftCertificate> optionalGiftCertificate;
    private static Tag testTag;
    private static GiftCertificate testGiftCertificate;
    private static GiftCertificateDto testGiftCertificateDto;


    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private TagDao tagDao;

    @Mock
    private GiftCertificateDtoMapper giftCertificateDtoMapper;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateServiceImpl;

    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    private static final Long TEST_ID = 1L;

    private GiftCertificate TEST_GIFT_CERTIFICATE = new GiftCertificate(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal(30.00), 30,
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER), LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));

    private Set <Tag> SET_OF_TUGS = Set.of(new Tag(1L,"Relax"), new Tag (2L, "Sport"));

    private GiftCertificateDto TEST_GIFT_CERTIFICATE_DTO = new GiftCertificateDto(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal(30.00), 30,
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER), LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));



    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
        testTag = Mockito.mock(Tag.class);
        optionalTag = Optional.of(testTag);
        testGiftCertificate = Mockito.mock(GiftCertificate.class);
        optionalGiftCertificate = Optional.of(testGiftCertificate);
        testGiftCertificateDto = Mockito.mock(GiftCertificateDto.class);

    }


    @Test
    public void methodShouldCreateGiftCertificateTest() {
        long id = 7L;
        when(giftCertificateDtoMapper.map(TEST_GIFT_CERTIFICATE_DTO)).thenReturn(TEST_GIFT_CERTIFICATE);
        when(giftCertificateDao.create(any())).thenReturn(7L);

        giftCertificateDao.create(TEST_GIFT_CERTIFICATE);

        verify(giftCertificateDao, times(1)).create(any());

    }

    @Test
    public void methodShouldDeleteGiftCertificate() throws ResourceNotFoundException {

        when(giftCertificateDao.findById(anyLong())).thenReturn(optionalGiftCertificate);
        doNothing().when(giftCertificateDao).delete(anyLong());

        giftCertificateServiceImpl.deleteGiftCertificate(1L);

        verify(giftCertificateDao, times(1)).delete(anyLong());

    }


    @Test
    public void testShouldFindGiftCertificateById()  {

        Optional<GiftCertificate> giftCertificate = Optional.of(TEST_GIFT_CERTIFICATE);
        when(giftCertificateDao.findById(TEST_ID)).thenReturn(giftCertificate);
        List<String> tags = Arrays.asList("Relax", "Music");
        TEST_GIFT_CERTIFICATE_DTO.setTags(tags);
        when(giftCertificateDtoMapper.mapToDto(giftCertificate.get())).thenReturn(TEST_GIFT_CERTIFICATE_DTO);

        GiftCertificateDto giftCertificateDtoActual = giftCertificateServiceImpl.findGiftCertificate(TEST_ID);

        assertEquals(TEST_GIFT_CERTIFICATE_DTO, giftCertificateDtoActual);

        verify(giftCertificateDao).findById(TEST_ID);

    }

    @Test
    public void testShouldUpdateGiftCertificateById()  {

        when(giftCertificateDao.findById(anyLong())).thenReturn(optionalGiftCertificate);
        when(giftCertificateDtoMapper.map(TEST_GIFT_CERTIFICATE_DTO)).thenReturn(TEST_GIFT_CERTIFICATE);
        doNothing().when(giftCertificateDao).update(testGiftCertificate);

        giftCertificateDao.update(TEST_GIFT_CERTIFICATE);

        verify(giftCertificateDao, times(1)).update(any());
    }
}
