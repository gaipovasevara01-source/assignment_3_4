package com.musicstream.service;

import com.musicstream.exception.DuplicateResourceException;
import com.musicstream.exception.InvalidInputException;
import com.musicstream.exception.ResourceNotFoundException;
import com.musicstream.model.Artist;
import com.musicstream.repository.ArtistRepository;

import java.util.List;

public class ArtistService {
    private final ArtistRepository repo = new ArtistRepository();

    public Artist create(String name) {
        if (name == null || name.isBlank()) throw new InvalidInputException("Artist name cannot be empty.");
        try {
            return repo.create(new Artist(name.trim()));
        } catch (Exception e) {
            // unique constraint might trigger database exception
            throw new DuplicateResourceException("Artist already exists: " + name);
        }
    }

    public Artist getById(int id) {
        Artist a = repo.findById(id);
        if (a == null) throw new ResourceNotFoundException("Artist not found: id=" + id);
        return a;
    }

    public List<Artist> getAll() {
        return repo.findAll();
    }

    public void delete(int id) {
        boolean ok = repo.delete(id);
        if (!ok) throw new ResourceNotFoundException("Artist not found: id=" + id);
    }

    public boolean exists(int id) {
        return repo.existsById(id);
    }
}
