package com.epam.esm.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Entity
@Table(name = "permissions")
public class Permission {

    @Id
    @SequenceGenerator(
            name = "permissions_id_seq",
            sequenceName = "permissions_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "permissions_id_seq"
    )
    @Column(name = "id")
    private long permissionId;


    @NotBlank
    @Size(min = 3, max = 30, message = "permission's name should have minimum 3 symbols and maximum 30")
    @Column(name = "permission", nullable = false)
    private String permission;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        if (getPermissionId() != that.getPermissionId()) return false;
        return getPermission() != null ? getPermission().equals(that.getPermission()) : that.getPermission() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getPermissionId() ^ (getPermissionId() >>> 32));
        result = 31 * result + (getPermission() != null ? getPermission().hashCode() : 0);
        return result;
    }
}
