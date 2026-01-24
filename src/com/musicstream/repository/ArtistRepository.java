package com.musicstream.repository;

import com.musicstream.exception.DatabaseOperationException;
import com.musicstream.model.Artist;
import com.musicstream.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistRepository {

    public Artist create(Artist artist) {
        String sql = "INSERT INTO artists(name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, artist.getName());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) artist.setId(rs.getInt(1));
            }
            return artist;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Artist insert failed");
        }
    }

    public Artist findById(int id) {
        String sql = "SELECT id, name FROM artists WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new Artist(rs.getInt("id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Artist fetch failed");
        }
    }

    public List<Artist> findAll() {
        String sql = "SELECT id, name FROM artists ORDER BY id";
        List<Artist> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Artist(rs.getInt("id"), rs.getString("name")));
            }
            return list;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Artist list failed");
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM artists WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Artist exists check failed", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM artists WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Artist delete failed", e);
        }
    }
}
