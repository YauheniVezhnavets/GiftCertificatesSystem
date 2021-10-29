package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface represents Service implementation that connected controller with Data Access Object.
 *
 * @param <T> has to implement {@link GiftCertificate} interface
 * @author Yauheni Vezhnavets
 * @see GiftCertificate
 */

public interface GiftCertificateService<T extends GiftCertificate> {


    /**
     * This method return all active gift certificates.
     *
     * @return list of{@link GiftCertificateDto}
     */

    Page<GiftCertificateDto> findAllGiftCertificates(int currentPage, int pageSize);

    /**
     * This method return existing gift certificates with criteria.
     *
     * @return list of{@link GiftCertificateDto}
     */
    Page<GiftCertificateDto> findGiftCertificates(Map<String, String> mapWithParameters,
                                                  int currentPage, int pageSize);

    /**
     * This method return existing gift certificates with concrete tags.
     *
     * @return list of{@link GiftCertificateDto}
     */
    Page<GiftCertificateDto> findGiftCertificatesByTags(Set<String> tagsName, int currentPage, int pageSize);


    /**
     * This method return sorted existing gift certificates.
     *
     * @return list of{@link GiftCertificateDto}
     */
    Page<GiftCertificateDto> findGiftCertificatesAndSort(Map<String, String> sortNames, int currentPage, int pageSize);

    /**
     * This method return gift certificate by his id.
     *
     * @return {@link GiftCertificateDto}
     * @throws {@link ResourceNotFoundException} in case if tag not found with searched id.
     */
    GiftCertificateDto findGiftCertificate(long id) throws ResourceNotFoundException;


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
