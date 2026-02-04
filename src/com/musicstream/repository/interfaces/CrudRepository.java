package com.musicstream.repository.interfaces;

import java.util.List;

public interface CrudRepository<T> {
    T create(T entity);
    T findById(int id);
    List<T> findAll();
    void update(T entity);
    void delete(int id);
}
