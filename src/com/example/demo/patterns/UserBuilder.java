package com.example.demo.patterns;

import com.example.demo.model.User;

public class UserBuilder {

    private String username;

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public User build() {
        return new User(username);
    }
}
