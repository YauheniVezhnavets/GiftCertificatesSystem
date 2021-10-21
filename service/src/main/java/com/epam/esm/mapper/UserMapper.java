package com.epam.esm.mapper;

import com.epam.esm.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

   public UserDetails mapToSecurityUser(User user){


    return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            user.isActive(),
            user.isActive(),
            user.isActive(),
            user.isActive(),
            user.getRole().getAuthorities());
    };
}
