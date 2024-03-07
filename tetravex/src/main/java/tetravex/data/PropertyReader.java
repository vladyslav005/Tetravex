package tetravex.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static Properties prop = null;
    public static String getDbURL() {
        if (prop == null) getPropSource();

        return new StringBuilder("jdbc:postgresql://")
                .append(prop.getProperty("postgres.db.host"))
                .append(":")
                .append(prop.getProperty("postgres.db.port"))
                .append("/")
                .append(prop.getProperty("postgres.db.database"))
                .append("?currentSchema=public")
                .toString();
    }

    public static String getDBUsername() {
        if (prop == null) getPropSource();
        return prop.getProperty("postgres.db.username");
    }

    public static String getDBPassword() {
        if (prop == null) getPropSource();
        return prop.getProperty("postgres.db.password");
    }

    private static void getPropSource() {
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("application.properties")) {

            if (input == null)
                throw new RuntimeException("Unable to load properties");
            prop = new Properties();
            prop.load(input);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}