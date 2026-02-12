package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User create(User user) {
        return repo.save(user);
    }

    public User update(Long id, User updated) {
        User u = getById(id);
        u.setUsername(updated.getUsername());
        return repo.save(u);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
