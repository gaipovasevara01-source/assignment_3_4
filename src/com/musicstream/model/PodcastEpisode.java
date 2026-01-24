package com.musicstream.model;

public class PodcastEpisode extends MediaContent {
    private String showName;
    private int episodeNumber;

    public PodcastEpisode() {}

    public PodcastEpisode(int id, String title, int durationSec, int artistId, String showName, int episodeNumber) {
        super(id, title, durationSec, artistId);
        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }

    public PodcastEpisode(String title, int durationSec, int artistId, String showName, int episodeNumber) {
        super(0, title, durationSec, artistId);
        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }

    @Override public String getType() { return "PODCAST"; }
    @Override public String getStreamUrl() { return "https://stream.local/podcast/" + id; }

    public String getShowName() { return showName; }
    public void setShowName(String showName) { this.showName = showName; }

    public int getEpisodeNumber() { return episodeNumber; }
    public void setEpisodeNumber(int episodeNumber) { this.episodeNumber = episodeNumber; }

    @Override public String toString() {
        return displayInfo() + ", showName='" + showName + "', episodeNumber=" + episodeNumber;
    }
}
