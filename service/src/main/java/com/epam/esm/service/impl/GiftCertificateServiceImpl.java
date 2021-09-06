package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.wrapper.GiftCertificateDtoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService<GiftCertificate> {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateTagDao giftCertificateTagDao;
    private final GiftCertificateValidator giftCertificateValidator;
    private final GiftCertificateDtoWrapper giftCertificateDtoWrapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao,
                                      GiftCertificateTagDao giftCertificateTagDao,
                                      GiftCertificateValidator giftCertificateValidator,
                                      GiftCertificateDtoWrapper giftCertificateDtoWrapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.giftCertificateValidator = giftCertificateValidator;
        this.giftCertificateDtoWrapper = giftCertificateDtoWrapper;
    }


    @Override
    public List<GiftCertificateDto> getGiftCertificates(String tagName, String giftCertificateName, String description,
                                                        String sortByName, String sortByDate) {
        List<GiftCertificate> sortedGiftCertificates = giftCertificateDao.getGiftCertificates(tagName,
                giftCertificateName, description, sortByName, sortByDate);

        List<GiftCertificateDto> sortedDtoGiftCertificates = new ArrayList<>();

        sortedGiftCertificates.forEach(giftCertificate ->
                sortedDtoGiftCertificates.add(giftCertificateDtoWrapper.wrap(giftCertificate)));
        return sortedDtoGiftCertificates;
    }

    @Override
    public GiftCertificateDto getGiftCertificate(long id) throws ResourceNotFoundException {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(id);
        if (optionalGiftCertificate.isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        return giftCertificateDtoWrapper.wrap(optionalGiftCertificate.get());
    }

    @Override
    @Transactional
    public void createGiftCertificate(GiftCertificateDto giftCertificateDto) throws InvalidFieldException {
        GiftCertificate giftCertificate = giftCertificateDtoWrapper.unwrap(giftCertificateDto);

        if (!giftCertificateValidator.isGiftCertificateCreationFormValid(giftCertificate)) {
            throw new InvalidFieldException();
        }

        giftCertificate.setCreateDate(ZonedDateTime.now());
        giftCertificate.setLastUpdateDate(ZonedDateTime.now());

        checkForTags(giftCertificate.getTags());

        long lastAddedId = giftCertificateDao.create(giftCertificate);
        giftCertificateTagDao.createConnections(lastAddedId, giftCertificate.getTags());
    }

    @Override
    @Transactional
    public void updateGiftCertificate(long id, GiftCertificateDto giftCertificateDto) throws InvalidFieldException,
            ResourceNotFoundException {

        if (giftCertificateDao.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(id);
        }

        GiftCertificate giftCertificate = giftCertificateDtoWrapper.unwrap(giftCertificateDto);

        if (!giftCertificateValidator.isGiftCertificateCreationFormValid(giftCertificate)) {
            throw new InvalidFieldException();
        }

        giftCertificate.setLastUpdateDate(ZonedDateTime.now());

        checkForTags(giftCertificate.getTags());

        if (giftCertificate.getTags().isEmpty()){
            giftCertificateTagDao.deleteConnectionBetweenGiftCertificateAndTagsByGiftCertificateId(id);
        }
        else {
            connectCertificatesAndTags(id, giftCertificate);
        }

        giftCertificateDao.update(id, giftCertificate);
    }

    @Override
    @Transactional
    public void deleteGiftCertificate(long id) throws ResourceNotFoundException {
        if (giftCertificateDao.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        giftCertificateTagDao.deleteConnectionBetweenGiftCertificateAndTagsByGiftCertificateId(id);
        giftCertificateDao.delete(id);
    }

    private void checkForTags(List<Tag> newTags) {
        List<String> newTagNames = new ArrayList<>();

        newTags.stream().forEach(tag -> newTagNames.add(tag.getName()));
        newTagNames.stream().filter(tagName -> tagDao.findByName(tagName).isEmpty())
                .forEach(tagName -> tagDao.create(new Tag(tagName)));
    }

    private void connectCertificatesAndTags(long id, GiftCertificate giftCertificate) {
        List<Long> tagIdsBeforeUpdate = giftCertificateTagDao.getAllTagIdConnectedWithCertificateId(id);
        List<Long> tagIdsAfterUpdate = tagDao.getAllTagsIdConnectedWithGiftCertificate(giftCertificate.getTags());


        for (Long tagId : tagIdsAfterUpdate){
            if(!tagIdsBeforeUpdate.contains(tagId))
            giftCertificateTagDao.addTagId(id, tagId);
        }

        for (Long tagId : tagIdsBeforeUpdate){
            if(!tagIdsAfterUpdate.contains(tagId))
            giftCertificateTagDao.deleteTagId(id, tagId);
        }
    }
}
