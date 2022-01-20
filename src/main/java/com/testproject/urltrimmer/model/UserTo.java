package com.testproject.urltrimmer.model;

public class UserTo {

    private final String email;

    private final String password;

    public UserTo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
