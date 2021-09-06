package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class GiftCertificateValidator {

    private final TagValidator tagValidator;

    private static final Pattern NAME_PATTERN = Pattern.compile("[_А-Яа-я\\w\\s]{3,50}");
    private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("[А-Яа-я\\w\\s\\p{Punct}]{5,100}");

    @Autowired
    public GiftCertificateValidator(TagValidator tagValidator) {
        this.tagValidator = tagValidator;
    }

    public boolean isGiftCertificateCreationFormValid(GiftCertificate giftCertificate) {
        return (isNameValid(giftCertificate.getName()) && isDescriptionValid(giftCertificate.getDescription()) &&
                isPriceValid(giftCertificate.getPrice()) && isDurationValid(giftCertificate.getDuration()) &&
                giftCertificate.getCreateDate() == null && giftCertificate.getLastUpdateDate() == null &&
                areGiftCertificateTagsValidForCreation(giftCertificate.getTags()));
    }

    public boolean isNameValid(String name) {
        return (name != null && NAME_PATTERN.matcher(name).matches());
    }

    public boolean isDescriptionValid(String description) {
        return (description != null && DESCRIPTION_PATTERN.matcher(description).matches());
    }

    public boolean isPriceValid(BigDecimal price) {
        return (price != null && price.compareTo(BigDecimal.ZERO) > 0);
    }

    public boolean isDurationValid(int duration) {
        return duration > 0;
    }

    public boolean areGiftCertificateTagsValidForCreation(List<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return true;
        }
        return tags.stream()
                .allMatch(tag -> tagValidator.isNameValid(tag));
    }

    public boolean areGiftCertificateTagsValid(List<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return false;
        }
        return tags.stream()
                .allMatch(tag -> tagValidator.isNameValid(tag));
    }
}
