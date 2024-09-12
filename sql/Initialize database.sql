DROP DATABASE IF EXISTS TENIS;
CREATE DATABASE TENIS;
USE TENIS;

DROP TABLE IF EXISTS PLAYERS;
CREATE TABLE IF NOT EXISTS PLAYERS(
    id_player INT auto_increment primary key,
    player_name VARCHAR(254) NOT NULL UNIQUE
)