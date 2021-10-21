package com.epam.esm.dto;


import com.epam.esm.entity.Tag;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TagDto extends RepresentationModel <TagDto> {

    private long tagId;

    private String name;

    private boolean isActive;


}
