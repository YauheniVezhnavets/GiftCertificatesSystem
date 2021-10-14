package com.epam.esm.service.impl;

import com.epam.esm.entity.QGiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.mapper.GiftCertificateDtoMapper;
import com.epam.esm.utils.QPredicates;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.*;

import java.util.stream.Collectors;

@Service("giftCertificateService")
@AllArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService<GiftCertificate> {

    private final String NAME = "sortByName";
    private final String CREATE_DATE = "sortByDate";

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateDtoMapper giftCertificateDtoMapper;


    @Override
    public List<GiftCertificateDto> findAllGiftCertificates() {

        List<GiftCertificate> giftCertificates = giftCertificateRepository.findAll();
        List<GiftCertificate> activeGiftCertificates = checkForActiveGiftCertificates(giftCertificates);

        return activeGiftCertificates.stream()
                .map(giftCertificateDtoMapper::mapToDto).
                collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificates(Map<String, String> mapWithParameters,
                                                         int currentPage) {

        Predicate predicate = QPredicates.builder().add(mapWithParameters.get("giftCertificateName"),
                        QGiftCertificate.giftCertificate.name::containsIgnoreCase)
                .add(mapWithParameters.get("description"),
                        QGiftCertificate.giftCertificate.description::containsIgnoreCase)
                .buildAnd();

        Page<GiftCertificate> sortedGiftCertificates = giftCertificateRepository.findAll(predicate, Pageable.unpaged());

        List<GiftCertificate> activeGiftCertificates = new ArrayList<>();

        sortedGiftCertificates.stream().filter(GiftCertificate::isActive)
                .map(activeGiftCertificates::add).collect(Collectors.toList());

        return activeGiftCertificates.stream().map(giftCertificateDtoMapper::mapToDto).
                collect(Collectors.toList());

    }

    @Override
    public List<GiftCertificateDto> findGiftCertificatesByTags(Set<String> tagsName
                                                               //           , int currentPage
    ) {

        List<GiftCertificate> sortedGiftCertificates = giftCertificateRepository.findByTagsIn(tagsName, tagsName.size());

        List<GiftCertificate> activeGiftCertificates = checkForActiveGiftCertificates(sortedGiftCertificates);

        return activeGiftCertificates.stream().map(giftCertificateDtoMapper::mapToDto).
                collect(Collectors.toList());
    }


    public List<GiftCertificateDto> findGiftCertificatesAndSort(Map <String,String> sortNames
                                                               //           , int currentPage
    ) {
        List<Sort.Order> orders = new ArrayList<>();

        if(sortNames.containsKey(NAME)){
            if (sortNames.get(NAME).equalsIgnoreCase("ASC")){
                orders.add(new Sort.Order(Sort.Direction.ASC, "name"));
            }
            orders.add(new Sort.Order(Sort.Direction.DESC, "name"));
        }

        if(sortNames.containsKey(CREATE_DATE)){
            if (sortNames.get(CREATE_DATE).equalsIgnoreCase("ASC")){
                orders.add(new Sort.Order(Sort.Direction.ASC, "lastUpdateDate"));
            }
            orders.add(new Sort.Order(Sort.Direction.DESC, "lastUpdateDate"));
        }


       List <GiftCertificate> sortedGiftCertificates = giftCertificateRepository.findAll(Sort.by(orders));


        List<GiftCertificate> activeGiftCertificates = checkForActiveGiftCertificates(sortedGiftCertificates);

        return activeGiftCertificates.stream().map(giftCertificateDtoMapper::mapToDto).
                collect(Collectors.toList());
    }


    @Override
    public GiftCertificateDto findGiftCertificate(long id) throws ResourceNotFoundException {

        GiftCertificate giftCertificate = giftCertificateRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(id));

        if (!giftCertificate.isActive()) {
            throw new ResourceNotFoundException(id);
        }

        return giftCertificateDtoMapper.mapToDto(giftCertificate);
    }


    @Override
    @Transactional
    public void createGiftCertificate(GiftCertificateDto giftCertificateDto) throws InvalidFieldException {
        GiftCertificate giftCertificate = giftCertificateDtoMapper.map(giftCertificateDto);

        if (giftCertificate.getTags().isEmpty()) {
            throw new InvalidFieldException();
        }
        Set<Tag> tags = createCertificateTagRelation(giftCertificate);
        giftCertificate.setTags(tags);

        giftCertificateRepository.save(giftCertificate);
    }

    @Override
    @Transactional
    public void deleteGiftCertificate(long id) throws ResourceNotFoundException {

        if (giftCertificateRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id).get();
        giftCertificate.setActive(false);
    }

    @Override
    @Transactional
    public void updateGiftCertificate(long id, GiftCertificateDto giftCertificateDto) {

        GiftCertificate currentGiftCertificate = giftCertificateRepository.findById(id).
                orElseThrow((() -> new ResourceNotFoundException(id)));

        GiftCertificate updatedGiftCertificate = giftCertificateDtoMapper.map(giftCertificateDto);

        updateFieldsInGiftCertificate(currentGiftCertificate, updatedGiftCertificate);

        giftCertificateRepository.save(currentGiftCertificate);
    }

    private void updateFieldsInGiftCertificate(GiftCertificate currentGiftCertificate,
                                               GiftCertificate updatedGiftCertificate) {
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
            Optional<Tag> optionalTag = tagRepository.findByName(tag.getName());
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

    private List<GiftCertificate> checkForActiveGiftCertificates(List<GiftCertificate> giftCertificates) {
        List<GiftCertificate> activeGiftCertificates = new ArrayList<>();

        giftCertificates.stream().filter(GiftCertificate::isActive)
                .map(activeGiftCertificates::add).collect(Collectors.toList());
        return activeGiftCertificates;
    }
}
