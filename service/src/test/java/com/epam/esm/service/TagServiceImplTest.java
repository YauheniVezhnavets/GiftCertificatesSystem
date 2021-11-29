package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.TagDtoMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagServiceImplTest {

    private static Tag testTag;
    private static Optional<Tag> optionalTag;
    private static User testUser;
    private static Optional<User> optionalUser;


    private static Tag tag = new Tag(7L, "Run", true);
    private static TagDto tagDto = new TagDto(7L, "Run", true);
    private static TagDto mostUsefulTagDto = new TagDto(1L, "Relax", true);
    private static Tag mostUsefulTag = new Tag(1L, "Relax", true);

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagDtoMapper tagDtoMapper;

    @Mock
    private UserRepository userRepository;

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

        when(tagDtoMapper.map(tagDto)).thenReturn(tag);
        when(tagRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(tagRepository.save(tag)).thenReturn(any());

        tagServiceImpl.createTag(tagDto);

        verify(tagRepository, times(1)).save(any());
    }

    @Test
    public void methodShouldReturnActiveTags() {

        Pageable pageAndResultPerPage = PageRequest.of(0, 1);
        when(tagRepository.findAll(pageAndResultPerPage)).thenReturn(new PageImpl<>(List.of(tag)));
        when(tagDtoMapper.mapToDto(tag)).thenReturn(tagDto);

        Page<TagDto> tags = tagServiceImpl.findTags(0, 1);

        assertNull(tags);
    }

    @Test
    public void methodShouldReturnTag() {

        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
        when(tagDtoMapper.mapToDto(tag)).thenReturn(tagDto);

        TagDto actualTag = tagServiceImpl.findTag(1L);

        assertEquals(tagDto, actualTag);
    }

    @Test
    public void methodShouldDeleteTag() throws ResourceNotFoundException {

        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));

        tagServiceImpl.deleteTag(7L);

    }

    @Test
    public void methodShouldFindMostUsedTagOfUserWithHighestCostOfAllOrders() {

        when(tagDtoMapper.mapToDto(mostUsefulTag)).thenReturn(mostUsefulTagDto);
        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
        when(tagRepository.findMostUsedTagOfUserWithHighestCostOfAllOrders(anyLong()))
                .thenReturn(Optional.of(mostUsefulTag));

        TagDto actual = tagServiceImpl.findMostUsedTagOfUserWithHighestCostOfAllOrders(1L);

        assertEquals(mostUsefulTagDto, actual);
    }

    @Test
    public void methodShouldThrowExceptionWhenTagIsEmpty() {

        when(tagDtoMapper.mapToDto(mostUsefulTag)).thenReturn(mostUsefulTagDto);
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {

            tagServiceImpl.findMostUsedTagOfUserWithHighestCostOfAllOrders(1L);

        });
    }



    @Test
    public void methodShouldReturnExceptionWhenIdNotFound() {
        when(tagRepository.findById(0L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {

            tagServiceImpl.findTag(0L);
        });
    }

    @Test
    public void methodShouldThrowExceptionWhenTagNotFoundInMethodDeleteTag() {

        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {

            tagServiceImpl.deleteTag(1L);

        });
    }

    @Test
    public void methodShouldThrowResourceDuplicateExceptionWhenTagExist() {

        when(tagDtoMapper.map(tagDto)).thenReturn(tag);
        when(tagRepository.findByName(anyString())).thenReturn(Optional.of(tag));
        when(tagRepository.save(tag)).thenReturn(any());

        assertThrows(ResourceDuplicateException.class, () -> {

            tagServiceImpl.createTag(tagDto);

        });
    }
}
