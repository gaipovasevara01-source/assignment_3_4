package com.musicstream.controller;

import com.musicstream.exception.DuplicateResourceException;
import com.musicstream.exception.InvalidInputException;
import com.musicstream.exception.ResourceNotFoundException;
import com.musicstream.model.MediaContent;
import com.musicstream.model.Playlist;
import com.musicstream.model.PodcastEpisode;
import com.musicstream.model.Song;
import com.musicstream.model.User;
import com.musicstream.repository.PlaylistItemRepository;
import com.musicstream.service.ArtistService;
import com.musicstream.service.MediaService;
import com.musicstream.service.PlaylistService;
import com.musicstream.service.UserService;
import com.musicstream.utils.DatabaseConnection;
import com.musicstream.utils.SortingUtils;
import com.musicstream.utils.ReflectionUtils;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initSchema();

        ArtistService artistService = new ArtistService();
        UserService userService = new UserService();
        MediaService mediaService = new MediaService();
        PlaylistService playlistService = new PlaylistService();

        List<MediaContent> all = null;

        try {
            System.out.println("=== Create user ===");
            User u = userService.create("farangiz");
            System.out.println(u);

            System.out.println("\n=== Create artists ===");
            var a1 = artistService.create("The Weeknd");
            var a2 = artistService.create("Lex Fridman Podcast");

            System.out.println("\n=== Create media (Song + Podcast) ===");
            MediaContent s1 = mediaService.create(new Song("Blinding Lights", 200, a1.getId(), "Pop", "After Hours"));
            MediaContent p1 = mediaService.create(new PodcastEpisode("AI and the Future", 3600, a2.getId(), "Lex Fridman Podcast", 123));

            System.out.println("\n=== Read all media (polymorphism) ===");
            all = mediaService.getAll(); // ✅ присвоили
            for (MediaContent m : all) {
                System.out.println(m.displayInfo());
            }


        } catch (DuplicateResourceException e) {
            System.out.println("DUPLICATE ERROR: " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println("VALIDATION ERROR: " + e.getMessage());
        } catch (ResourceNotFoundException e) {
            System.out.println("NOT FOUND ERROR: " + e.getMessage());
        }

        System.out.println("\nDONE.");

        if (all != null) {
            SortingUtils.sort(all, (a, b) -> Integer.compare(a.getDurationSec(), b.getDurationSec()));
            System.out.println("\n=== Sorted by duration (lambda) ===");
            for (MediaContent m : all) {
                System.out.println(m.displayInfo());
            }
        }

        ReflectionUtils.inspect(Song.class);
    }
}
