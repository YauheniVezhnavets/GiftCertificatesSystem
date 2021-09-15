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
import com.epam.esm.mapper.GiftCertificateDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService<GiftCertificate> {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateTagDao giftCertificateTagDao;
    private final GiftCertificateValidator giftCertificateValidator;
    private final GiftCertificateDtoMapper giftCertificateDtoWrapper;
    private final String TIME_ZONE = "GMT+3";


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao,
                                      GiftCertificateTagDao giftCertificateTagDao,
                                      GiftCertificateValidator giftCertificateValidator,
                                      GiftCertificateDtoMapper giftCertificateDtoMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.giftCertificateValidator = giftCertificateValidator;
        this.giftCertificateDtoWrapper = giftCertificateDtoMapper;
    }


    @Override
    public List<GiftCertificateDto> getGiftCertificates(String tagName, String giftCertificateName, String description,
                                                        String sortByName, String sortByDate) {
        List<GiftCertificate> sortedGiftCertificates = giftCertificateDao.getGiftCertificates(tagName,
                giftCertificateName, description, sortByName, sortByDate);

        for (GiftCertificate giftCertificate : sortedGiftCertificates){
            Set <Tag> tags = getTagsByGiftCertificateId(giftCertificate.getCertificateId());
            giftCertificate.setTags(tags);
        }

        return sortedGiftCertificates.stream().map(giftCertificateDtoWrapper::map).collect(Collectors.toList());

    }

    @Override
    public GiftCertificateDto getGiftCertificate(long id) throws ResourceNotFoundException {

        GiftCertificate giftCertificate = giftCertificateDao.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(id));

        return giftCertificateDtoWrapper.map(giftCertificate);
    }

    @Override
    @Transactional
    public void createGiftCertificate(GiftCertificateDto giftCertificateDto) throws InvalidFieldException {
        GiftCertificate giftCertificate = giftCertificateDtoWrapper.mapToDto(giftCertificateDto);

        if (!giftCertificateValidator.isGiftCertificateCreationFormValid(giftCertificate)) {
            throw new InvalidFieldException();
        }

        giftCertificate.setCreateDate(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        giftCertificate.setLastUpdateDate(LocalDateTime.now(ZoneId.of(TIME_ZONE)));

        checkForTags(giftCertificate.getTags());

        long lastAddedId = giftCertificateDao.create(giftCertificate);
        giftCertificateTagDao.linkAllTagsToCertificate(lastAddedId, giftCertificate.getTags());
    }

    @Override
    @Transactional
    public void updateGiftCertificate(long id, GiftCertificateDto giftCertificateDto) throws InvalidFieldException,
            ResourceNotFoundException {

        giftCertificateDao.findById(id).orElseThrow((() -> new ResourceNotFoundException(id)));

        GiftCertificate giftCertificate = giftCertificateDtoWrapper.mapToDto(giftCertificateDto);

        if (!giftCertificateValidator.isGiftCertificateCreationFormValid(giftCertificate)) {
            throw new InvalidFieldException();
        }

        giftCertificate.setLastUpdateDate(LocalDateTime.now(ZoneId.of(TIME_ZONE)));

        checkForTags(giftCertificate.getTags());

        if (giftCertificate.getTags().isEmpty()){
            giftCertificateTagDao.unlinkAllTagsFromCertificateAndTagsByCertificateId(id);
        }
        else {
            connectCertificatesAndTags(id, giftCertificate);
        }

        giftCertificateDao.update(id, giftCertificate);
    }


    @Override
    @Transactional
    public void deleteGiftCertificate(long id) throws ResourceNotFoundException {

        giftCertificateDao.findById(id).orElseThrow((() -> new ResourceNotFoundException(id)));

        giftCertificateTagDao.unlinkAllTagsFromCertificateAndTagsByCertificateId(id);
        giftCertificateDao.delete(id);
    }

    public Set <Tag> getTagsByGiftCertificateId(long id) throws ResourceNotFoundException {
        List <Tag> listOfTags = tagDao.getAllTagsConnectedWithCertificateId(id);
        return new HashSet<>(listOfTags);

    }

    private void checkForTags(Set <Tag> newTags) {
        List<String> newTagNames = newTags.stream().map(Tag::getName).collect(Collectors.toList());

        newTagNames.stream().filter(tagName -> tagDao.findByName(tagName).isEmpty())
                .forEach(tagName -> tagDao.create(new Tag(tagName)));
    }

    private void connectCertificatesAndTags(long id, GiftCertificate giftCertificate) {
        List<Long> tagIdsBeforeUpdate = giftCertificateTagDao.findAllTagIdsForCertificate(id);
        List<Long> tagIdsAfterUpdate = tagDao.getAllTagsIdConnectedWithGiftCertificate(giftCertificate.getTags());

        tagIdsAfterUpdate.stream().filter(tagId->!tagIdsBeforeUpdate.contains(tagId)).
                forEach(tagId -> giftCertificateTagDao.linkTagToCertificateById(id,tagId));

        tagIdsBeforeUpdate.stream().filter(tagId->!tagIdsAfterUpdate.contains(tagId)).
                forEach(tagId -> giftCertificateTagDao.unlinkTagFromCertificateById(id,tagId));

    }
}
