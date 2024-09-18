DROP DATABASE IF EXISTS TENIS;
CREATE DATABASE TENIS;
USE TENIS;

-- Tabla de jugadores
CREATE TABLE IF NOT EXISTS PLAYER (
      id_player INT auto_increment PRIMARY KEY,
      player_name VARCHAR(254) NOT NULL UNIQUE
    );

-- Tabla de partidos (match)
CREATE TABLE IF NOT EXISTS MATCH_ (
      id INT AUTO_INCREMENT PRIMARY KEY,
      date DATE NOT NULL,
      sets_number INT NOT NULL,
      id_player1 INT NOT NULL,
      id_player2 INT NOT NULL,
      id_match_winner INT,
      FOREIGN KEY (id_player1) REFERENCES PLAYER(id_player),
    FOREIGN KEY (id_player2) REFERENCES PLAYER(id_player),
    FOREIGN KEY (id_match_winner) REFERENCES PLAYER(id_player)
    );

-- Tabla de sets
CREATE TABLE IF NOT EXISTS SET_ (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_player1 INT NOT NULL,
    id_player2 INT NOT NULL,
    id_match INT NOT NULL,
    games_player1 INT,
    games_player2 INT,
    id_set_winner INT,
    set_over BOOLEAN,
    FOREIGN KEY (id_player1) REFERENCES PLAYER(id_player),
    FOREIGN KEY (id_player2) REFERENCES PLAYER(id_player),
    FOREIGN KEY (id_match) REFERENCES MATCH_(id),
    FOREIGN KEY (id_set_winner) REFERENCES PLAYER(id_player)
    );

-- Tabla de juegos (game)
CREATE TABLE IF NOT EXISTS GAME (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_player1 INT NOT NULL,
    id_player2 INT NOT NULL,
    id_set INT NOT NULL,
    points_player1 VARCHAR(255),
    points_player2 VARCHAR(255),
    id_game_winner INT,
    game_over BOOLEAN,
    FOREIGN KEY (id_player1) REFERENCES PLAYER(id_player),
    FOREIGN KEY (id_player2) REFERENCES PLAYER(id_player),
    FOREIGN KEY (id_set) REFERENCES SET_(id),
    FOREIGN KEY (id_game_winner) REFERENCES PLAYER(id_player)
    );

-- Tabla de puntos (point)
CREATE TABLE IF NOT EXISTS POINT (
     id INT AUTO_INCREMENT PRIMARY KEY,
     id_player1 INT NOT NULL,
     id_player2 INT NOT NULL,
     score_player1 VARCHAR(255),
    score_player2 VARCHAR(255),
    id_player_service INT NOT NULL,
    id_point_winner INT,
    id_game INT NOT NULL,
    FOREIGN KEY (id_player1) REFERENCES PLAYER(id_player),
    FOREIGN KEY (id_player2) REFERENCES PLAYER(id_player),
    FOREIGN KEY (id_player_service) REFERENCES PLAYER(id_player),
    FOREIGN KEY (id_point_winner) REFERENCES PLAYER(id_player),
    FOREIGN KEY (id_game) REFERENCES GAME(id)
    );
