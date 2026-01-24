package com.musicstream.controller;

import com.musicstream.exception.DuplicateResourceException;
import com.musicstream.exception.InvalidInputException;
import com.musicstream.exception.ResourceNotFoundException;
import com.musicstream.model.MediaContent;
import com.musicstream.model.Playlist;
import com.musicstream.model.PodcastEpisode;
import com.musicstream.model.Song;
import com.musicstream.model.User;
import com.musicstream.service.ArtistService;
import com.musicstream.service.MediaService;
import com.musicstream.service.PlaylistService;
import com.musicstream.service.UserService;
import com.musicstream.utils.DatabaseConnection;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initSchema();

        // ✅ Declare services once
        ArtistService artistService = new ArtistService();
        UserService userService = new UserService();
        MediaService mediaService = new MediaService();
        PlaylistService playlistService = new PlaylistService();

        try {
            System.out.println("=== Create user ===");
            User u = userService.create("farangiz");
            System.out.println(u);

            System.out.println("\n=== Create artists ===");
            var a1 = artistService.create("The Weeknd");
            var a2 = artistService.create("Lex Fridman Podcast");
            System.out.println(a1);
            System.out.println(a2);

            System.out.println("\n=== Create media (Song + Podcast) ===");
            MediaContent s1 = mediaService.create(
                    new Song("Blinding Lights", 200, a1.getId(), "Pop", "After Hours")
            );
            MediaContent p1 = mediaService.create(
                    new PodcastEpisode("AI and the Future", 3600, a2.getId(), "Lex Fridman Podcast", 123)
            );
            System.out.println(s1.play());
            System.out.println(p1.play());

            System.out.println("\n=== Read all media (polymorphism) ===");
            List<MediaContent> all = mediaService.getAll();
            for (MediaContent m : all) {
                System.out.println(m.displayInfo());
            }

            System.out.println("\n=== Update media ===");
            Song updated = (Song) mediaService.getById(s1.getId());
            updated.setTitle("Blinding Lights (Remastered)");
            updated.setDurationSec(205);
            mediaService.update(updated);
            System.out.println("Updated: " + mediaService.getById(updated.getId()).displayInfo());

            System.out.println("\n=== Create playlist ===");
            Playlist pl = playlistService.createPlaylist(new Playlist("My Favorites", u.getId()));
            System.out.println(pl);

            System.out.println("\n=== Add media to playlist ===");
            playlistService.addToPlaylist(pl.getId(), s1.getId());
            playlistService.addToPlaylist(pl.getId(), p1.getId());
            System.out.println("Added 2 items to playlist.");

            System.out.println("\n=== Trigger DuplicateResourceException (add same media again) ===");
            playlistService.addToPlaylist(pl.getId(), s1.getId());

        } catch (DuplicateResourceException e) {
            System.out.println("DUPLICATE ERROR: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("VALIDATION ERROR: " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            System.out.println("NOT FOUND ERROR: " + e.getMessage());
        }

        System.out.println("\n=== Trigger InvalidInputException (duration <= 0) ===");
        try {
            var artist = artistService.getAll().get(0); // ✅ reuse existing service
            mediaService.create(new Song("Bad Song", 0, artist.getId(), "Pop", "None")); // ✅ reuse existing service
        } catch (InvalidInputException e) {
            System.out.println("VALIDATION ERROR: " + e.getMessage());
        }

        System.out.println("\n=== Trigger ResourceNotFoundException (delete missing media) ===");
        try {
            mediaService.delete(999999);
        } catch (ResourceNotFoundException e) {
            System.out.println("NOT FOUND ERROR: " + e.getMessage());
        }

        System.out.println("\nDONE.");
    }
}
