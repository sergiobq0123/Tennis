package dao;
import dao.connector.Connector;
import models.Point;
import java.sql.*;
import java.util.ArrayList;

public class PointDAO {

    private final Connector connector = new Connector();

    public boolean pointExists(Point point) throws SQLException {
        boolean exists = false;
        String query = "SELECT * FROM points WHERE id = ?";
        try (Connection conn = connector.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, point.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                exists = true;
            }
        }
        return exists;
    }

    public void addPoint(Point point) throws SQLException {
        String query = "INSERT INTO POINT (id_player1, id_player2, score_player1, score_player2, id_player_service, id_point_winner, id_game) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, point.getIdPlayer1());
            statement.setInt(2, point.getIdPlayer2());
            statement.setString(3, point.getScorePlayer1());
            statement.setString(4, point.getScorePlayer2());
            statement.setInt(5, point.getIdPlayerService());
            statement.setObject(6, point.getIdPointWinner() != null ? point.getIdPointWinner() : null, Types.INTEGER);
            statement.setInt(7, point.getIdGame());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating point failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    point.setId(generatedId);
                } else {
                    throw new SQLException("Creating point failed, no ID obtained.");
                }
            }
        }
    }

    public Point getPoint(int id) throws SQLException {
        Point point = null;
        String query = "SELECT * FROM POINT WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                point = new Point();
                point.setId(rs.getInt("id"));
                point.setIdPlayer1(rs.getInt("id_player1"));
                point.setIdPlayer2(rs.getInt("id_player2"));
                point.setScorePlayer1(rs.getString("score_player1"));
                point.setScorePlayer2(rs.getString("score_player2"));
                point.setIdPlayerService(rs.getInt("id_player_service"));
                point.setIdPointWinner(rs.getInt("id_point_winner"));
                point.setIdGame(rs.getInt("id_game"));
            }
        }
        return point;
    }

    public ArrayList<Point> getAllPoints() throws SQLException {
        ArrayList<Point> points = new ArrayList<>();
        String query = "SELECT * FROM POINT";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Point point = new Point();
                point.setId(rs.getInt("id"));
                point.setIdPlayer1(rs.getInt("id_player1"));
                point.setIdPlayer2(rs.getInt("id_player2"));
                point.setScorePlayer1(rs.getString("score_player1"));
                point.setScorePlayer2(rs.getString("score_player2"));
                point.setIdPlayerService(rs.getInt("id_player_service"));
                point.setIdPointWinner(rs.getInt("id_point_winner"));
                point.setIdGame(rs.getInt("id_game"));
                points.add(point);
            }
        }
        return points;
    }

    public void updatePoint(Point point) throws SQLException {
        String query = "UPDATE POINT SET id_player1 = ?, id_player2 = ?, score_player1 = ?, score_player2 = ?, id_player_service = ?, id_point_winner = ?, id_game = ? WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, point.getIdPlayer1());
            statement.setInt(2, point.getIdPlayer2());
            statement.setString(3, point.getScorePlayer1());
            statement.setString(4, point.getScorePlayer2());
            statement.setInt(5, point.getIdPlayerService());
            statement.setInt(6, point.getIdPointWinner());
            statement.setInt(7, point.getIdGame());
            statement.setInt(8, point.getId());
            statement.executeUpdate();
        }
    }

    public void deletePoint(int id) throws SQLException {
        String query = "DELETE FROM POINT WHERE id = ?";
        try (Connection conn = connector.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

}


