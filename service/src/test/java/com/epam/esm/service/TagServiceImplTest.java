package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entities.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.TestInstance;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagServiceImplTest {

    private static Tag testTag;
    private static Optional<Tag> optionalTag;


    @Mock
    private TagDao tagDao;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private TagServiceImpl tagServiceImpl;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
        testTag = Mockito.mock(Tag.class);
        optionalTag = Optional.of(testTag);
    }

    @Test
    public void methodShouldCreateTagTest() {
        long expected = 7L;
        Tag tag = new Tag("Run");
        when(tagDao.findByName(anyString())).thenReturn(Optional.empty());
        when(tagDao.create(tag)).thenReturn(expected);

        tagServiceImpl.createTag(tag);

        verify(tagDao, times(1)).findByName(any());
        verify(tagDao, times(1)).create(any());
    }


    @Test
    public void methodShouldReturnListOfTags() {

        when(tagDao.findAll(1)).thenReturn(new ArrayList<>());

        List<Tag> tags = tagServiceImpl.findTags(1);

        assertNotNull(tags);
    }

    @Test
    public void methodShouldReturnTag() throws ResourceNotFoundException {

        when(tagDao.findById(anyLong())).thenReturn(optionalTag);

        Tag tag = tagServiceImpl.findTag(1L);

        assertNotNull(tag);
    }

    @Test
    public void methodShouldDeleteTag() throws ResourceNotFoundException {

        when(tagDao.findById(anyLong())).thenReturn(optionalTag);
        doNothing().when(tagDao).delete(anyLong());

        tagServiceImpl.deleteTag(1L);

        verify(tagDao, times(1)).delete(anyLong());

    }
}
