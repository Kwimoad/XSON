package org.database;

import org.resource.ResourceDB;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class manages the database connection using the Singleton pattern.
 */
public class DatabaseConnection {

    private static DatabaseConnection instance; // Singleton
    private Connection connection;

    /**
     * Private constructor to create the database connection.
     */
    private DatabaseConnection() {
        try {
            ResourceDB resourceDB = new ResourceDB();
            Class.forName(resourceDB.getDriver());
            connection = DriverManager.getConnection(
                    resourceDB.getUrl(),
                    resourceDB.getUser(),
                    resourceDB.getPassword()
            );
        } catch (Exception e) {
            e.printStackTrace();
            connection = null;
        }
    }

    /**
     * Get the single instance of DatabaseConnection (Singleton).
     *
     * @return DatabaseConnection instance
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Get the SQL connection.
     *
     * @return Connection object
     */
    public Connection getConnection() {
        return connection;
    }

}
