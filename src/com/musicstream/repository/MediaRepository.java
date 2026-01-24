package com.musicstream.repository;

import com.musicstream.exception.DatabaseOperationException;
import com.musicstream.model.MediaContent;
import com.musicstream.model.PodcastEpisode;
import com.musicstream.model.Song;
import com.musicstream.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MediaRepository {

    public MediaContent create(MediaContent media) {
        String sql = """
                INSERT INTO media(title, duration_sec, artist_id, media_type, genre, album, show_name, episode_number)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, media.getTitle());
            ps.setInt(2, media.getDurationSec());
            ps.setInt(3, media.getArtistId());
            ps.setString(4, media.getType());

            if (media instanceof Song s) {
                ps.setString(5, s.getGenre());
                ps.setString(6, s.getAlbum());
                ps.setNull(7, Types.VARCHAR);
                ps.setNull(8, Types.INTEGER);
            } else if (media instanceof PodcastEpisode p) {
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.VARCHAR);
                ps.setString(7, p.getShowName());
                ps.setInt(8, p.getEpisodeNumber());
            } else {
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.VARCHAR);
                ps.setNull(7, Types.VARCHAR);
                ps.setNull(8, Types.INTEGER);
            }

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) media.setId(rs.getInt(1));
            }
            return media;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Media insert failed", e);
        }
    }

    public MediaContent findById(int id) {
        String sql = "SELECT * FROM media WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapRow(rs);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Media fetch failed", e);
        }
    }

    public List<MediaContent> findAll() {
        String sql = "SELECT * FROM media ORDER BY id";
        List<MediaContent> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
            return list;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Media list failed", e);
        }
    }

    public boolean update(MediaContent media) {
        String sql = """
                UPDATE media
                SET title=?, duration_sec=?, artist_id=?, genre=?, album=?, show_name=?, episode_number=?
                WHERE id=?
                """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, media.getTitle());
            ps.setInt(2, media.getDurationSec());
            ps.setInt(3, media.getArtistId());

            if (media instanceof Song s) {
                ps.setString(4, s.getGenre());
                ps.setString(5, s.getAlbum());
                ps.setNull(6, Types.VARCHAR);
                ps.setNull(7, Types.INTEGER);
            } else if (media instanceof PodcastEpisode p) {
                ps.setNull(4, Types.VARCHAR);
                ps.setNull(5, Types.VARCHAR);
                ps.setString(6, p.getShowName());
                ps.setInt(7, p.getEpisodeNumber());
            } else {
                ps.setNull(4, Types.VARCHAR);
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.VARCHAR);
                ps.setNull(7, Types.INTEGER);
            }

            ps.setInt(8, media.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Media update failed", e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM media WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseOperationException("Media delete failed", e);
        }
    }

    private MediaContent mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        int duration = rs.getInt("duration_sec");
        int artistId = rs.getInt("artist_id");
        String type = rs.getString("media_type");

        if ("SONG".equals(type)) {
            return new Song(id, title, duration, artistId,
                    rs.getString("genre"),
                    rs.getString("album"));
        } else {
            return new PodcastEpisode(id, title, duration, artistId,
                    rs.getString("show_name"),
                    rs.getInt("episode_number"));
        }
    }
}
