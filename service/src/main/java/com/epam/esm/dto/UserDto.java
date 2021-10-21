package com.epam.esm.dto;

import com.epam.esm.entity.Role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDto userDto = (UserDto) o;

        if (getUserId() != userDto.getUserId()) return false;
        if (isActive() != userDto.isActive()) return false;
        if (getFirsName() != null ? !getFirsName().equals(userDto.getFirsName()) : userDto.getFirsName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(userDto.getLastName()) : userDto.getLastName() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(userDto.getEmail()) : userDto.getEmail() != null) return false;
        if (getPassword() != null ? !getPassword().equals(userDto.getPassword()) : userDto.getPassword() != null)
            return false;
        return getRole() == userDto.getRole();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + (getFirsName() != null ? getFirsName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        result = 31 * result + (isActive() ? 1 : 0);
        return result;
    }
}
