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
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
