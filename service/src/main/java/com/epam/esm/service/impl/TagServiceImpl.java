package com.epam.esm.service.impl;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.TagService;
import lombok.AllArgsConstructor;
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



    @Override
    public List<Tag> findTags(int currentPage) {
        List<Tag> tags = tagRepository.findAll();
        List<Tag> activeTags = new ArrayList<>();

        tags.stream().filter(Tag::isActive).map(activeTags::add).collect(Collectors.toList());

        return activeTags;
    }

    @Override
    public Tag findTag(long id) throws ResourceNotFoundException {

        Tag tag = tagRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(id));

        if (!tag.isActive()){
            throw new ResourceNotFoundException(id);
        }
        return tag;
    }


    @Override
    @Transactional
    public void createTag(Tag tag) throws InvalidFieldException, ResourceDuplicateException {

        Optional<Tag> optionalTag = tagRepository.findByName(tag.getName());
        if (optionalTag.isEmpty()){
            tagRepository.save(tag);
        }
        if (optionalTag.isPresent()) {
            if (optionalTag.get().isActive()) {
                throw new ResourceDuplicateException();
            } else {
                optionalTag.get().setActive(true);
            }
        }
        tagRepository.save(optionalTag.get());
    }

    @Override
    @Transactional
    public void deleteTag(long id) throws ResourceNotFoundException {
        if (tagRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        Tag tag = tagRepository.findById(id).get();
        tag.setActive(false);
    }

    @Override
    public Tag findMostUsedTagOfUserWithHighestCostOfAllOrders(long userId) throws ResourceNotFoundException {

        if (userRepository.findById(userId).isEmpty()) {
            throw new ResourceNotFoundException(userId);
        }

        Optional<Tag> optionalTag = tagRepository.findMostUsedTagOfUserWithHighestCostOfAllOrders(userId);
        if (optionalTag.isEmpty()) {
            throw new ResourceNotFoundException(optionalTag.get().getTagId());
        }
        return optionalTag.get();
    }
}
