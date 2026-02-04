package com.musicstream.service;

import com.musicstream.exception.InvalidInputException;
import com.musicstream.model.User;
import com.musicstream.repository.interfaces.UserRepositoryPort;
import com.musicstream.repository.UserRepository;

public class UserService {
    private final UserRepositoryPort repo;

    public UserService(UserRepositoryPort repo) {
        this.repo = repo;
    }

    public UserService() {
        this(new UserRepository());
    }

    public User create(String username) {
        if (username == null || username.isBlank())
            throw new InvalidInputException("Username cannot be empty.");

        String u = username.trim();

        User existing = repo.findByUsername(u);
        if (existing != null) return existing;

        return repo.create(new User(u));
    }

    public boolean exists(int ownerUserId) {
        return false;
    }
}
