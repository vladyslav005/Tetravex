package tetravex.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    public static void main(String[] args) {
        System.out.println(PropertyReader.getDbURL());
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        PropertyReader.getDbURL(), PropertyReader.getBdUsername(), PropertyReader.getBdUPassword());
            } catch (SQLException e) {
                throw new RuntimeException("Failed to connect to database");
            }
        }
        return connection;
    }
}
