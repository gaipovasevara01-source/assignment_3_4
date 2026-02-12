package com.example.demo.patterns;

import com.example.demo.model.User;

public class UserFactory {

    public static User create(String username) {
        return new User(username);
    }
}
