package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.demo.utils.InMemoryCache;


import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;
    private final InMemoryCache cache = InMemoryCache.getInstance();


    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll() {

        String key = "users_all";

        if (cache.contains(key)) {
            System.out.println("Cache hit");
            return (List<User>) cache.get(key);
        }

        System.out.println("Cache miss → loading from DB");

        List<User> users = repo.findAll();
        cache.put(key, users);

        return users;
    }


    public User getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User create(User user) {
        cache.remove("users_all");
        return repo.save(user);
    }

    public User update(Long id, User updated) {
        User u = getById(id);
        u.setUsername(updated.getUsername());
        cache.remove("users_all");
        return repo.save(u);
    }

    public void delete(Long id) {
        cache.remove("users_all");
        repo.deleteById(id);
    }
}
