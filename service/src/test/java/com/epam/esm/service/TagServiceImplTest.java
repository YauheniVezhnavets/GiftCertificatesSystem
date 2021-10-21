package com.epam.esm.service;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.TestInstance;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagServiceImplTest {

    private static Tag testTag;
    private static Optional<Tag> optionalTag;
    private static User testUser;
    private static Optional<User> optionalUser;


    @Mock
    private TagRepository tagRepository;

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

//    @Test
//    public void methodShouldCreateTagTest() {
//        long expected = 7L;
//        Tag tag = new Tag("Run");
//        when(tagRepository.findByName(anyString())).thenReturn(Optional.empty());
//    //   when(tagRepository.save(tag)).thenReturn(expected);
//
//        tagServiceImpl.createTag(tag);
//
//        verify(tagRepository, times(1)).findByName(any());
//        verify(tagRepository, times(1)).save(any());
//    }


//    @Test
//    public void methodShouldReturnListOfTags() {
//
//        when(tagRepository.findAll(1)).thenReturn(new ArrayList<>());
//
//        List<Tag> tags = tagServiceImpl.findTags(1);
//
//        assertNotNull(tags);
//    }
//
//    @Test
//    public void methodShouldReturnTag(){
//
//        when(tagRepository.findById(anyLong())).thenReturn(optionalTag);
//
//        Tag tag = tagServiceImpl.findTag(1L);
//
//        assertNotNull(tag);
//    }
//
//    @Test
//    public void methodShouldDeleteTag() throws ResourceNotFoundException {
//
//        doNothing().when(tagRepository).delete(optionalTag.get());
//
//        tagServiceImpl.deleteTag(1L);
//
//        verify(tagRepository, times(1)).delete(optionalTag.get());
//
//    }
//
//    @Test
//    public void methodShouldFindMostUsedTagOfUserWithHighestCostOfAllOrders(){
//        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
//        when(tagRepository.findMostUsedTagOfUserWithHighestCostOfAllOrders(anyLong())).thenReturn(Optional.of(testTag));
//
//      Tag actual = tagServiceImpl.findMostUsedTagOfUserWithHighestCostOfAllOrders(1L);
//
//       assertEquals(actual,testTag);
//
//    }
//
//
//    @Test
//    public void methodShouldReturnExceptionWhenIdNotFound()  {
//        when(tagRepository.findById(0L)).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//
//            tagServiceImpl.findTag(0L);
//        });
//    }
}
