package com.musicstream.repository;

import com.musicstream.exception.DatabaseOperationException;
import com.musicstream.model.User;
import com.musicstream.utils.DatabaseConnection;
import com.musicstream.repository.interfaces.UserRepositoryPort;

import java.sql.*;

public class UserRepository implements UserRepositoryPort{
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
    @Override
    public User findById(int id) {
        String sql = "SELECT id, username FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new User(rs.getInt("id"), rs.getString("username"));
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("User fetch by id failed", e);
        }
    }

    @Override
    public java.util.List<User> findAll() {
        String sql = "SELECT id, username FROM users ORDER BY id";
        java.util.List<User> list = new java.util.ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(new User(rs.getInt("id"), rs.getString("username")));
            return list;

        } catch (SQLException e) {
            throw new DatabaseOperationException("User list failed", e);
        }
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE users SET username=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entity.getUsername());
            ps.setInt(2, entity.getId());

            int rows = ps.executeUpdate();
            if (rows == 0) throw new DatabaseOperationException("User update failed (0 rows)", null);

        } catch (SQLException e) {
            throw new DatabaseOperationException("User update failed", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseOperationException("User delete failed", e);
        }
    }
}
