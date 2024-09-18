package dao;

import dao.connector.Connector;
import models.Match;

import java.sql.*;
import java.util.ArrayList;

public class MatchDAO {

    private final Connector connector = new Connector();

    public boolean matchExists(Match match) throws SQLException {
        boolean exists = false;
        String query = "SELECT * FROM MATCH_ WHERE id = ?";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, match.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                exists = true;
            }
        }
        return exists;
    }

    public void addMatch(Match match) throws SQLException {
        String query = "INSERT INTO MATCH_ (date, sets_number, id_player1, id_player2, id_match_winner) VALUES (?, ?, ?, ?,?)";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setDate(1, match.getDate());
            statement.setInt(2, match.getSetsNumber());
            statement.setInt(3, match.getIdPlayer1());
            statement.setInt(4, match.getIdPlayer2());
            statement.setObject(5, match.getIdMatchWinner() != null ? match.getIdMatchWinner() : null, Types.INTEGER);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating set failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    match.setId(generatedId);
                } else {
                    throw new SQLException("Creating set failed, no ID obtained.");
                }
            }
        }
    }

    public ArrayList<Match> getAllMatches() throws SQLException {
        ArrayList<Match> matches = new ArrayList<>();
        String query = "SELECT * FROM MATCH_";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Match match = new Match();
                match.setId(rs.getInt("id"));
                match.setDate(rs.getDate("date"));
                match.setSetsNumber(rs.getInt("sets_number"));
                match.setIdPlayer1(rs.getInt("id_player1"));
                match.setIdPlayer2(rs.getInt("id_player2"));
                match.setIdMatchWinner(rs.getInt("id_match_winner"));

                matches.add(match);
            }
        }
        return matches;
    }

    public Match getMatch(int id) throws SQLException {
        Match match = null;
        String query = "SELECT * FROM MATCH_ WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                match = new Match();
                match.setId(rs.getInt("id"));
                match.setDate(rs.getDate("date"));
                match.setSetsNumber(rs.getInt("sets_number"));
                match.setIdPlayer1(rs.getInt("id_player1"));
                match.setIdPlayer2(rs.getInt("id_player2"));
                match.setIdMatchWinner(rs.getInt("id_match_winner"));
            }
        }
        return match;
    }

    public void updateMatch(Match match) throws SQLException {
        String query = "UPDATE MATCH_ SET id_player1 = ?, id_player2 = ?, id_match_winner = ? WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, match.getIdPlayer1());
            statement.setInt(2, match.getIdPlayer2());
            statement.setObject(3, match.getIdMatchWinner() != null ? match.getIdMatchWinner() : null, Types.INTEGER);
            statement.setInt(4, match.getId());

            statement.executeUpdate();
        }
    }

    public void deleteMatch(Match match) throws SQLException {
        String query = "DELETE FROM MATCH WHERE id = ?";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, match.getId());
            statement.executeUpdate();
        }
    }
}
