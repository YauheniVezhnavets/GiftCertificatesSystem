package com.epam.esm.controllers;


import com.epam.esm.dto.PagedModelDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * The class that represents an API for basic tag-related application operations
 *
 * @author Yauheni Vezhnavets
 **/
@RestController
@RequestMapping("GiftCertificateSystem/api/v1/tags")
public class TagController {

    private static final String JSON = "application/json";
    private static final String ID = "id";


    private final TagService<Tag> tagService;

    @Autowired
    public TagController(TagService<Tag> tagService) {
        this.tagService = tagService;
    }

    /**
     * Returns an {@link PagedModelDto} object contained {@link HttpStatus} status and a {@link List}
     * list of {@link Tag} tags retrieved from database.
     *
     * @return {@link PagedModelDto} contained both {@link HttpStatus} status and {@link List} of {@link Tag} tags.
     */
    @GetMapping(produces = JSON, params = {"page"})
    public PagedModelDto getTags(@RequestParam @Min(0) int page,
                                 @RequestParam(required = false, defaultValue = "10") @Min(1) int size,
                                 PagedResourcesAssembler<TagDto> assembler) {

        Page<TagDto> tags = tagService.findTags(page, size);

        return new PagedModelDto(
                assembler.toModel(tags), HttpStatus.OK);
    }

    /**
     * Returns an {@link EntityModel} object contained {@link HttpStatus} status and a {@link Tag} object
     * from database.
     *
     * @param id - id of {@link Tag} that has to be retrieved from database.
     * @return {@link EntityModel} contained both {@link HttpStatus} status and an {@link Tag} object.
     * @throws {@link ResourceNotFoundException} in case if nothing found with searched id.
     */
    @GetMapping(value = "/{id}", produces = JSON)
    public EntityModel<TagDto> getTag(@PathVariable(ID) long id) throws ResourceNotFoundException {
        TagDto tag = tagService.findTag(id);

        return EntityModel.of(tag,
                linkTo(methodOn(TagController.class).getTag(id)).withSelfRel(),
                linkTo(methodOn(TagController.class).deleteTag(id)).withRel("deleteTag"));
    }

    /**
     * Creates new {@link Tag} object and returns an {@link ResponseEntity} object contained
     * {@link HttpStatus} status and created {@link Tag} object.
     *
     * @param tagDto - new data for creating an {@link TagDto} object.
     * @return {@link ResponseEntity} contained {@link HttpStatus} status.
     * @throws {@link InvalidFieldException} in case if invalid data passed in request to create an {@link Tag} object.
     * @throws {@link ResourceDuplicateException} in case if passed tag already exists in database.
     */

    @PostMapping(consumes = JSON)
    @PreAuthorize("hasAuthority('tag:write')")
    public ResponseEntity createTag(@Valid @RequestBody TagDto tagDto, BindingResult bindingResult) throws ResourceDuplicateException {
        if (bindingResult.hasErrors()) {
            throw new InvalidFieldException();
        }
        tagService.createTag(tagDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Deletes an {@link Tag} object by id retrieved from request and returns an {@link ResponseEntity} object contained
     * {@link HttpStatus} status.
     *
     * @param id - id of {@link Tag} that has to be deleted from database.
     * @return {@link ResponseEntity} contained {@link HttpStatus} status.
     * @throws {@link ResourceNotFoundException} in case if nothing found.
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('tag:write')")
    public ResponseEntity deleteTag(@PathVariable(ID) long id) throws ResourceNotFoundException {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
