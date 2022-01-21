package com.testproject.urltrimmer.web;

import com.testproject.urltrimmer.model.User;
import org.springframework.lang.NonNull;

public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.getRole());
        this.user = user;
    }

    public int id() {
        return user.getId();
    }

    public User getUser() {
        return user;
    }
}
