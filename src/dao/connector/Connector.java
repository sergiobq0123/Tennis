package dao.connector;

import java.sql.*;

public class Connector {
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/tenis";
    public static final String user = "root";
    public static final String password = "root";

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, user, password);
        } catch (SQLException e) {
            System.out.println("Failed to Connect to database");
            System.out.println("SQL State: "+e.getSQLState()+"\n"+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}

