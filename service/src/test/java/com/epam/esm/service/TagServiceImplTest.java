package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entities.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TagServiceImplTest {


    private static TagDao tagDao;
    private static TagServiceImpl tagServiceImpl;
    private static Tag testTag;
    private static Optional<Tag> optionalTag;

//
//    @BeforeAll
//    public static void init() throws ResourceNotFoundException {
//        tagDao = Mockito.mock(TagDao.class);
//        giftCertificateTagDao = Mockito.mock(GiftCertificateTagDao.class);
//        testTag = Mockito.mock(Tag.class);
//        optionalTag = Optional.of(testTag);
//    }

//    @Test
//    public void methodShouldReturnListOfTags() {
//
// //       when(tagDao.findAll()).thenReturn(new ArrayList<>());
//
//        List<Tag> tags = tagServiceImpl.getTags();
//
//        assertNotNull(tags);
//    }

//    @Test
//    public void methodShouldReturnTag() throws ResourceNotFoundException {
//
//        when(tagDao.findById(anyLong())).thenReturn(optionalTag);
//
//        Tag tag = tagServiceImpl.getTag(1L);
//
//        assertNotNull(tag);
//    }
//
//    @Test
//    public void methodShouldDeleteTag() throws ResourceNotFoundException {
//
//        when(tagDao.findById(anyLong())).thenReturn(optionalTag);
//        doNothing().when(tagDao).delete(anyLong());
//        doNothing().when(giftCertificateTagDao).deleteByTagId(anyLong());
//
//        tagServiceImpl.deleteTag(1L);
//
//        verify(tagDao, times(1)).delete(anyLong());
//        verify(giftCertificateTagDao, times(1)).deleteByTagId(anyLong());
//    }
//
//    @Test
//    public void methodShouldCreateTag() throws ResourceNotFoundException, InvalidFieldException, ResourceDuplicateException {
//
//  //      when(tagValidator.isNameValid(any())).thenReturn(true);
//        when(tagDao.findByName(anyString())).thenReturn(Optional.empty());
//        when(tagDao.create(any())).thenReturn(anyLong());
//
//        tagServiceImpl.createTag(new Tag("name"));
//
//        verify(tagDao, times(1)).create(any());
//    }
}
