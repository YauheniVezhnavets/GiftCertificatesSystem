package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TagDtoMapperTest {

    private final TagDtoMapper tagMapper = new TagDtoMapper();
    Tag tag = new Tag(1L, "Run", true);
    TagDto tagDto = new TagDto(0L, "Run", true);
    TagDto expectedTagDto = new TagDto(1L, "Run", true);
    TagDto invalidTagDto = new TagDto(1L, "RunX", true);
    Tag invalidTag = new Tag(0L, "RunX", true);

    @Test
    void testShouldMapTagToTagDto() {

        TagDto actualTagDto = tagMapper.mapToDto(tag);

        assertEquals(expectedTagDto, actualTagDto);
    }

    @Test
    void testShouldMapTagToTagDtoNotEquals() {

        TagDto actualTagDto = tagMapper.mapToDto(tag);

        assertNotEquals(invalidTagDto, actualTagDto);
    }


    @Test
    void testShouldMapTagDtoToTagNotEquals() {

        Tag actualTag = tagMapper.map(tagDto);

        assertNotEquals(invalidTag, actualTag);
    }
}
