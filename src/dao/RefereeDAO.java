package dao;

import dao.connector.Connector;
import models.Referee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RefereeDAO {
    private final Connector connector = new Connector();

    public boolean refereeExists(Referee referee) throws SQLException {
        boolean exists = false;
        String query = "SELECT * FROM REFEREE WHERE referee_name = ?";
        try(Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, referee.getName());
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                exists = true;
            }
        }
        return exists;
    }
    public void addReferee(Referee referee) throws SQLException {
        String query = "INSERT INTO REFEREE (referee_name,referee_password) VALUES (?,?)";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, referee.getName());
            statement.setString(2, referee.getPassword());
            statement.executeUpdate();
        }
    }

    public ArrayList<Referee> getAllReferees() throws SQLException {
        ArrayList<Referee> referees = new ArrayList<>();
        String query = "SELECT * FROM REFEREE";

        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_referee");
                String refereeName = rs.getString("referee_name");
                String refereePassword = rs.getString("referee_password");

                Referee referee = new Referee(id, refereeName, refereePassword);
                referees.add(referee);
            }
        }
        return referees;
    }
    public Referee getReferee(int id) throws SQLException {
        Referee referee = new Referee();
        String query = "SELECT * FROM REFEREE WHERE id_referee = ?";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                referee.setId(rs.getInt("id_referee"));
                referee.setName(rs.getString("referee_name"));
                referee.setPassword(rs.getString("referee_password"));
            }
        }
        return referee;
    }
    public Referee getRefereeByName(String name) throws SQLException {
        Referee referee = new Referee();
        String query = "SELECT * FROM REFEREE WHERE referee_name = ?";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                referee.setId(rs.getInt("id_referee"));
                referee.setName(rs.getString("referee_name"));
                referee.setPassword(rs.getString("referee_password"));
            }
        }
        return referee;
    }

    public void updateReferee(Referee referee) throws SQLException {
        String query = "UPDATE REFEREE SET referee_name = ?, referee_password = ? WHERE id_referee = ?";
        try(Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, referee.getName());
            statement.setString(2, referee.getPassword());
            statement.setInt(3, referee.getId());
            statement.executeUpdate();
        }
    }

    public void deleteReferee(Referee referee) throws SQLException {
        String query = "DELETE FROM REFEREE WHERE id_referee = ?";
        try(Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, referee.getId());
            statement.executeUpdate();
        }
    }
}
