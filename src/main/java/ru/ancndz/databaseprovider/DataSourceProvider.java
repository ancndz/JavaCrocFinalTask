package ru.ancndz.databaseprovider;

import org.apache.derby.jdbc.EmbeddedDataSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataSourceProvider {
    private EmbeddedDataSource dataSource = null;
    private final Map<String, String> properties = new HashMap<>();
    
    public DataSourceProvider() throws IOException {
        loadProperties();
    }

    private void loadProperties() throws IOException {
        Properties properties = new Properties();
        try {
            properties.load(
                    Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                this.properties.put((String) entry.getKey(), (String) entry.getValue());
            }
        } catch (Exception e) {
            System.out.println("Error occurred during loading properties!");
            throw e;
        }
    }

    public EmbeddedDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new EmbeddedDataSource();
            dataSource.setUser(properties.get("db_user"));
            dataSource.setPassword(properties.get("db_pass"));
            dataSource.setDatabaseName(properties.get("db_name"));
        }
        return dataSource;
    }



}