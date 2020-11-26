package ru.ancndz.model;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

        record.setRegDuration(computeRecordDuration(record));

        record.computeTrafficScore();

        this.recordList.add(record);
        // при добавлении новой записи тут же проверяем ее
        if (higherTrafficRecord == null ||
                record.getTrafficScore() > higherTrafficRecord.getTrafficScore()
            ) {
            higherTrafficRecord = record;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Метод вычисления продолжительности записи
     * Если предыдущая запись с этой же камеры не найдена, считаем продолжительность - 1 час
     * @param inspectedRecord - исходная запись
     * @return java.time.Duration
     */
    private Duration computeRecordDuration(Record inspectedRecord) {
        //берем все записи с этой же камеры, сортируем в хрон. порядке и берем последний
        Optional<Record> previousRecord = this.recordList.stream()
                .filter(eachRecord -> eachRecord.getCameraID() == inspectedRecord.getCameraID())
                .max(Comparator.comparing(Record::getDateTime));

        Duration recordDuration = Duration.ofHours(1);
        //если предыдущая запись существует, берем разницу между ними; в противном случае - один час
        if (previousRecord.isPresent()) {
            //вычисляем период между прошлой датой снятия показаний и текущей
            recordDuration = Duration.between(previousRecord.get().getDateTime().toLocalTime(),
                    inspectedRecord.getDateTime().toLocalTime());
        }
        return recordDuration;
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
