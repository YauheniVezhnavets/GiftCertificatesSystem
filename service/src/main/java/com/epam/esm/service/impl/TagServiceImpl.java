package com.epam.esm.service.impl;


import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entities.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService<Tag> {

    private final TagDao tagDao;
    private final UserDao userDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao, UserDao userDao) {

        this.tagDao = tagDao;
        this.userDao = userDao;
    }

    @Override
    public List<Tag> findTags(int currentPage) {
        return tagDao.findAll(currentPage);
    }

    @Override
    public Tag findTag(long id) throws ResourceNotFoundException {
        return tagDao.findById(id).orElseThrow((() -> new ResourceNotFoundException(id)));
    }


    @Override
    @Transactional
    public void createTag(Tag tag) throws InvalidFieldException, ResourceDuplicateException {

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
        tagDao.delete(id);
    }

    @Override
    public Tag findMostUsedTagOfUserWithHighestCostOfAllOrders(long userId) throws ResourceNotFoundException {

        if (userDao.findById(userId).isEmpty()){
            throw new ResourceNotFoundException(userId);
        }

        Optional <Tag> optionalTag = tagDao.findMostUsedTagOfUserWithHighestCostOfAllOrders(userId);
        if (optionalTag.isEmpty()){
            throw new ResourceNotFoundException(userId);
        }
        return optionalTag.get();
    }
}
