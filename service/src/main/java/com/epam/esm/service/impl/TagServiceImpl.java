package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entities.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService<Tag> {

    private final TagDao tagDao;
    private final GiftCertificateTagDao giftCertificateTagDao;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagDao tagDao, GiftCertificateTagDao giftCertificateTagDao, TagValidator tagValidator) {
        this.tagDao = tagDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.tagValidator = tagValidator;
    }

    @Override
    public List<Tag> getTags() {
        return tagDao.findAll();
    }

    @Override
    public Tag getTag(long id) throws ResourceNotFoundException {
        Optional<Tag> optionalTag = tagDao.findById(id);
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        }
        throw new ResourceNotFoundException(id);
    }

    @Override
    public void createTag(Tag tag) throws InvalidFieldException, ResourceDuplicateException {
        if (!tagValidator.isNameValid(tag)) {
            throw new InvalidFieldException();
        }
        Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
        if (optionalTag.isPresent()) {
            throw new ResourceDuplicateException();
        }
        tagDao.create(tag);
    }

    @Override
    @Transactional
    public void deleteTag(long id) throws ResourceNotFoundException {
        if (tagDao.findById(id).isEmpty()) {
            throw new ResourceNotFoundException(id);
        }
        giftCertificateTagDao.deleteByTagId(id);
        tagDao.delete(id);
    }
}
