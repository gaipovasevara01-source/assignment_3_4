CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS artists (
                                       id SERIAL PRIMARY KEY,
                                       name VARCHAR(150) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS media (
                                     id SERIAL PRIMARY KEY,
                                     title VARCHAR(200) NOT NULL,
    duration_sec INTEGER NOT NULL CHECK (duration_sec > 0),
    artist_id INTEGER NOT NULL,
    media_type VARCHAR(20) NOT NULL CHECK (media_type IN ('SONG','PODCAST')),
    genre VARCHAR(100),
    album VARCHAR(150),
    show_name VARCHAR(150),
    episode_number INTEGER,
    UNIQUE (title, artist_id, media_type),
    FOREIGN KEY (artist_id) REFERENCES artists(id)
    );

CREATE TABLE IF NOT EXISTS playlists (
                                         id SERIAL PRIMARY KEY,
                                         name VARCHAR(150) NOT NULL,
    owner_user_id INTEGER NOT NULL,
    UNIQUE (name, owner_user_id),
    FOREIGN KEY (owner_user_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS playlist_items (
                                              id SERIAL PRIMARY KEY,
                                              playlist_id INTEGER NOT NULL,
                                              media_id INTEGER NOT NULL,
                                              added_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              UNIQUE (playlist_id, media_id),
    FOREIGN KEY (playlist_id) REFERENCES playlists(id),
    FOREIGN KEY (media_id) REFERENCES media(id)
    );
