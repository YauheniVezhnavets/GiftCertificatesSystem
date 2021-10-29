package com.epam.esm.mapper;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserDtoMapperTest {

    private final UserDtoMapper userDtoMapper = new UserDtoMapper();

    private final User user = new User (1L, "Ivan", "Ivanov", "ivan@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O");

    private final User invalidUser = new User (1L, "Ivaxn", "Ivanov", "ivan@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O");

    private final UserDto userDto = new UserDto (1L, "Ivan", "Ivanov", "ivan@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O");

    private final UserDto expectedUserDto = new UserDto (1L, "Ivan", "Ivanov", "ivan@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O");

    private final UserDto invalidUserDto = new UserDto (1L, "Ivanx", "Ivanov", "ivan@mail.ru",
            "$2a$10$loNUxszEeh6zePwybYSHl.yzjhWQJDO6OfTtbdOspuDOYRnAXPT.O");


    @Test
    void testShouldMapUserToUserDto() {

        UserDto actualUserDto = userDtoMapper.mapToDto(user);

        assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    void testShouldMapUserToUserDtoNotEquals() {

        UserDto actualUserDto = userDtoMapper.mapToDto(user);

        assertNotEquals(invalidUserDto, actualUserDto);
    }


    @Test
    void testShouldMapUserDtoToUserNotEquals() {

        User actualUser = userDtoMapper.map(userDto);

        assertNotEquals(invalidUser, actualUser);
    }
}
