package com.musicstream.repository;

import com.musicstream.exception.DatabaseOperationException;
import com.musicstream.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistItemRepository {

    public void addMediaToPlaylist(int playlistId, int mediaId) {
        String sql = "INSERT INTO playlist_items(playlist_id, media_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playlistId);
            ps.setInt(2, mediaId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Add media to playlist failed", e);
        }
    }

    public boolean exists(int playlistId, int mediaId) {
        String sql = "SELECT 1 FROM playlist_items WHERE playlist_id=? AND media_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, playlistId);
            ps.setInt(2, mediaId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Playlist item exists check failed", e);
        }
    }
}
