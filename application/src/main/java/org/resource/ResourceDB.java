package org.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ResourceDB class loads database configuration from a properties file.
 * This class is used to get database driver, URL, username, and password.
 */
public class ResourceDB {

    private String driver;
    private String url;
    private String user;
    private String password;
    private int maxSize;
    private int minSize;
    private int maxIdleTime;

    /**
     * Constructor: loads database properties from "application.properties" file.
     * Throws RuntimeException if the file cannot be loaded.
     */
    public ResourceDB() {
        try {
            Properties props = new Properties();
            InputStream input = ResourceDB.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(input);
            driver = props.getProperty("db.driver");
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            maxSize = Integer.parseInt(props.getProperty("db.pool.max-size"));
            minSize = Integer.parseInt(props.getProperty("db.pool.min-size"));
            maxIdleTime = Integer.parseInt(props.getProperty("db.pool.max-idle-time"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return the driver name (JDBC)
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Sets the database driver.
     *
     * @param driver
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     *
     * @return the database URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the database URL.
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return the database username
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the database username.
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return the database password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * Sets the database password.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the maximum pool size.
     *
     * @return maxSize
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * Sets the maximum pool size.
     *
     * @param maxSize
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Gets the minimum pool size.
     *
     * @return minSize
     */
    public int getMinSize() {
        return minSize;
    }

    /**
     * Sets the minimum pool size.
     *
     * @param minSize
     */
    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    /**
     * Gets the maximum idle time for connections in the pool.
     *
     * @return maxIdleTime
     */
    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    /**
     * Sets the maximum idle time for connections in the pool.
     *
     * @param maxIdleTime
     */
    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }
}
