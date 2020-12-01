package ru.ancndz.service;

import ru.ancndz.utils.XMLSaveUtil;
import ru.ancndz.databaseprovider.DataSourceProvider;
import ru.ancndz.repository.RecordRepository;
import ru.ancndz.model.Record;
import ru.ancndz.recordtracker.RecordVault;
import ru.ancndz.utils.XMLConvertUtil;

import java.io.IOException;
import java.util.List;

/**
 * Сервис для работы с записями
 */
public class TrackerService {
    RecordRepository recordRepository;
    RecordVault recordVault;

    public TrackerService() {
        recordRepository = prepareConnect();
        recordVault = new RecordVault();
    }

    private RecordRepository prepareConnect() {
        DataSourceProvider dataSourceProvider;
        try {
            dataSourceProvider = new DataSourceProvider();
            return new RecordRepository(dataSourceProvider.getDataSource());
        } catch (IOException e) {
            System.out.println("Ошибка создания провайдера: " + e.getMessage());
            return null;
        }
    }

    /**
     * Получение записи с наибольшим рейтингом трафика
     * @return Record
     */
    public Record getHighestTrafficRecord() {
        if (recordRepository == null) {
            return null;
        }
        List<Record> recordList = recordRepository.findAll();
        recordVault.addAllRecords(recordList);

        Record winner = recordVault.getHighestTrafficRecord();

        try {
            XMLSaveUtil.save(winner.getRecordDateTimeStartString(), XMLConvertUtil.toXml(winner));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return winner;
    }

    /**
     * Добавление {@link Record} в репозиторий {@link RecordRepository}
     * @param record
     */
    public void addValue(Record record) {
        this.recordRepository.addRecord(record);
    }
}
