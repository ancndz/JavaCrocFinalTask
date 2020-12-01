package ru.ancndz.repository;

import org.apache.derby.jdbc.EmbeddedDataSource;
import ru.ancndz.databaseprovider.PrepareTable;
import ru.ancndz.model.Record;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Репозиторий для {@link Record}
 */
public class RecordRepository {
    private final EmbeddedDataSource dataSource;

    public RecordRepository(EmbeddedDataSource dataSource) {
        this.dataSource = dataSource;
        PrepareTable.initRecordTable(dataSource);
    }

    private void printQueryException(Exception e) {
        System.out.println("Ошибка выполнения запроса: " + e.getMessage());
    }

    /**
     * Заполнение statement с нужными данными Record
     * @param record {@link Record} с данными
     * @param statement {@link PreparedStatement} - statement для заполнения
     * @throws SQLException
     */
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

    /**
     * Создание списка {@link Record} из {@link ResultSet}
     * @param resultSet {@link ResultSet}, который пришел из запроса
     * @return List of Record
     * @throws SQLException
     */
    private List<Record> createRecordFromResultSet(ResultSet resultSet) throws SQLException {
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

    /**
     * Добавление записи в БД
     * @param record {@link Record}
     * @return Integer - ID записанного {@link Record}, null при ошибке
     */
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

    /**
     * Получение всех строк из таблицы
     * @return
     */
    public List<Record> findAll() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + Record.TABLE_NAME);
            return createRecordFromResultSet(resultSet);
        } catch (Exception e) {
            printQueryException(e);
        }
        return new ArrayList<>();
    }

    /**
     * Обновление заданной записи
     * Если у нее нет ID (она была создана не из данных от БД), она записывается туда как новая
     * @param record обновляемая {@link Record}
     * @return Integer ID записи
     */
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

    /**
     * Удаление записи по ее ID
     * @param id {@link Integer} ID записи ({@link Record})
     */
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

    /**
     * Полная очистка таблицы
     * @return true если очистка успешно завершилась, false в ином случае
     */
    public Boolean deleteAll() {
        String sqlQuery = "truncate table " + Record.TABLE_NAME;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.execute();
            return true;
        } catch (SQLException e) {
            printQueryException(e);
            return false;
        }
    }
}
