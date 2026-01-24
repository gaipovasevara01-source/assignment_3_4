package com.musicstream.model;

public class Song extends MediaContent {
    private String genre;
    private String album;

    public Song() {}

    public Song(int id, String title, int durationSec, int artistId, String genre, String album) {
        super(id, title, durationSec, artistId);
        this.genre = genre;
        this.album = album;
    }

    public Song(String title, int durationSec, int artistId, String genre, String album) {
        super(0, title, durationSec, artistId);
        this.genre = genre;
        this.album = album;
    }

    @Override public String getType() { return "SONG"; }
    @Override public String getStreamUrl() { return "https://stream.local/song/" + id; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }

    @Override public String toString() {
        return displayInfo() + ", genre='" + genre + "', album='" + album + "'";
    }
}
