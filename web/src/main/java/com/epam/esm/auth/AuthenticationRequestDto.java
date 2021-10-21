package com.epam.esm.auth;

import lombok.Data;

@Data
public class AuthenticationRequestDto {

    private String email;
    private String password;

}
