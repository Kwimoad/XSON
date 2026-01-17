package org.database;

import org.resource.ResourceDB;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class manages the database connection using DataSource and Singleton pattern.
 */
public class DatabaseConnection {

    private static DatabaseConnection instance; // Singleton
    private DataSource dataSource;

    /**
     * Private constructor to configure the DataSource.
     */
    private DatabaseConnection() {
        try {
            ResourceDB resourceDB = new ResourceDB();

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(resourceDB.getUrl());
            config.setUsername(resourceDB.getUser());
            config.setPassword(resourceDB.getPassword());
            config.setMaximumPoolSize(resourceDB.getMaxSize());
            config.setMinimumIdle(resourceDB.getMinSize());
            config.setIdleTimeout(resourceDB.getMaxIdleTime() * 1000L);
            config.setMaxLifetime(resourceDB.getMaxIdleTime() * 2000L);

            // Création du datasource Hikari
            this.dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            e.printStackTrace();
            this.dataSource = null;
        }
    }

    /**
     * Get the single instance of DatabaseConnection (Singleton).
     */
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Get a SQL connection from DataSource.
     */
    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource is not initialized");
        }
        return dataSource.getConnection();
    }
    
}