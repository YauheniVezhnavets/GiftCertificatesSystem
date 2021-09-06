package com.epam.esm.wrapper;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class GiftCertificateDtoWrapper {

    public GiftCertificateDto wrap(GiftCertificate giftCertificate) {
        List<String> listOfTagsName = new ArrayList<>();
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
                giftCertificate.getCertificateId(), giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration(),
                giftCertificate.getCreateDate().toString(), giftCertificate.getLastUpdateDate().toString());
        for (Tag tag : giftCertificate.getTags()) {
            listOfTagsName.add(tag.getName());
        }
        giftCertificateDto.setTags(listOfTagsName);
        return giftCertificateDto;
    }

    public GiftCertificate unwrap(GiftCertificateDto giftCertificateDto) {

        GiftCertificate giftCertificate = new GiftCertificate(
                giftCertificateDto.getId(), giftCertificateDto.getName(),
                giftCertificateDto.getDescription(), giftCertificateDto.getPrice(), giftCertificateDto.getDuration(),
                giftCertificateDto.getCreateDate() != null ? ZonedDateTime.parse(giftCertificateDto.getCreateDate())
                        : null,
                giftCertificateDto.getLastUpdateDate() != null ? ZonedDateTime.parse(giftCertificateDto.getLastUpdateDate())
                        : null);

        List<Tag> listOfTags = new ArrayList<>();

        for (String tagName : giftCertificateDto.getTags()) {
            listOfTags.add(new Tag(tagName));
        }
        giftCertificate.setTags(listOfTags);
        return giftCertificate;
    }
}
