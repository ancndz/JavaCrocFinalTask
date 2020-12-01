package ru.ancndz.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.ancndz.databaseprovider.DataSourceProvider;
import ru.ancndz.model.Record;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RecordRepositoryTest {

    Record testRecord = new Record(5, 4,
                                 LocalDateTime.now().minusMinutes(20), LocalDateTime.now().plusMinutes(20));

    static RecordRepository recordRepository;

    @BeforeAll
    static void prepareConnect() throws IOException {
        DataSourceProvider dataSourceProvider;
        try {
            dataSourceProvider = new DataSourceProvider();
        } catch (IOException e) {
            System.out.println("Ошибка создания провайдера: " + e.getMessage());
            throw e;
        }
        recordRepository = new RecordRepository(dataSourceProvider.getDataSource());
    }

    @AfterEach
    void cleanUp() {
        recordRepository.deleteAll();
    }

    @Test
    void addRecord() {
        // добавляем
        int id = recordRepository.addRecord(this.testRecord);
        List<Record> recordList = recordRepository.findAll();
        // находим
        Optional<Record> record = recordList.stream().filter(r -> r.getID().equals(id)).findFirst();
        assertTrue(record.isPresent());
        // она соответсвует данной
        assertEquals(this.testRecord, record.get());
    }

    @Test
    void findAll() {
        List<Record> recordList = recordRepository.findAll();
        assertTrue(recordList.isEmpty());
    }

    @Test
    void updateRecord() {
        // добавляем запись
        int id = recordRepository.addRecord(this.testRecord);
        List<Record> recordList = recordRepository.findAll();
        // находим ее
        Optional<Record> record = recordList.stream().filter(r -> r.getID().equals(id)).findFirst();

        assertTrue(record.isPresent());
        // меняем
        record.get().setTrafficRating(99);
        // обновляем
        recordRepository.updateRecord(record.get());
        recordList = recordRepository.findAll();
        // снова получаем
        record = recordList.stream().filter(r -> r.getID().equals(id)).findFirst();
        assertTrue(record.isPresent());
        // проверяем
        assertEquals(99, record.get().getTrafficRating());
    }

    @Test
    void deleteById() {
        // добавляем запись
        int id = recordRepository.addRecord(this.testRecord);
        List<Record> recordList = recordRepository.findAll();
        // проверяем наличие записи
        assertTrue(recordList.contains(this.testRecord));

        // удаляем эту же запись
        recordRepository.deleteById(id);
        recordList = recordRepository.findAll();
        // проверяем, что записей больше нет
        assertTrue(recordList.isEmpty());
    }
}