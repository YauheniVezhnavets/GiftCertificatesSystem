package com.epam.esm.validator;

import com.epam.esm.entities.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TagValidatorTest {

    private final TagValidator tagValidator = new TagValidator();

    private static final String VALID_NAME = "Valid_name";
    private static final String INVALID_NAME = "invalid%name";

    @Test
    public void methodShouldReturnTrueWhenDataValid() {

        Tag tag = new Tag(VALID_NAME);

        boolean actual = tagValidator.isNameValid(tag);
        assertTrue(actual);
    }

    @Test
    public void methodShouldReturnFalseWhenDataInValid() {

        Tag tag = new Tag(INVALID_NAME);

        boolean actual = tagValidator.isNameValid(tag);
        assertFalse(actual);
    }

}