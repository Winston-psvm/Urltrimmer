package com.testproject.urltrimmer.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
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

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 200)
    @JoinColumn(name = "user_id") //https://stackoverflow.com/a/62848296/548473
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> role;

    public User(Integer id, String email, String password, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = Collections.singleton(role);
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Collection<Role> roles) {
        this.role = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public Set<Role> getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
