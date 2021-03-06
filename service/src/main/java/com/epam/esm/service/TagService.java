package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * This interface represents Service implementation that connected controller with Data Access Object.
 *
 * @author Yauheni Vezhnavets
 * @param <T> has to implement {@link Tag} interface
 * @see Tag
 */


public interface TagService <T extends Tag> {

    /**
     * This method return all existing tags.
     *
     * @return list of{@link TagDto}
     */
    Page<TagDto> findTags(int currentPage, int pageSize);


    /**
     * This method return tag by his id.
     *
     * @return {@link TagDto}
     * @throws  {@link ResourceNotFoundException} in case if tag not found with searched id.
     */
    TagDto findTag(long tagId) throws ResourceNotFoundException;


    /**
     * This method create new tag.
     *
     * @throws {@link InvalidFieldException} in case if tag's name is not correct.
     * @throws {@link ResourceDuplicateException} in case if this tag's name already exist.
     */
    void createTag(TagDto tagDto) throws InvalidFieldException, ResourceDuplicateException;



    /**
     * This method delete tag by his id.
     *
     * @throws {@link ResourceNotFoundException} in case if this tag's id not found.
     */
    void deleteTag(long id) throws ResourceNotFoundException;


    /**
     * This method return most popular tag .
     *
     * @throws {@link ResourceNotFoundException} in case if this tag's id not found.
     */
    TagDto findMostUsedTagOfUserWithHighestCostOfAllOrders(long userId) throws ResourceNotFoundException;
}
