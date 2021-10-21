package com.epam.esm.entity;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.entity.Permission.*;

@Getter
public enum Role {
    ADMIN(Set.of(
            TAG_READ,
            TAG_WRITE,
            GIFT_CERTIFICATE_READ,
            GIFT_CERTIFICATE_WRITE,
            USER_READ,
            USER_WRITE,
            ORDER_READ,
            ORDER_WRITE
    )),
    USER(Set.of(
            TAG_READ,
            GIFT_CERTIFICATE_READ,
            USER_READ,
            ORDER_READ,
            ORDER_WRITE
    ));

    private final Set<Permission> permissions;


    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }


    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
