package tetravex.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private String source = "application.properties";

    public PropertyReader(String source) {
        this.source = source;
    }

    public PropertyReader() {
    }

    public String getDbURL() {
        Properties prop = getPropSource();

        return new StringBuilder("jdbc:postgresql://")
                .append(prop.getProperty("postgres.db.host"))
                .append(":")
                .append(prop.getProperty("postgres.db.port"))
                .append("/")
                .append(prop.getProperty("postgres.db.database"))
                .append("?currentSchema=public")
                .toString();
    }

    public String getDBUsername() {
        Properties prop = getPropSource();

        if (prop == null) getPropSource();
        return prop.getProperty("postgres.db.username");
    }

    public String getDBPassword() {
        Properties prop = getPropSource();

        if (prop == null) getPropSource();
        return prop.getProperty("postgres.db.password");
    }

    private Properties getPropSource() {
        Properties prop;
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream(source)) {

            if (input == null)
                throw new RuntimeException("Unable to load properties");
            prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}