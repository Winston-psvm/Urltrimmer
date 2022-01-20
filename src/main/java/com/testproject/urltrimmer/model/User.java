package com.testproject.urltrimmer.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @NotEmpty
    @Column(name = "email", unique = true)
    @Email
    private String email;

    @NotBlank
    @NotEmpty
    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Enumerated
    @Column(name = "role")
    private Role role;

    public User(Integer id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
