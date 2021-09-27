package com.epam.esm.controllers;


import com.epam.esm.entities.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.Link;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 *  The class that represents an API for basic tag-related application operations
 *  @author Yauheni Vezhnavets
 **/
@RestController
@RequestMapping("/tags")
public class TagController {

    private static final String JSON = "application/json";
    private static final String ID = "id";

    private final TagService <Tag> tagService;

    @Autowired
    public TagController(TagService <Tag> tagService) {
        this.tagService = tagService;
    }

    /**
     * Returns an {@link ResponseEntity} object contained {@link HttpStatus} status and a {@link List}
     * list of {@link Tag} tags retrieved from database.
     *
     * @return {@link ResponseEntity} contained both {@link HttpStatus} status and {@link List} of {@link Tag} tags.
     */
    @GetMapping (produces = JSON, params = {"page"})
    public ResponseEntity<List<Tag>> getTags(@RequestParam(defaultValue = "1") @Min(1) int page) {

        List<Tag> listOfTags = tagService.findTags(page);
        listOfTags.stream().forEach(
                tag -> tag.add(
                        linkTo(methodOn(TagController.class).getTags(page)).withRel("getTags"),
                        linkTo(methodOn(TagController.class).getTag(tag.getTagId())).withSelfRel(),
                        linkTo(methodOn(TagController.class).deleteTag(tag.getTagId())).withRel("deleteTag")
                )
        );

        return new ResponseEntity<>(listOfTags, HttpStatus.OK);
    }

    /**
     * Returns an {@link ResponseEntity} object contained {@link HttpStatus} status and a {@link Tag} object
     * from database.
     * @param id - id of {@link Tag} that has to be retrieved from database.
     * @return {@link ResponseEntity} contained both {@link HttpStatus} status and an {@link Tag} object.
     * @throws {@link ResourceNotFoundException} in case if nothing found with searched id.
     */
    @GetMapping(value = "/{id}", produces = JSON)
    public ResponseEntity<Tag> getTag(@PathVariable(ID) long id) throws ResourceNotFoundException {
        Tag tag = tagService.findTag(id);
        tag.add(
                linkTo(methodOn(TagController.class).getTag(id)).withSelfRel(),
                linkTo(methodOn(TagController.class).deleteTag(id)).withRel("deleteTag")
        );
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }
    /**
     * Creates new {@link Tag} object and returns an {@link ResponseEntity} object contained
     * {@link HttpStatus} status and created {@link Tag} object.
     *
     * @param tag - new data for creating an {@link Tag} object.
     * @return {@link ResponseEntity} contained {@link HttpStatus} status.
     * @throws {@link InvalidFieldException} in case if invalid data passed in request to create an {@link Tag} object.
     * @throws {@link ResourceDuplicateException} in case if passed tag already exists in database.
     */

    @PostMapping(consumes = JSON)
    public ResponseEntity createTag(@Valid @RequestBody Tag tag) throws InvalidFieldException, ResourceDuplicateException {
        tagService.createTag(tag);
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
    public ResponseEntity deleteTag(@PathVariable(ID) long id) throws ResourceNotFoundException {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
