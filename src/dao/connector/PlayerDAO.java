package dao.connector;

import java.sql.*;

import models.Player;

public class PlayerDAO {

    private Connector connector = new Connector();

    public boolean playerExists(Player player) throws SQLException{
        boolean exists = false;
        String query = "SELECT * FROM users WHERE username = ?";
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
        String query = "INSERT INTO users (username) VALUES (?)";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, player.getName());
            statement.executeUpdate();
        }
    }
    public void updateUser(Player player) throws SQLException {
        String query = "UPDATE users SET username = ? WHERE id_usuario = ?";
        try(Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, player.getName());
            statement.setInt(2, player.getId());
            statement.executeUpdate();
        }
    }
}
