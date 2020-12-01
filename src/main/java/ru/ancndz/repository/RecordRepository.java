package ru.ancndz.repository;

import org.apache.derby.jdbc.EmbeddedDataSource;
import ru.ancndz.model.Record;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RecordRepository {
    private final EmbeddedDataSource dataSource;

    public RecordRepository(EmbeddedDataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }


    private void initTable() {
        System.out.printf("Start initializing %s table%n", Record.TABLE_NAME);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            DatabaseMetaData databaseMetadata = connection.getMetaData();
            ResultSet resultSet = databaseMetadata.getTables(
                    null,
                    null,
                    Record.TABLE_NAME,
                    new String[]{"TABLE"});
            if (resultSet.next()) {
                System.out.println("Table has already been initialized");
            } else {
                statement.executeUpdate(
                        "create table "
                                + Record.TABLE_NAME
                                + " ("
                                + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) primary key, "
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

    private void printQueryException(Exception e) {
        System.out.println("Ошибка выполнения запроса: " + e.getMessage());
    }

    private void setStatementValues(Record record, PreparedStatement statement) throws SQLException {
        LocalDate start_date = record.getRecordDateTimeStart().toLocalDate();
        LocalTime start_time = record.getRecordDateTimeStart().toLocalTime();
        LocalDate end_date = record.getRecordDateTimeEnd().toLocalDate();
        LocalTime end_time = record.getRecordDateTimeEnd().toLocalTime();
        statement.setInt(1, record.getTrafficRating());
        statement.setInt(2, record.getTrafficAccidents());
        statement.setDate(3, Date.valueOf(start_date));
        statement.setTime(4, Time.valueOf(start_time));
        statement.setDate(5, Date.valueOf(end_date));
        statement.setTime(6, Time.valueOf(end_time));
    }

    private List<Record> createRecord(ResultSet resultSet) throws SQLException {
        List<Record> records = new ArrayList<>();
        while (resultSet.next()) {
            records.add(new Record(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    LocalDateTime.of(resultSet.getDate(4).toLocalDate(), resultSet.getTime(5).toLocalTime()),
                    LocalDateTime.of(resultSet.getDate(6).toLocalDate(), resultSet.getTime(7).toLocalTime())
            ));
        }
        return records;
    }

    public Integer addRecord(Record record) {
        String sqlQuery = "insert into " + Record.TABLE_NAME + "(" +
                "traffic_rating, accidents, start_date, start_time, end_date, end_time)" +
                " values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            setStatementValues(record, statement);
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            printQueryException(e);
            return null;
        }
    }

    public List<Record> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + Record.TABLE_NAME);
            return createRecord(resultSet);
        } catch (Exception e) {
            printQueryException(e);
        }
        return new ArrayList<>();
    }

    public Integer updateRecord(Record record) {
        if (record.getID() == null) {
            return addRecord(record);
        }
        String sqlQuery = "update " + Record.TABLE_NAME + " set traffic_rating = ?," +
                "accidents = ?, " +
                "start_date = ?, " +
                "start_time = ?, " +
                "end_date = ?, " +
                "end_time = ? " +
                "where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            setStatementValues(record, statement);
            statement.setInt(7, record.getID());
            statement.execute();
            return record.getID();
        } catch (SQLException e) {
            printQueryException(e);
            return null;
        }
    }

    public void deleteById(Integer id) {
        String sqlQuery = "delete from " + Record.TABLE_NAME + " where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            printQueryException(e);
        }
    }

    public void trimTable() {
        String sqlQuery = "truncate " + Record.TABLE_NAME + " RESTART IDENTITY";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.execute();
        } catch (SQLException e) {
            printQueryException(e);
        }
    }
}
