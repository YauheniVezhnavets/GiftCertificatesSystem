package com.epam.esm.service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entities.GiftCertificate;
import com.epam.esm.entities.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.wrapper.GiftCertificateDtoWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;


public class GiftCertificateServiceImplTest {

    private static GiftCertificateDao giftCertificateDao;
    private static TagDao tagDao;
    private static GiftCertificateTagDao giftCertificateTagDao;
    private static GiftCertificateValidator giftCertificateValidator;
    private static GiftCertificateServiceImpl giftCertificateServiceImpl;
    private static GiftCertificateDtoWrapper giftCertificateDtoWrapper;
    private static Tag testTag;
    private static GiftCertificate testGiftCertificate;
    private static Optional<Tag> optionalTag;
    private static Optional<GiftCertificate> optionalGiftCertificate;


    @BeforeAll
    public static void init() throws ResourceNotFoundException {
        giftCertificateDao = Mockito.mock(GiftCertificateDao.class);
        tagDao = Mockito.mock(TagDao.class);
        giftCertificateTagDao = Mockito.mock(GiftCertificateTagDao.class);
        giftCertificateValidator = Mockito.mock(GiftCertificateValidator.class);
        giftCertificateDtoWrapper = Mockito.mock(GiftCertificateDtoWrapper.class);
        giftCertificateServiceImpl = new GiftCertificateServiceImpl(giftCertificateDao, tagDao,
                giftCertificateTagDao, giftCertificateValidator,giftCertificateDtoWrapper);
        testTag = Mockito.mock(Tag.class);
        testGiftCertificate = Mockito.mock(GiftCertificate.class);
        optionalTag = Optional.of(testTag);
        optionalGiftCertificate = Optional.of(testGiftCertificate);
    }

    @Test
    public void methodShouldReturnListOfGiftCertificates() {

        when(giftCertificateDao.getGiftCertificates(any(),any(),any(),any(),any())).thenReturn(new ArrayList<>());

        List<GiftCertificateDto> tags = giftCertificateServiceImpl.getGiftCertificates("Relax",
                null,null,null,null);

        assertNotNull(tags);
    }



    @Test
    public void methodShouldDeleteGiftCertificate() throws ResourceNotFoundException {

        when(giftCertificateDao.findById(anyLong())).thenReturn(optionalGiftCertificate);
        doNothing().when(giftCertificateDao).delete(anyLong());
        doNothing().when(giftCertificateTagDao)
                .deleteConnectionBetweenGiftCertificateAndTagsByGiftCertificateId(anyLong());

        giftCertificateServiceImpl.deleteGiftCertificate(1L);

        verify(giftCertificateDao, times(1)).delete(anyLong());
        verify(giftCertificateTagDao, times(1))
                .deleteConnectionBetweenGiftCertificateAndTagsByGiftCertificateId(anyLong());
    }
}
