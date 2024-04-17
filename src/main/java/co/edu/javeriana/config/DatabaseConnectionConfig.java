package co.edu.javeriana.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionConfig {
    private static Connection connection = null;

    private DatabaseConnectionConfig() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            Properties config = PropertiesConfig.readConfigFile();
            try {
                Class.forName(config.getProperty("driver"));
                connection = DriverManager.getConnection(
                        config.getProperty("url"),
                        config.getProperty("user"),
                        config.getProperty("password"));
            } catch (Exception exception) {
                throw new RuntimeException(exception.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new RuntimeException("Failed to close database connection", e);
            }
        }
    }
}
