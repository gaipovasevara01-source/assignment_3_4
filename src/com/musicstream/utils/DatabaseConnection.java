package com.musicstream.utils;

import com.musicstream.exception.DatabaseOperationException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/music_streaming";
    private static final String USER = "postgres";
    private static final String PASSWORD = "22112007";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to connect to DB", e);
        }
    }

    public static void initSchema() {
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            String sql = readResource("schema.sql");
            for (String statement : sql.split(";")) {
                String s = statement.trim();
                if (!s.isEmpty()) st.execute(s);
            }
        } catch (Exception e) {
            throw new DatabaseOperationException("Failed to initialize schema", e);
        }
    }

    private static String readResource(String filename) {
        InputStream is = DatabaseConnection.class.getClassLoader().getResourceAsStream(filename);
        if (is == null) throw new RuntimeException("Resource not found: " + filename);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to read resource: " + filename, e);
        }
    }
}
