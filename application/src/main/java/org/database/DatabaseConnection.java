package org.database;

import org.resource.ResourceDB;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static DatabaseConnection instance; // Singleton
    private Connection connection;

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

    // Création de l’instance unique
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Accès à la connexion
    public Connection getConnection() {
        return connection;
    }
}
