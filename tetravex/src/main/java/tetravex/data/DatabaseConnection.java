package tetravex.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    public static void main(String[] args) {


        DatabaseConnection.getConnection();
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        PropertyReader.getDbURL(), PropertyReader.getDBUsername(), PropertyReader.getDBPassword());
            } catch (SQLException e) {
                throw new RuntimeException("Failed to connect to database", e);
            }
        }
        return connection;
    }
}
