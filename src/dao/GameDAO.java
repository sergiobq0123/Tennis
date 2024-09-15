package dao;

import dao.connector.Connector;
import models.Game;
import models.Player;

import java.sql.*;
import java.util.ArrayList;

public class GameDAO {


    private final Connector connector = new Connector();

    public boolean gameExists(Game game) throws SQLException {
        boolean exists = false;
        String query = "SELECT * FROM GAME WHERE id = ?";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, game.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                exists = true;
            }
        }
        return exists;
    }

    public void addGame(Game game) throws SQLException {
        String query = "INSERT INTO GAME (id_player1, id_player2, id_set, points_player1, points_player2, id_game_winner) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, game.getIdPlayer1());
            statement.setInt(2, game.getIdPlayer2());
            statement.setInt(3, game.getIdSet());
            statement.setObject(4, game.getPointsPlayer1() != null ? game.getIdGameWinner() : null, Types.INTEGER);
            statement.setObject(5, game.getPointsPlayer2() != null ? game.getIdGameWinner() : null, Types.INTEGER);
            statement.setObject(6, game.getIdGameWinner() != null ? game.getIdGameWinner() : null, Types.INTEGER);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating game failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    game.setId(generatedId);
                } else {
                    throw new SQLException("Creating game failed, no ID obtained.");
                }
            }
        }
    }

    public ArrayList<Game> getAllGames() throws SQLException {
        ArrayList<Game> games = new ArrayList<>();
        String query = "SELECT * FROM GAME";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Game game = new Game();
                game.setId(rs.getInt("id"));
                game.setIdPlayer1(rs.getInt("id_player1"));
                game.setIdPlayer2(rs.getInt("id_player2"));
                game.setIdSet(rs.getInt("id_set"));
                game.setPointsPlayer1(rs.getInt("points_player1"));
                game.setPointsPlayer2(rs.getInt("points_player2"));
                game.setIdGameWinner(rs.getInt("id_game_winner"));

                games.add(game);
            }
        }
        return games;
    }

    public Game getGame(int id) throws SQLException {
        Game game = null;
        String query = "SELECT * FROM GAME WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                game = new Game();
                game.setId(rs.getInt("id"));
                game.setIdPlayer1(rs.getInt("id_player1"));
                game.setIdPlayer2(rs.getInt("id_player2"));
                game.setIdSet(rs.getInt("id_set"));
                game.setPointsPlayer1(rs.getInt("points_player1"));
                game.setPointsPlayer2(rs.getInt("points_player2"));
                game.setIdGameWinner(rs.getInt("id_game_winner"));
            }
        }
        return game;
    }

    public void updateGame(Game game) throws SQLException {
        String query = "UPDATE GAME SET id_player1 = ?, id_player2 = ?, id_set = ?, points_player1 = ?, points_player2 = ?, id_game_winner = ? WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, game.getIdPlayer1());
            statement.setInt(2, game.getIdPlayer2());
            statement.setInt(3, game.getIdSet());
            statement.setInt(4, game.getPointsPlayer1());
            statement.setInt(5, game.getPointsPlayer2());
            statement.setObject(6, game.getIdGameWinner() != null ? game.getIdGameWinner() : null, Types.INTEGER);
            statement.setInt(7, game.getId());

            statement.executeUpdate();
        }
    }

    public void deletePlayer(Player player) throws SQLException {
        String query = "DELETE FROM PLAYER WHERE id_player = ?";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, player.getId());
            statement.executeUpdate();
        }
    }
}


