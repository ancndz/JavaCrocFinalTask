package ru.ancndz.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Хранилище всех записей
 *
 * @author AntonU
 */
public class RecordVault {
    /**
     * Список всех заргестрированных записей
     */
    private final List<Record> recordList;

    /**
     * Запись с наибольшей загруженностью трафика
     */
    private Record highestTrafficRecord;

    public RecordVault() {
        this.recordList = new ArrayList<>();
        this.highestTrafficRecord = null;
    }

    public RecordVault(List<Record> recordList) {
        this.recordList = recordList;
        this.highestTrafficRecord = null;
    }

    public RecordVault(List<Record> recordList, Record highestTrafficRecord) {
        this.recordList = recordList;
        this.highestTrafficRecord = highestTrafficRecord;
    }

    /**
     * Метод добавления записи
     * @param record - зарегистрированная запись
     * @return - Boolean, true если higherTrafficRecord изменилась, false в противном случае
     */
    public Boolean addRecord(Record record) {
        this.recordList.add(record);
        // при добавлении новой записи тут же проверяем ее
        if (highestTrafficRecord == null ||
                record.getTrafficRating() > highestTrafficRecord.getTrafficRating() || (
                (record.getTrafficRating() == highestTrafficRecord.getTrafficRating()) &&
                        (record.getTrafficAccidents() > highestTrafficRecord.getTrafficAccidents()))
            ) {
            highestTrafficRecord = record;
            return true;
        } else {
            return false;
        }
    }

    public void addAllRecords(List<Record> recordList) {
        for (Record eachRecord: recordList) {
            addRecord(eachRecord);
        }
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public Record getHighestTrafficRecord() {
        return highestTrafficRecord;
    }
}
