package nl.han.oose.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionFactory {

    private static final String PROPERTY_LOCATION = "/database.properties";

    Connection getConnection() throws SQLException, SpotitubePersistenceException {
        var properties = loadProperties();
        return DriverManager.getConnection(properties.getProperty("db.url"),
                properties.getProperty("db.user"),
                properties.getProperty("db.password"));

    }

    private Properties loadProperties() throws SpotitubePersistenceException {
        Properties properties = new Properties();
        try (
                InputStream inputStream = this.getClass().getResourceAsStream(PROPERTY_LOCATION)
        ) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SpotitubePersistenceException("Properties file could not be read", e);
        }
        return properties;
    }
}
