package com.musicstream.service;

import com.musicstream.exception.DuplicateResourceException;
import com.musicstream.exception.InvalidInputException;
import com.musicstream.exception.ResourceNotFoundException;
import com.musicstream.model.Playlist;
import com.musicstream.repository.PlaylistItemRepository;
import com.musicstream.repository.PlaylistRepository;

public class PlaylistService {
    private final PlaylistRepository playlistRepo = new PlaylistRepository();
    private final PlaylistItemRepository itemRepo = new PlaylistItemRepository();
    private final UserService userService = new UserService();
    private final MediaService mediaService = new MediaService();

    public Playlist createPlaylist(Playlist playlist) {
        if (playlist.getName() == null || playlist.getName().isBlank())
            throw new InvalidInputException("Playlist name cannot be empty.");

        if (!userService.exists(playlist.getOwnerUserId()))
            throw new ResourceNotFoundException("User does not exist: id=" + playlist.getOwnerUserId());

        return playlistRepo.create(playlist);
    }

    public void addToPlaylist(int playlistId, int mediaId) {
        if (!playlistRepo.existsById(playlistId))
            throw new ResourceNotFoundException("Playlist not found: id=" + playlistId);

        mediaService.getById(mediaId);

        if (itemRepo.exists(playlistId, mediaId))
            throw new DuplicateResourceException("Media already in playlist. playlistId=" + playlistId + ", mediaId=" + mediaId);

        itemRepo.addMediaToPlaylist(playlistId, mediaId);
    }
}
