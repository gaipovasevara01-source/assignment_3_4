package com.musicstream.service;

import com.musicstream.exception.DuplicateResourceException;
import com.musicstream.exception.ResourceNotFoundException;
import com.musicstream.model.MediaContent;
import com.musicstream.repository.MediaRepository;

import java.util.List;

public class MediaService {
    private final MediaRepository repo = new MediaRepository();
    private final ArtistService artistService = new ArtistService();

    public MediaContent create(MediaContent media) {
        media.validate();
        if (!artistService.exists(media.getArtistId())) {
            throw new ResourceNotFoundException("Artist does not exist: id=" + media.getArtistId());
        }
        try {
            return repo.create(media);
        } catch (Exception e) {
            throw new DuplicateResourceException("Media already exists (same title + artist + type).");
        }
    }

    public MediaContent getById(int id) {
        MediaContent m = repo.findById(id);
        if (m == null) throw new ResourceNotFoundException("Media not found: id=" + id);
        return m;
    }

    public List<MediaContent> getAll() {
        return repo.findAll();
    }

    public void update(MediaContent media) {
        media.validate();
        if (!artistService.exists(media.getArtistId())) {
            throw new ResourceNotFoundException("Artist does not exist: id=" + media.getArtistId());
        }
        boolean ok = repo.update(media);
        if (!ok) throw new ResourceNotFoundException("Media not found: id=" + media.getId());
    }

    public void delete(int id) {
        boolean ok = repo.delete(id);
        if (!ok) throw new ResourceNotFoundException("Media not found: id=" + id);
    }
}
