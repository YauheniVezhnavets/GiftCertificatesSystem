package com.epam.esm.service;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
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

import org.junit.jupiter.api.TestInstance;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GiftCertificateServiceImplTest {

    private static Optional<Tag> optionalTag;
    private static Optional<GiftCertificate> optionalGiftCertificate;
    private static Tag testTag;
    private static GiftCertificate testGiftCertificate;
    private static GiftCertificateDto testGiftCertificateDto;


    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private GiftCertificateDtoMapper giftCertificateDtoMapper;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateServiceImpl;

    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    private static final Long TEST_ID = 1L;

    private GiftCertificate TEST_GIFT_CERTIFICATE = new GiftCertificate(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal(30.00), 30,
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER),
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));

    private Set <Tag> SET_OF_TUGS = Set.of(new Tag(1L,"Relax", true), new Tag (2L, "Sport",true));

    private GiftCertificateDto TEST_GIFT_CERTIFICATE_DTO = new GiftCertificateDto(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal(30.00), 30,
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER),
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));



    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
        testTag = Mockito.mock(Tag.class);
        optionalTag = Optional.of(testTag);
        testGiftCertificate = Mockito.mock(GiftCertificate.class);
        optionalGiftCertificate = Optional.of(testGiftCertificate);
        testGiftCertificateDto = Mockito.mock(GiftCertificateDto.class);

    }
//
//
////    @Test
//    public void methodShouldCreateGiftCertificateWithNewTagOfGiftCertificateTest() {
//        long id = 7L;
//        when(giftCertificateDtoMapper.map(TEST_GIFT_CERTIFICATE_DTO)).thenReturn(TEST_GIFT_CERTIFICATE);
//        TEST_GIFT_CERTIFICATE.setTags(SET_OF_TUGS);
//        when(tagRepository.findByName(anyString())).thenReturn(Optional.empty());
//
//        assertFalse(TEST_GIFT_CERTIFICATE.getTags().isEmpty());
//
//   //     when(giftCertificateRepository.save(TEST_GIFT_CERTIFICATE)).thenReturn(id);
//
//        giftCertificateServiceImpl.createGiftCertificate(TEST_GIFT_CERTIFICATE_DTO);
//
//    }
//
//
//    @Test
//    public void methodShouldCreateGiftCertificateWithExistingTagOfGiftCertificateTest() {
//        long id = 7L;
//        when(giftCertificateDtoMapper.map(TEST_GIFT_CERTIFICATE_DTO)).thenReturn(TEST_GIFT_CERTIFICATE);
//        TEST_GIFT_CERTIFICATE.setTags(SET_OF_TUGS);
//        when(tagRepository.findByName(anyString())).thenReturn(optionalTag);
//
//        assertFalse(TEST_GIFT_CERTIFICATE.getTags().isEmpty());
//
//   //     when(giftCertificateRepository.save(TEST_GIFT_CERTIFICATE)).thenReturn(id);
//
//        giftCertificateServiceImpl.createGiftCertificate(TEST_GIFT_CERTIFICATE_DTO);
//
//        verify(giftCertificateRepository, times(1)).save(TEST_GIFT_CERTIFICATE);
//
//    }
//
//    @Test
//    public void methodShouldDeleteGiftCertificate() throws ResourceNotFoundException {
//
//        doNothing().when(giftCertificateRepository).delete(optionalGiftCertificate.get());
//
//        giftCertificateServiceImpl.deleteGiftCertificate(1L);
//
//        verify(giftCertificateRepository, times(1)).delete(optionalGiftCertificate.get());
//
//    }
//
//
    @Test
    public void testShouldFindGiftCertificateById()  {

        Optional<GiftCertificate> giftCertificate = Optional.of(TEST_GIFT_CERTIFICATE);
        when(giftCertificateRepository.findById(TEST_ID)).thenReturn(giftCertificate);
        List<String> tags = Arrays.asList("Relax", "Music");
        TEST_GIFT_CERTIFICATE_DTO.setTags(tags);
        when(giftCertificateDtoMapper.mapToDto(giftCertificate.get())).thenReturn(TEST_GIFT_CERTIFICATE_DTO);

        GiftCertificateDto giftCertificateDtoActual = giftCertificateServiceImpl.findGiftCertificate(TEST_ID);

        assertEquals(TEST_GIFT_CERTIFICATE_DTO, giftCertificateDtoActual);

        verify(giftCertificateRepository).findById(TEST_ID);

    }
//
//    @Test
//    public void testShouldUpdateGiftCertificateById()  {
//
//        when(giftCertificateRepository.findById(anyLong())).thenReturn(optionalGiftCertificate);
//        when(giftCertificateDtoMapper.map(TEST_GIFT_CERTIFICATE_DTO)).thenReturn(TEST_GIFT_CERTIFICATE);
//        doNothing().when(giftCertificateRepository).save(testGiftCertificate);
//
//        giftCertificateServiceImpl.updateGiftCertificate(1L, TEST_GIFT_CERTIFICATE_DTO);
//
//        verify(giftCertificateRepository, times(1)).save(any());
//    }
//
//    @Test
//    public void methodShouldReturnExceptionWhenIdNotFound()  {
//        when(giftCertificateRepository.findById(0L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//
//            giftCertificateServiceImpl.findGiftCertificate(0L);
//        });
//    }
}
