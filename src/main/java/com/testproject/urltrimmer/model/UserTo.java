package com.testproject.urltrimmer.model;

public record UserTo(Integer id, String email, String password) {

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
