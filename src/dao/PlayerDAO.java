package dao;

import java.sql.*;
import java.util.ArrayList;

import dao.connector.Connector;
import models.Player;

public class PlayerDAO {

    private final Connector connector = new Connector();

    public boolean playerExists(Player player) throws SQLException{
        boolean exists = false;
        String query = "SELECT * FROM PLAYER WHERE player_name = ?";
        try(Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, player.getName());
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                exists = true;
            }
        }
        return exists;
    }
    public void addPlayer(Player player) throws SQLException {
        String query = "INSERT INTO PLAYER (player_name) VALUES (?)";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, player.getName());
            statement.executeUpdate();
        }
    }
    public ArrayList<Player> getAllPlayers() throws SQLException {
        ArrayList<Player> players = new ArrayList<>();
        String query = "SELECT * FROM PLAYER";

        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_player");
                String player_name = rs.getString("player_name");

                Player player = new Player(id, player_name);
                players.add(player);
            }
        }
        return players;
    }
    public Player getPlayer(int id) throws SQLException {
        Player player = new Player();
        String query = "SELECT * FROM PLAYER WHERE id_player = ?";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                 player.setId(rs.getInt("id_player"));
                 player.setName(rs.getString("player_name"));
                 System.out.println("Player retrieved: " + player.toString());
            }
        }
        return player;
    }

    public void updatePlayer(Player player) throws SQLException {
        String query = "UPDATE PLAYER SET player_name = ? WHERE id_player = ?";
        try(Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, player.getName());
            statement.setInt(2, player.getId());
            statement.executeUpdate();
        }
    }

    public void deletePlayer(Player player) throws SQLException {
        String query = "DELETE FROM PLAYER WHERE id_player = ?";
        try(Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, player.getId());
            statement.executeUpdate();
        }
    }
}
