package com.epam.esm.validator;

import com.epam.esm.entities.Tag;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class TagValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile("[#_А-Яа-я\\w]{1,50}");

    public boolean isNameValid(Tag tag) {
        String tagName = tag.getName();
        return tagName != null && NAME_PATTERN.matcher(tagName).matches();
    }
}
