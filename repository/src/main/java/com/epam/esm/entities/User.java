package com.epam.esm.entities;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User extends RepresentationModel<User> implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private long userId;

    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "first_name")
    private String firsName;

    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Size(min = 4, max = 50)
    @Column(name = "password")
    private String password;


    public User () {
    }

    public User(long userId, String firsName, String lastName, String email, String password) {
        this.userId = userId;
        this.firsName = firsName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (getUserId() != user.getUserId()) return false;
        if (getFirsName() != null ? !getFirsName().equals(user.getFirsName()) : user.getFirsName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(user.getLastName()) : user.getLastName() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        return getPassword() != null ? getPassword().equals(user.getPassword()) : user.getPassword() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (getUserId() ^ (getUserId() >>> 32));
        result = 31 * result + (getFirsName() != null ? getFirsName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firsName='" + firsName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
