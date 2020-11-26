package ru.ancndz;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.ancndz.IOServise.InputOutputXML;
import ru.ancndz.databaseService.provider.DataSourceProvider;
import ru.ancndz.databaseService.repository.RecordRepository;
import ru.ancndz.objects.Record;
import ru.ancndz.objects.RecordVault;
import ru.ancndz.xmlConverter.JAXBConverter;

import java.io.IOException;
import java.util.List;

public class Core {
    RecordRepository recordRepository;
    RecordVault recordVault;
    JAXBConverter jaxbConverter;
    InputOutputXML inputOutputXML;

    public Core() {
        recordRepository = prepareConnect();
        recordVault = new RecordVault();
        jaxbConverter = new JAXBConverter();
        inputOutputXML = new InputOutputXML();
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

    public Record getHighestTrafficRecord() {
        if (recordRepository == null) {
            return null;
        }
        List<Record> recordList = recordRepository.findAll();
        recordVault.addAllRecords(recordList);

        Record winner = recordVault.getHighestTrafficRecord();

        try {
            inputOutputXML.save(winner.getRecordDateTimeStartString(), jaxbConverter.toXml(winner));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return winner;
    }

    public void addValue(Record record) {
        this.recordRepository.addRecord(record);
    }
}
