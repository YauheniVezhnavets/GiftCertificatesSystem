package com.epam.esm.service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entities.Tag;
import com.epam.esm.entities.User;
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
    private static User testUser;
    private static Optional<User> optionalUser;


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
        testUser = Mockito.mock(User.class);
        optionalUser = Optional.of(testUser);
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
    public void methodShouldReturnTag(){

        when(tagDao.findById(anyLong())).thenReturn(optionalTag);

        Tag tag = tagServiceImpl.findTag(1L);

        assertNotNull(tag);
    }

    @Test
    public void methodShouldDeleteTag() throws ResourceNotFoundException {

        doNothing().when(tagDao).delete(optionalTag.get());

        tagServiceImpl.deleteTag(1L);

        verify(tagDao, times(1)).delete(optionalTag.get());

    }

    @Test
    public void methodShouldFindMostUsedTagOfUserWithHighestCostOfAllOrders(){
        when(userDao.findById(anyLong())).thenReturn(optionalUser);
        when(tagDao.findMostUsedTagOfUserWithHighestCostOfAllOrders(anyLong())).thenReturn(Optional.of(testTag));

      Tag actual = tagServiceImpl.findMostUsedTagOfUserWithHighestCostOfAllOrders(1L);

       assertEquals(actual,testTag);

    }


    @Test
    public void methodShouldReturnExceptionWhenIdNotFound()  {
        when(tagDao.findById(0L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {

            tagServiceImpl.findTag(0L);
        });
    }
}
