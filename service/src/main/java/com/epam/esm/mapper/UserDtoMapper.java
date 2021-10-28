package com.epam.esm.mapper;


import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUserId(user.getUserId());
        userDto.setFirsName(user.getFirsName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoles(user.getRoles());
        userDto.setActive(user.isActive());

        return userDto;
    }

    public User map(UserDto userDto) {
        User user = new User();

        user.setFirsName(userDto.getFirsName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        user.setActive(userDto.isActive());

        return user;
    }

}
