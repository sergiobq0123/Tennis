package dao;

import dao.connector.Connector;
import models.Match;
import models.Set;
import models.Player;

import java.sql.*;
import java.util.ArrayList;

public class SetDao {
    
    private final Connector connector = new Connector();

    public boolean setExists(Set set) throws SQLException {
        boolean exists = false;
        String query = "SELECT * FROM SET_ WHERE id = ?";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, set.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                exists = true;
            }
        }
        return exists;
    }

    public void addSet(Set set) throws SQLException {
        String query = "INSERT INTO SET_ (id_player1, id_player2, id_match, id_set_winner) VALUES (?, ?, ?, ?)";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, set.getIdPlayer1());
            statement.setInt(2, set.getIdPlayer2());
            statement.setInt(3, set.getIdMatch());
            statement.setObject(4, set.getIdSetWinner() != null ? set.getIdSetWinner() : null, Types.INTEGER);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating set failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    set.setId(generatedId);
                } else {
                    throw new SQLException("Creating set failed, no ID obtained.");
                }
            }
        }
    }

    public ArrayList<Set> getAllSets() throws SQLException {
        ArrayList<Set> sets = new ArrayList<>();
        String query = "SELECT * FROM SET_";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Set set = new Set();
                set.setId(rs.getInt("id"));
                set.setIdPlayer1(rs.getInt("id_player1"));
                set.setIdPlayer2(rs.getInt("id_player2"));
                set.setIdMatch(rs.getInt("id_match"));
                set.setIdSetWinner(rs.getInt("id_set_winner"));
                sets.add(set);
            }
        }
        return sets;
    }

    public Set getSet(int id) throws SQLException {
        Set set = null;
        String query = "SELECT * FROM SET_ WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                set = new Set();
                set.setId(rs.getInt("id"));
                set.setIdPlayer1(rs.getInt("id_player1"));
                set.setIdPlayer2(rs.getInt("id_player2"));
                set.setIdMatch(rs.getInt("id_match"));
                set.setIdSetWinner(rs.getInt("id_set_winner"));

            }
        }
        return set;
    }

    public void updateSet(Set set) throws SQLException {
        String query = "UPDATE SET_ SET id_player1 = ?, id_player2 = ?, id_match = ?, id_set_winner = ? WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, set.getIdPlayer1());
            statement.setInt(2, set.getIdPlayer2());
            statement.setInt(3, set.getIdMatch());
            statement.setObject(4, set.getIdSetWinner() != null ? set.getIdSetWinner() : null, Types.INTEGER);
            statement.setInt(5, set.getId());

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

    public String getSetsByMatch(Match match) throws SQLException {
        String totalSets= "";
        String query = "SELECT COUNT(*) AS total FROM SET_ WHERE id_match = ? AND id_set_winner = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, match.getId());
            statement.setInt(2, match.getIdPlayer1());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                totalSets += rs.getInt("total");  // Sumar el total de apariciones de player1
            }
            statement.setInt(2, match.getIdPlayer2());
            rs = statement.executeQuery();
            if (rs.next()) {
                totalSets +="/" + rs.getInt("total");  // Sumar el total de apariciones de player1
            }
        }
        return  totalSets;
    }
    public ArrayList<Integer> getSetsIdByMatch(int id) throws SQLException {
        ArrayList<Integer> totalSets= new ArrayList<>();
        String query = "SELECT id FROM SET_ WHERE id_match =?";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                totalSets.add(rs.getInt("id"));
            }

        }
        return  totalSets;
    }
}