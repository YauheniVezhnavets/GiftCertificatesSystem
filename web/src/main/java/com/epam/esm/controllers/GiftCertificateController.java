package com.epam.esm.controllers;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.PagedModelDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The class that represents an API for basic giftCertificate-related application operations
 *
 * @author Yauheni Vezhnavets
 **/


@RestController
@RequestMapping("api/v1/certificates")
public class GiftCertificateController {

    private static final String JSON = "application/json";
    private static final String ID = "id";

    private final GiftCertificateService<GiftCertificate> giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService<GiftCertificate> giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('giftCertificate:read')")
    public PagedModelDto getAllGiftCertificates(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) int size,
            PagedResourcesAssembler<GiftCertificateDto> assembler
    ) {
        Page<GiftCertificateDto> giftCertificates = giftCertificateService.findAllGiftCertificates(page, size);

        return new PagedModelDto(
                assembler.toModel(giftCertificates), HttpStatus.OK);
    }

    /**
     * Returns an {@link PagedModelDto} object contained {@link HttpStatus} status and a {@link List} list of
     * {@link GiftCertificateDto} mapped from a list of {@link GiftCertificate} gift certificates retrieved
     * from database.The retrieved data has to fill parameters received from request.
     * All parameters are optional.
     * <p>
     * //   * @param tagName - name of a {@link Tag} tag should be contained in a gift certificate.
     *
     * @param giftCertificateName - part of a name of searched gift certificate.
     *                            //  * @param description - part of a description of searched gift certificate.
     * @return {@link PagedModelDto} contained both {@link HttpStatus} status and {@link List} of
     * {@link GiftCertificateDto}
     */

    @GetMapping(value = "/search", produces = JSON)
    @PreAuthorize("hasAuthority('giftCertificate:read')")
    public PagedModelDto getGiftCertificatesWithCriteria(

            @RequestParam(required = false) String giftCertificateName,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) int size,
            PagedResourcesAssembler<GiftCertificateDto> assembler) {

        Map<String, String> mapWithParameters = new HashMap<>();

        mapWithParameters.put("giftCertificateName", giftCertificateName);
        mapWithParameters.put("description", description);
        Page<GiftCertificateDto> giftCertificates = giftCertificateService.findGiftCertificates(mapWithParameters,
                page, size);

        return new PagedModelDto(
                assembler.toModel(giftCertificates), HttpStatus.OK);
    }


    /**
     * Returns an {@link PagedModelDto} object contained {@link HttpStatus} status and a {@link List} list of
     * {@link GiftCertificateDto} mapped from a list of {@link GiftCertificate} gift certificates retrieved
     * from database.The retrieved data has to fill parameters received from request.
     * All parameters are optional.
     * <p>
     * //   * @param tagName - name of a {@link Tag} tag should be contained in a gift certificate.
     *
     * @return {@link PagedModelDto} contained both {@link HttpStatus} status and {@link List} of
     * {@link GiftCertificateDto}
     */

    @GetMapping(value = "/tags", produces = JSON)
    @PreAuthorize("hasAuthority('giftCertificate:read')")
    public PagedModelDto getGiftCertificatesByTags(
            @RequestParam(name = "tagName") Set<String> tagsName,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) int size,
            PagedResourcesAssembler<GiftCertificateDto> assembler) {
        Page<GiftCertificateDto> giftCertificates =
                giftCertificateService.findGiftCertificatesByTags(tagsName, page, size);

        return new PagedModelDto(
                assembler.toModel(giftCertificates), HttpStatus.OK);
    }

    /**
     * Returns an {@link PagedModelDto} object contained {@link HttpStatus} status and a {@link List} list of
     * {@link GiftCertificateDto} mapped from a list of {@link GiftCertificate} gift certificates retrieved
     * from database.The retrieved data has to fill parameters received from request.
     * All parameters are optional.
     * <p>
     * //  * @param sortByName - sort of the retrieved gift certificates by name.
     *
     * @param sortByDate - sort of the retrieved gift certificates by creation date.
     * @return {@link PagedModelDto} contained both {@link HttpStatus} status and {@link List} of
     * {@link GiftCertificateDto}
     */

    @GetMapping(value = "/sort", produces = JSON)
    @PreAuthorize("hasAuthority('giftCertificate:read')")
    public PagedModelDto getGiftCertificatesAndSort(

            @RequestParam(required = false) String sortByName,
            @RequestParam(required = false) String sortByDate,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "10") @Min(1) int size,
            PagedResourcesAssembler<GiftCertificateDto> assembler) {

        Map<String, String> mapWithParameters = new HashMap<>();

        if (sortByName != null) {
            mapWithParameters.put("sortByName", sortByName);
        }
        if (sortByDate != null) {
            mapWithParameters.put("sortByDate", sortByDate);
        }

        Page<GiftCertificateDto> giftCertificates = giftCertificateService.
                findGiftCertificatesAndSort(mapWithParameters,page,size);


        return new PagedModelDto(
                assembler.toModel(giftCertificates), HttpStatus.OK);
    }


    /**
     * Returns an {@link EntityModel} object contained {@link HttpStatus} status and a {@link GiftCertificateDto}
     * object mapped from a  {@link GiftCertificate} gift certificates retrieved from database.
     *
     * @param id - id of {@link GiftCertificate} that has to be retrieved from database.
     * @return {@link EntityModel} contained both {@link HttpStatus} status and {@link GiftCertificateDto} object
     * @throws {@link ResourceNotFoundException} in case if nothing found with searched id.
     */
    @GetMapping(value = "/{id}", produces = JSON)
    @PreAuthorize("hasAuthority('giftCertificate:read')")
    public EntityModel<GiftCertificateDto> getGiftCertificate(@PathVariable(ID) long id)
            throws ResourceNotFoundException {
        GiftCertificateDto giftCertificate = giftCertificateService.findGiftCertificate(id);

        return EntityModel.of(giftCertificate,
                linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(id)).withSelfRel(),
                linkTo(methodOn(GiftCertificateController.class).deleteGiftCertificate(id))
                        .withRel("deleteGiftCertificate"));
    }

    /**
     * Creates new {@link GiftCertificate} object and returns an {@link ResponseEntity} object contained
     * {@link HttpStatus} status and {@link GiftCertificateDto} mapped from passed {@link GiftCertificate} object.
     *
     * @param giftCertificateDto - data for creating new {@link GiftCertificate} object.
     * @return {@link ResponseEntity} contained {@link HttpStatus} status.
     * @throws {@link InvalidFieldException} in case if invalid data passed in request to construct
     *                a {@link GiftCertificate} object.
     */

    @PostMapping(consumes = JSON)
    @PreAuthorize("hasAuthority('giftCertificate:write')")
    public ResponseEntity createGiftCertificate(@Valid @RequestBody GiftCertificateDto giftCertificateDto,
                                                BindingResult bindingResult)
            throws InvalidFieldException {
        if (bindingResult.hasErrors()) {
            throw new InvalidFieldException();
        }

        giftCertificateService.createGiftCertificate(giftCertificateDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * Updates {@link GiftCertificate} object by id and returns an {@link ResponseEntity} object contained
     * {@link HttpStatus} status and {@link GiftCertificateDto} mapped from {@link GiftCertificate} object with id.
     *
     * @param id                 - id of {@link GiftCertificate} object to be updated.
     * @param giftCertificateDto - new data for updating an {@link GiftCertificate} object to be updated.
     * @return {@link ResponseEntity} contained {@link HttpStatus} status.
     * @throws {@link InvalidFieldException} in case if invalid data passed in request to update
     *                a {@link GiftCertificate} object.
     * @throws {@link ResourceNotFoundException} in case if nothing found with searched id.
     */
    @PatchMapping(value = "/{id}", consumes = JSON)
    @PreAuthorize("hasAuthority('giftCertificate:write')")
    public ResponseEntity updateGiftCertificate(@PathVariable(ID) long id,
                                                @Valid @RequestBody GiftCertificateDto giftCertificateDto)
            throws ResourceNotFoundException, InvalidFieldException {
        giftCertificateService.updateGiftCertificate(id, giftCertificateDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Deletes an {@link GiftCertificate} object by id retrieved from request and returns an {@link ResponseEntity}
     * object contained
     * {@link HttpStatus} status.
     *
     * @param id - id of {@link GiftCertificate} that has to be deleted from database.
     * @return {@link ResponseEntity} contained {@link HttpStatus} status.
     * @throws {@link ResourceNotFoundException} in case if nothing found with searched id.
     */

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('giftCertificate:write')")
    public ResponseEntity deleteGiftCertificate(@PathVariable(ID) long id) throws ResourceNotFoundException {
        giftCertificateService.deleteGiftCertificate(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
