package com.epam.esm.service.impl;


import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.TagDtoMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("tagService")
@AllArgsConstructor
public class TagServiceImpl implements TagService<Tag> {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final TagDtoMapper tagDtoMapper;



    @Override
    public Page <TagDto> findTags(int currentPage, int pageSize) {
        Pageable pageAndResultPerPage = PageRequest.of(currentPage, pageSize);
        Page <Tag> tags = tagRepository.findAll(pageAndResultPerPage);
        List<Tag> activeTags = new ArrayList<>();

        tags.stream().filter(Tag::isActive).map(activeTags::add).collect(Collectors.toList());

        List<TagDto> activeDtoTags = activeTags.stream()
                .map(tagDtoMapper::mapToDto).
                collect(Collectors.toList());

        return new PageImpl<>(activeDtoTags);
    }

    @Override
    public TagDto findTag(long id) throws ResourceNotFoundException {

        Tag tag = tagRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(id, "Tag with this id not found"));

        if (!tag.isActive()){
            throw new ResourceNotFoundException(id, "Tag with this id not active");
        }
        return tagDtoMapper.mapToDto(tag);
    }


    @Override
    @Transactional
    public void createTag(TagDto tagDto) throws InvalidFieldException, ResourceDuplicateException {

        Tag tag = tagDtoMapper.map(tagDto);

        Optional<Tag> optionalTag = tagRepository.findByName(tag.getName());
        if (optionalTag.isEmpty()){
            tagRepository.save(tag);
        }
        if (optionalTag.isPresent()) {
            if (optionalTag.get().isActive()) {
                throw new ResourceDuplicateException();
            } else {
                optionalTag.get().setActive(true);
                tagRepository.save(optionalTag.get());
            }
        }
    }

    @Override
    @Transactional
    public void deleteTag(long id) throws ResourceNotFoundException {
        if (tagRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(id, "Tag with this id not found");
        }
        Tag tag = tagRepository.findById(id).get();
        tag.setActive(false);
    }

    @Override
    public TagDto findMostUsedTagOfUserWithHighestCostOfAllOrders(long userId) throws ResourceNotFoundException {

        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException(userId, "User with this id not found");
        }

        Optional<Tag> optionalTag = tagRepository.findMostUsedTagOfUserWithHighestCostOfAllOrders(userId);
        if (optionalTag.isEmpty()) {
            throw new ResourceNotFoundException(optionalTag.get().getTagId(), "Tag not found");
        }
        return tagDtoMapper.mapToDto(optionalTag.get());
    }
}
