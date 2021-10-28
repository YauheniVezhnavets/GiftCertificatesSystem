package com.epam.esm.mapper;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class UserMapper {

    public UserDetails mapToSecurityUser(User user) {

            List<GrantedAuthority> authorities = new ArrayList<>();
            for (Role role: user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getRole()));
                role.getPermissions().stream()
                        .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                        .forEach(authorities::add);
            }



        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                user.isActive(),
                user.isActive(),
                user.isActive(),
                authorities);
    }
}
