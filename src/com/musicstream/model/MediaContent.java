package com.musicstream.model;

import com.musicstream.exception.InvalidInputException;
import com.musicstream.interfaces.Playable;
import com.musicstream.interfaces.Validatable;

public abstract class MediaContent implements Validatable, Playable {
    protected int id;
    protected String title;
    protected int durationSec;
    protected int artistId;

    protected MediaContent() {}

    protected MediaContent(int id, String title, int durationSec, int artistId) {
        this.id = id;
        this.title = title;
        this.durationSec = durationSec;
        this.artistId = artistId;
    }

    public abstract String getType();
    public abstract String getStreamUrl();

    public String displayInfo() {
        return "[" + getType() + "] id=" + id + ", title='" + title + "', durationSec=" + durationSec + ", artistId=" + artistId;
    }

    @Override
    public void validate() throws InvalidInputException {
        if (title == null || title.isBlank()) throw new InvalidInputException("Title cannot be empty.");
        if (durationSec <= 0) throw new InvalidInputException("Duration must be > 0 seconds.");
        if (artistId <= 0) throw new InvalidInputException("artistId must be a positive integer.");
    }

    @Override
    public String play() {
        return "Streaming: " + title + " (" + getType() + ") -> " + getStreamUrl();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getDurationSec() { return durationSec; }
    public void setDurationSec(int durationSec) { this.durationSec = durationSec; }

    public int getArtistId() { return artistId; }
    public void setArtistId(int artistId) { this.artistId = artistId; }
}
