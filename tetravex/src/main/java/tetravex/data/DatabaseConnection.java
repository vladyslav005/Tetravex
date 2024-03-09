package tetravex.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;


    public static Connection getConnection() {
        if (connection == null) {
            PropertyReader propertyReader = new PropertyReader();
            try {
                connection = DriverManager.getConnection(
                        propertyReader.getDbURL(), propertyReader.getDBUsername(), propertyReader.getDBPassword());
            } catch (SQLException e) {
                throw new RuntimeException("Failed to connect to database", e);
            }
        }
        return connection;
    }
}
