package ru.ancndz.databaseService.provider;

import org.postgresql.ds.PGSimpleDataSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataSourceProvider {
    private PGSimpleDataSource pgSimpleDataSource = null;
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

    public PGSimpleDataSource getDataSource() {
        if (pgSimpleDataSource == null) {
            pgSimpleDataSource = new PGSimpleDataSource();
            pgSimpleDataSource.setURL(properties.get("db_url"));
            pgSimpleDataSource.setUser(properties.get("db_user"));
            pgSimpleDataSource.setPassword(properties.get("db_pass"));
            pgSimpleDataSource.setDatabaseName(properties.get("db_name"));
        }
        return pgSimpleDataSource;
    }

}