DROP DATABASE IF EXISTS TENIS;
CREATE DATABASE TENIS;
USE TENIS;

CREATE TABLE IF NOT EXISTS PLAYER(
                                     id_player INT auto_increment primary key,
                                     player_name VARCHAR(254) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS POINT (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     id_player1 INT NOT NULL,
                                     id_player2 INT NOT NULL,
                                     score_player1 VARCHAR(255),
    score_player2 VARCHAR(255),
    id_player_service INT NOT NULL,
    id_point_winner INT,
    id_game INT NOT NULL
    );

CREATE TABLE IF NOT EXISTS GAME (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    id_player1 INT NOT NULL,
                                    id_player2 INT NOT NULL,
                                    id_set INT NOT NULL,
                                    points_player1 VARCHAR(255),
    points_player2 VARCHAR(255),
    id_game_winner INT
    );

CREATE TABLE IF NOT EXISTS SET_ (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    id_player1 INT NOT NULL,
                                    id_player2 INT NOT NULL,
                                    id_match INT NOT NULL,
                                    games_player1 INT,
                                    games_player2 INT,
                                    id_set_winner INT
);
CREATE TABLE IF NOT EXISTS MATCH_ (
                                      id INT AUTO_INCREMENT PRIMARY KEY,
                                      date date NOT NULL,
                                      sets_number INT NOT NULL,
                                      id_player1 INT NOT NULL,
                                      id_player2 INT NOT NULL,
                                      id_match_winner INT
);