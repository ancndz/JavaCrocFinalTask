package ru.ancndz.databaseprovider;

import ru.ancndz.model.Record;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Класс подготовки базы данных: создание таблиц, если их не существует
 */
public class PrepareTable {

    /**
     * Создание таблицы для {@link Record}
     * @param dataSource {@link DataSource}
     */
    public static void initRecordTable(DataSource dataSource) {
        System.out.printf("Start initializing %s table%n", Record.TABLE_NAME);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            DatabaseMetaData databaseMetadata = connection.getMetaData();
            ResultSet resultSet = databaseMetadata.getTables(
                    null,
                    null,
                    Record.TABLE_NAME.toUpperCase(),
                    new String[]{"TABLE"});
            if (resultSet.next()) {
                System.out.println("Table has already been initialized");
            } else {
                statement.executeUpdate(
                        "create table "
                                + Record.TABLE_NAME
                                + " ("
                                + "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                                + "traffic_rating int,"
                                + "accidents int," +
                                "start_date date," +
                                "start_time time," +
                                "end_date date," +
                                "end_time time"
                                + ")");
                System.out.println("Table was successfully initialized");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred during table initializing: " + e.getMessage());
        } finally {
            System.out.println("=========================");
        }
    }

}
