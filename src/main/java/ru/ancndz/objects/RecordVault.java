package ru.ancndz.objects;

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
    private Record higherTrafficRecord;

    public RecordVault(List<Record> recordList) {
        this.recordList = recordList;
        this.higherTrafficRecord = null;
    }

    public RecordVault(List<Record> recordList, Record higherTrafficRecord) {
        this.recordList = recordList;
        this.higherTrafficRecord = higherTrafficRecord;
    }

    /**
     * Метод добавления записи
     * @param record - зарегистрированная запись
     * @return - Boolean, true если higherTrafficRecord изменилась, false в противном случае
     */
    public Boolean addRecord(Record record) {
        this.recordList.add(record);
        // при добавлении новой записи тут же проверяем ее
        if (higherTrafficRecord == null ||
                record.getTrafficRating() > higherTrafficRecord.getTrafficRating() || (
                (record.getTrafficRating() == higherTrafficRecord.getTrafficRating()) &&
                        (record.getTrafficAccidents() > higherTrafficRecord.getTrafficAccidents()))
            ) {
            higherTrafficRecord = record;
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

    public Record getHigherTrafficRecord() {
        return higherTrafficRecord;
    }
}
