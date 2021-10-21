package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagDtoMapper {

    public TagDto mapToDto(Tag tag) {
        TagDto tagDto = new TagDto();

       tagDto.setTagId(tag.getTagId());
       tagDto.setName(tag.getName());
       tagDto.setActive(tag.isActive());

        return tagDto;
    }

    public Tag map(TagDto tagDto) {
        Tag tag = new Tag();

        tag.setName(tagDto.getName());

        return tag;
    }
}
