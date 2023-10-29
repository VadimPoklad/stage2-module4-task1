package com.mjc.stage2.impl;



import com.mjc.stage2.ConnectionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class H2ConnectionFactory implements ConnectionFactory {

    public Connection createConnection() {
        Properties properties = new PropertyFileLoader("h2database.properties").getProperties();
        try {
            Class.forName(properties.getProperty("jdbc_driver"));
            return DriverManager.getConnection(properties.getProperty("db_url"),
                    properties.getProperty("user"),
                    properties.getProperty("password"));
        } catch (SQLException | ClassNotFoundException ignored) {}
        return null;
    }
}


class PropertyFileLoader {
    private final Properties properties = new Properties();
    public PropertyFileLoader(String filename){
        loadPropertyFile(filename);
    }

    private void loadPropertyFile(String filename){
        try (InputStream inputStream = PropertyFileLoader.class
                .getClassLoader()
                .getResourceAsStream(filename)
        ) {
            properties.load(inputStream);

        } catch (IOException ignored) {}
    }
    public Properties getProperties(){
        return properties;
    }
}
