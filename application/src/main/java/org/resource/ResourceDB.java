package org.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceDB {

    private String driver;
    private String url;
    private String user;
    private String password;

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

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
