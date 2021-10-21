package com.epam.esm.entity;

import lombok.Getter;

@Getter
public enum Permission {

    USER_READ ("user:read"),
    USER_WRITE("user:write"),
    TAG_READ("tag:read"),
    TAG_WRITE("tag:write"),
    GIFT_CERTIFICATE_READ("giftCertificate:read"),
    GIFT_CERTIFICATE_WRITE("giftCertificate:write"),
    ORDER_READ ("order:read"),
    ORDER_WRITE("order:write");


    private final String permission;

    Permission (String permission){
        this.permission=permission;
    }

}
