package com.musicstream.repository;

import com.musicstream.exception.DatabaseOperationException;
import com.musicstream.model.User;
import com.musicstream.utils.DatabaseConnection;

import java.sql.*;

public class UserRepository {
    public User create(User user) {
        String sql = "INSERT INTO users(username) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) user.setId(rs.getInt(1));
            }
            return user;

        } catch (SQLException e) {
            throw new DatabaseOperationException("User insert failed");
        }
    }
    public User findByUsername(String username) {
        String sql = "SELECT id, username FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new User(rs.getInt("id"), rs.getString("username"));
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("User fetch by username failed", e);
        }
    }


    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) {
            throw new DatabaseOperationException("User exists check failed", e);
        }
    }
}
