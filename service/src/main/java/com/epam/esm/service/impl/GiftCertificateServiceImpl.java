package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.mapper.GiftCertificateDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService<GiftCertificate> {

    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateDtoMapper giftCertificateDtoMapper;


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao,
                                      GiftCertificateDtoMapper giftCertificateDtoMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateDtoMapper = giftCertificateDtoMapper;
    }


    @Override
    public List<GiftCertificateDto> getGiftCertificates(Set <String> tagsName, String giftCertificateName, String description,
                                                        String sortByName, String sortByDate, int currentPage) {
        Map <String,String> mapWithParameters = new HashMap<>();

        mapWithParameters.put("giftCertificateName", giftCertificateName);
        mapWithParameters.put("description", description);
        mapWithParameters.put("sortByName", sortByName);
        mapWithParameters.put("sortByDate", sortByDate);


        List<GiftCertificate> sortedGiftCertificates = giftCertificateDao.getGiftCertificates(mapWithParameters,
                tagsName, currentPage);

        return sortedGiftCertificates.stream().distinct().map(giftCertificateDtoMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto getGiftCertificate(long id) throws ResourceNotFoundException {

        GiftCertificate giftCertificate = giftCertificateDao.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(id));

        return giftCertificateDtoMapper.mapToDto(giftCertificate);
    }


    @Override
    @Transactional
    public void createGiftCertificate(GiftCertificateDto giftCertificateDto) throws InvalidFieldException {
        GiftCertificate giftCertificate = giftCertificateDtoMapper.map(giftCertificateDto);

        if(giftCertificate.getTags().isEmpty()){
            throw new InvalidFieldException();
        }
        Set<Tag> tags = createCertificateTagRelation(giftCertificate);
        giftCertificate.setTags(tags);

        giftCertificateDao.create(giftCertificate);
    }

    @Override
    @Transactional
    public void deleteGiftCertificate(long id) throws ResourceNotFoundException {

        if (giftCertificateDao.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        giftCertificateDao.delete(id);
    }

    @Override
    @Transactional
    public void updateGiftCertificate(long id, GiftCertificateDto giftCertificateDto) {

        GiftCertificate currentGiftCertificate = giftCertificateDao.findById(id).
                orElseThrow((() -> new ResourceNotFoundException(id)));

        GiftCertificate updatedGiftCertificate = giftCertificateDtoMapper.map(giftCertificateDto);
        System.out.println(updatedGiftCertificate);

        updateFieldsInGiftCertificate(currentGiftCertificate, updatedGiftCertificate);

        giftCertificateDao.update(currentGiftCertificate);
    }

    private void updateFieldsInGiftCertificate(GiftCertificate currentGiftCertificate, GiftCertificate updatedGiftCertificate) {
        if (updatedGiftCertificate.getName() != null) {
            currentGiftCertificate.setName(updatedGiftCertificate.getName());
        }
        if (updatedGiftCertificate.getDescription() != null) {
            currentGiftCertificate.setDescription(updatedGiftCertificate.getDescription());
        }
        if (updatedGiftCertificate.getPrice() != null) {
            currentGiftCertificate.setPrice(updatedGiftCertificate.getPrice());
        }
        if (updatedGiftCertificate.getDuration() > 0) {
            currentGiftCertificate.setDuration(updatedGiftCertificate.getDuration());
        }
        if (updatedGiftCertificate.getTags() != null) {
            Set<Tag> tags = createCertificateTagRelation(updatedGiftCertificate);
            currentGiftCertificate.setTags(tags);
        }
    }

    private Set<Tag> createCertificateTagRelation(GiftCertificate certificate) {
        Set<Tag> currentTags = certificate.getTags();
        Set<Tag> entityTags = new HashSet<>();
        for (Tag tag : currentTags) {
            Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
            if (optionalTag.isPresent()) {
                Tag existingTag = optionalTag.get();
                entityTags.add(existingTag);
            } else {
                Tag tagWithoutId = new Tag(tag.getName());
                entityTags.add(tagWithoutId);
            }
        }
        return entityTags;
    }
}
