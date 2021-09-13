package com.epam.esm.controllers;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 *  The class that represents an API for basic giftCertificate-related application operations
 *  @author Yauheni Vezhnavets
 **/


@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {

    private static final String JSON = "application/json";
    private static final String ID = "id";

    GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Returns an {@link ResponseEntity} object contained {@link HttpStatus} status and a {@link List} list of
     * {@link GiftCertificateDto} mapped from a list of {@link GiftCertificate} gift certificates retrieved
     * from database.The retrieved data has to fill parameters received from request.
     * All parameters are optional.
     *
     * @param tagName - name of a {@link Tag} tag should be contained in a gift certificate.
     * @param giftCertificateName - part of a name of searched gift certificate.
     * @param description - part of a description of searched gift certificate.
     * @param sortByName - sort of the retrieved gift certificates by name.
     * @param sortByDate - sort of the retrieved gift certificates by creation date.
     * @return {@link ResponseEntity} contained both {@link HttpStatus} status and {@link List} of
     * {@link GiftCertificateDto}
     */

    @GetMapping(produces = JSON)
    public ResponseEntity<List<GiftCertificateDto>> getGiftCertificates(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String giftCertificateName,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String sortByName,
            @RequestParam (required = false) String sortByDate) {
        return new ResponseEntity<>(giftCertificateService.getGiftCertificates(tagName,giftCertificateName,
                description,sortByName,sortByDate), HttpStatus.OK);
    }

    /**
     * Returns an {@link ResponseEntity} object contained {@link HttpStatus} status and a {@link GiftCertificateDto}
     * object mapped from a  {@link GiftCertificate} gift certificates retrieved from database.
     *
     * @param id - id of {@link GiftCertificate} that has to be retrieved from database.
     * @return {@link ResponseEntity} contained both {@link HttpStatus} status and {@link GiftCertificateDto} object
     * @throws {@link ResourceNotFoundException} in case if nothing found with searched id.
     */
    @GetMapping(value = "/{id}", produces = JSON)
    public ResponseEntity<GiftCertificateDto> getGiftCertificate(@PathVariable(ID) long id)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(giftCertificateService.getGiftCertificate(id), HttpStatus.OK);
    }

    /**
     * Creates new {@link GiftCertificate} object and returns an {@link ResponseEntity} object contained
     * {@link HttpStatus} status and {@link GiftCertificateDto} mapped from passed {@link GiftCertificate} object.
     * @param giftCertificateDto - data for creating new {@link GiftCertificate} object.
     * @return {@link ResponseEntity} contained {@link HttpStatus} status.
     * @throws {@link InvalidFieldException} in case if invalid data passed in request to construct
     * a {@link GiftCertificate} object.
     *
     */

    @PostMapping(consumes = JSON)
    public ResponseEntity createGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto)
            throws InvalidFieldException {
        giftCertificateService.createGiftCertificate(giftCertificateDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    /**
     * Updates {@link GiftCertificate} object by id and returns an {@link ResponseEntity} object contained
     * {@link HttpStatus} status and {@link GiftCertificateDto} mapped from {@link GiftCertificate} object with id.
     *
     * @param id - id of {@link GiftCertificate} object to be updated.
     * @param giftCertificateDto - new data for updating an {@link GiftCertificate} object to be updated.
     * @return {@link ResponseEntity} contained {@link HttpStatus} status.
     * @throws {@link InvalidFieldException} in case if invalid data passed in request to update
     * a {@link GiftCertificate} object.
     * @throws {@link ResourceNotFoundException} in case if nothing found with searched id.
     */
    @PatchMapping(value = "/{id}", consumes = JSON)
    public ResponseEntity updateGiftCertificate(@PathVariable(ID) long id,
                                                @RequestBody GiftCertificateDto giftCertificateDto)
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
    public ResponseEntity deleteGiftCertificate(@PathVariable(ID) long id) throws ResourceNotFoundException {
        giftCertificateService.deleteGiftCertificate(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
