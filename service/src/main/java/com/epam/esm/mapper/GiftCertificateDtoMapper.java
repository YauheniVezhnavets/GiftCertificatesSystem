package com.epam.esm.mapper;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GiftCertificateDtoMapper {

    public GiftCertificateDto map(GiftCertificate giftCertificate) {
        List <String> listOfTagsName = new ArrayList<>();
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
                giftCertificate.getCertificateId(), giftCertificate.getName(),
                giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration(),
               giftCertificate.getCreateDate(),giftCertificate.getLastUpdateDate());

        for (Tag tag : giftCertificate.getTags()) {
            listOfTagsName.add(tag.getName());
        }
        giftCertificateDto.setTags(listOfTagsName);
        return giftCertificateDto;
    }

    public GiftCertificate unmap(GiftCertificateDto giftCertificateDto) {

        GiftCertificate giftCertificate = new GiftCertificate(
                giftCertificateDto.getId(), giftCertificateDto.getName(),
                giftCertificateDto.getDescription(), giftCertificateDto.getPrice(), giftCertificateDto.getDuration(),
                giftCertificateDto.getCreateDate(),giftCertificateDto.getLastUpdateDate());

        Set<Tag> setOfTags = new HashSet<>();

        for (String tagName : giftCertificateDto.getTags()) {
            setOfTags.add(new Tag(tagName));
        }
        giftCertificate.setTags(setOfTags);
        return giftCertificate;
    }
}
