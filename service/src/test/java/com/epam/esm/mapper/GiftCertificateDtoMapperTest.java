package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GiftCertificateDtoMapperTest {

    GiftCertificateDtoMapper giftCertificateDtoMapper = new GiftCertificateDtoMapper();

    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    private GiftCertificate giftCertificate = new GiftCertificate(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal("30.00"), 30,
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER),
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));

    private GiftCertificateDto giftCertificateDto = new GiftCertificateDto(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal("30.00"), 30,
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER),
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));

    private GiftCertificateDto expectedGiftCertificateDto = new GiftCertificateDto(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal("30.00"), 30,
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER),
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));

    private GiftCertificateDto invalidGiftCertificateDto = new GiftCertificateDto(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal("50.00"), 30,
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER),
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));

    private GiftCertificate invalidGiftCertificate = new GiftCertificate(1L,
            "Golden_Coffee", "Taste our best coffee.", new BigDecimal("50.00"), 30,
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER),
            LocalDateTime.parse("2021-08-31T06:12:15.156Z", FORMATTER));

    private Set<Tag> tags = Set.of(new Tag("Relax"));
    private List<String> listOfStringTags = List.of("Relax");


    @Test
    void methodShouldMapGiftCertificateToGiftCertificateDto() {

        giftCertificate.setTags(tags);
        GiftCertificateDto actualGiftCertificateDto = giftCertificateDtoMapper.mapToDto(giftCertificate);
        expectedGiftCertificateDto.setTags(listOfStringTags);
        assertEquals(expectedGiftCertificateDto, actualGiftCertificateDto);
    }

    @Test
    void methodShouldMapGiftCertificateToGiftCertificateDtoNotEquals() {

        giftCertificate.setTags(tags);
        GiftCertificateDto actualGiftCertificateDto = giftCertificateDtoMapper.mapToDto(giftCertificate);
        invalidGiftCertificateDto.setTags(listOfStringTags);
        assertNotEquals(invalidGiftCertificateDto, actualGiftCertificateDto);
    }


    @Test
    void methodShouldMapGiftCertificateDtoToGiftCertificateNotEquals() {

        giftCertificateDto.setTags(listOfStringTags);
        GiftCertificate actualGiftCertificate = giftCertificateDtoMapper.map(giftCertificateDto);
        invalidGiftCertificate.setTags(tags);
        assertNotEquals(invalidGiftCertificate, actualGiftCertificate);
    }
}
