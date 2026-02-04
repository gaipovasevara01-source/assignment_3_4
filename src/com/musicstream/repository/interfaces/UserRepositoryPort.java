package com.musicstream.repository.interfaces;

import com.musicstream.model.User;

public interface UserRepositoryPort extends CrudRepository<User> {
    User findByUsername(String username);
    boolean existsById(int id);
}
