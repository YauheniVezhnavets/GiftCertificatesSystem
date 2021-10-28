package com.epam.esm.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;


@Getter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @SequenceGenerator(
            name = "roles_id_seq",
            sequenceName = "roles_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "roles_id_seq"
    )
    @Column(name = "id")
    private long roleId;

    @NotBlank
    @Size(min = 3, max = 25, message = "role's name should have minimum 3 symbols and maximum 25")
    @Column(name = "role", nullable = false)
    private String role;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    private Set<Permission> permissions;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role1 = (Role) o;

        if (getRoleId() != role1.getRoleId()) return false;
        if (getRole() != null ? !getRole().equals(role1.getRole()) : role1.getRole() != null) return false;
        return getPermissions() != null ? getPermissions().equals(role1.getPermissions()) : role1.getPermissions() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getRoleId() ^ (getRoleId() >>> 32));
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        result = 31 * result + (getPermissions() != null ? getPermissions().hashCode() : 0);
        return result;
    }
}
