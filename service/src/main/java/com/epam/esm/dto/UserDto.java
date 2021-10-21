package com.epam.esm.dto;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto extends RepresentationModel <UserDto> {

    private long userId;

    private String firsName;

    private String lastName;

    private String email;

    private String password;

    private Role role;

    private boolean isActive = true;

    public UserDto(String firsName, String lastName, String email, String password, Role role, boolean isActive) {
        this.firsName = firsName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }
}
