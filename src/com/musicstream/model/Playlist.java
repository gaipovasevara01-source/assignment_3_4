package com.musicstream.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id;
    private String name;
    private int ownerUserId;

    private final List<MediaContent> items = new ArrayList<>();

    public Playlist() {}
    public Playlist(int id, String name, int ownerUserId) {
        this.id = id; this.name = name; this.ownerUserId = ownerUserId;
    }
    public Playlist(String name, int ownerUserId) {
        this.name = name; this.ownerUserId = ownerUserId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(int ownerUserId) { this.ownerUserId = ownerUserId; }

    public List<MediaContent> getItems() { return items; }

    public void addItem(MediaContent media) { items.add(media); }

    @Override public String toString() {
        return "Playlist{id=" + id + ", name='" + name + "', ownerUserId=" + ownerUserId + ", itemsCount=" + items.size() + "}";
    }
}
