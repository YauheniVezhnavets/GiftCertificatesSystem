package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;


import java.util.List;
import java.util.Set;

/**
 * This interface represents Service implementation that connected controller with Data Access Object.
 *
 * @author Yauheni Vezhnavets
 * @param <T> has to implement {@link GiftCertificate} interface
 * @see GiftCertificate
 */

public interface GiftCertificateService <T extends GiftCertificate> {

    /**
     * This method return existing gift certificates with criteria.
     * @return list of{@link GiftCertificateDto}
     */
    List<GiftCertificateDto> getGiftCertificates(Set<String> tagsName, String giftCertificateName, String description,
                                                 String sortByName, String sortByDate);

    /**
     * This method return gift certificate by his id.
     *
     * @return {@link GiftCertificateDto}
     * @throws  {@link ResourceNotFoundException} in case if tag not found with searched id.
     */
    GiftCertificateDto getGiftCertificate(long id) throws ResourceNotFoundException;


    /**
     * This method create new gift certificate.
     *
     * @throws {@link InvalidFieldException} in case if gift certificate's name is not correct.
     */
    void createGiftCertificate(GiftCertificateDto giftCertificateDto) throws InvalidFieldException;

    /**
     * This method update gift certificate.
     *
     * @throws {@link InvalidFieldException} in case if gift certificate's name is not correct.
     * @throws {@link ResourceNotFoundException} in case if this gift certificate's id not found.
     */
    void updateGiftCertificate(long id, GiftCertificateDto giftCertificateDto) throws InvalidFieldException,
            ResourceNotFoundException;

    /**
     * This method delete gift certificate by his id.
     *
     * @throws {@link ResourceNotFoundException} in case if this gift certificate's id not found.
     */

    void deleteGiftCertificate(long id) throws ResourceNotFoundException;

}
