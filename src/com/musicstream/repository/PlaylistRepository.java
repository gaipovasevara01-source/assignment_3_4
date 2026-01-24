package com.musicstream.repository;

import com.musicstream.exception.DatabaseOperationException;
import com.musicstream.model.Playlist;
import com.musicstream.utils.DatabaseConnection;

import java.sql.*;

public class PlaylistRepository {

    public Playlist create(Playlist playlist) {
        String sql = "INSERT INTO playlists(name, owner_user_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, playlist.getName());
            ps.setInt(2, playlist.getOwnerUserId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) playlist.setId(rs.getInt(1));
            }
            return playlist;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Playlist insert failed", e);
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM playlists WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Playlist exists check failed", e);
        }
    }
}
