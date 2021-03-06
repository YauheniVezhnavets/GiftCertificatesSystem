package com.epam.esm.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email")
        }
)
public class User implements Identifiable {

    @Id
    @SequenceGenerator(
            name = "users_id_seq",
            sequenceName = "users_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_id_seq"
    )
    @Column(name = "id", updatable = false)
    private long userId;

    @NotBlank
    @Size(min = 1, max = 50, message = "FirstName should have minimum 1 symbol and maximum 50")
    @Column(name = "first_name", nullable = false)
    private String firsName;

    @NotBlank
    @Size(min = 1, max = 50, message = "FirstName should have minimum 1 symbol and maximum 50")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 50, message = "Email should have minimum 3 symbols and maximum 25")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @Size(min = 3, max = 100, message = "Password should have minimum 3 symbols and maximum 100")
    @Column(name = "password", nullable = false)
    private String password;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;


    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public User(String firsName, String lastName, String email, String password) {
        this.firsName = firsName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(long userId, String firsName, String lastName, String email, String password) {
        this.userId = userId;
        this.firsName = firsName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (getUserId() != user.getUserId()) return false;
        if (isActive() != user.isActive()) return false;
        if (getFirsName() != null ? !getFirsName().equals(user.getFirsName()) : user.getFirsName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(user.getLastName()) : user.getLastName() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        return getRoles() != null ? getRoles().equals(user.getRoles()) : user.getRoles() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + (getFirsName() != null ? getFirsName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getRoles() != null ? getRoles().hashCode() : 0);
        result = 31 * result + (isActive() ? 1 : 0);
        return result;
    }
}
