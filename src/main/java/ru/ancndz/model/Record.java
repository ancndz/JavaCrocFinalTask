package ru.ancndz.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Зарегистрированная запись
 *
 * @author AntonU
 */
@XmlRootElement
public class Record {

    @XmlTransient
    public static String TABLE_NAME = "records";

    @XmlTransient
    private Integer ID = null;

    /**
     * Ретинг пробок
     */
    @XmlElement(name = "Traffic")
    private int trafficRating;
    /**
     * Количество ДТП
     */
    @XmlElement(name = "Accidents")
    private int trafficAccidents;
    /**
     * Время начала записи
     */
    @XmlTransient
    private LocalDateTime recordDateTimeStart;
    /**
     * Время конца записи
     */
    @XmlTransient
    private LocalDateTime recordDateTimeEnd;
    /**
     * Время начала записи (строка)
     */
    @XmlAttribute(name = "Start_period")
    private String recordDateTimeStartString;
    /**
     * Время конца записи (строка)
     */
    @XmlAttribute(name = "End_period")
    private String recordDateTimeEndString;

    /**
     * Форматтер для конвертации времени в строку и обратно
     */
    @XmlTransient
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Record() {
    }

    public Record(int trafficRating, int trafficAccidents, LocalDateTime recordDateTimeStart, LocalDateTime recordDateTimeEnd) {
        this.trafficRating = trafficRating;
        this.trafficAccidents = trafficAccidents;
        this.recordDateTimeStart = recordDateTimeStart;
        this.recordDateTimeEnd = recordDateTimeEnd;
        this.recordDateTimeStartString = convertToString(recordDateTimeStart);
        this.recordDateTimeEndString = convertToString(recordDateTimeEnd);
    }

    public Record(int ID, int trafficRating, int trafficAccidents, LocalDateTime recordDateTimeStart, LocalDateTime recordDateTimeEnd) {
        this.ID = ID;
        this.trafficRating = trafficRating;
        this.trafficAccidents = trafficAccidents;
        this.recordDateTimeStart = recordDateTimeStart;
        this.recordDateTimeEnd = recordDateTimeEnd;
        this.recordDateTimeStartString = convertToString(recordDateTimeStart);
        this.recordDateTimeEndString = convertToString(recordDateTimeEnd);
    }

    private String convertToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }

    private LocalDateTime parseFromString(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    @XmlTransient
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public int getTrafficRating() {
        return trafficRating;
    }

    public void setTrafficRating(int trafficRating) {
        this.trafficRating = trafficRating;
    }

    public int getTrafficAccidents() {
        return trafficAccidents;
    }

    public void setTrafficAccidents(int trafficAccidents) {
        this.trafficAccidents = trafficAccidents;
    }

    public LocalDateTime getRecordDateTimeStart() {
        return recordDateTimeStart;
    }

    public void setRecordDateTimeStart(LocalDateTime recordDateTimeStart) {
        this.recordDateTimeStartString = convertToString(recordDateTimeStart);
        this.recordDateTimeStart = recordDateTimeStart;
    }

    public LocalDateTime getRecordDateTimeEnd() {
        return recordDateTimeEnd;
    }

    public void setRecordDateTimeEnd(LocalDateTime recordDateTimeEnd) {
        this.recordDateTimeEndString = convertToString(recordDateTimeEnd);
        this.recordDateTimeEnd = recordDateTimeEnd;
    }

    public String getRecordDateTimeStartString() {
        return recordDateTimeStartString;
    }

    public void setRecordDateTimeStartString(String recordDateTimeStartString) {
        this.recordDateTimeStart = parseFromString(recordDateTimeStartString);
        this.recordDateTimeStartString = recordDateTimeStartString;
    }

    public String getRecordDateTimeEndString() {
        return recordDateTimeEndString;
    }

    public void setRecordDateTimeEndString(String recordDateTimeEndString) {
        this.recordDateTimeEnd = parseFromString(recordDateTimeEndString);
        this.recordDateTimeEndString = recordDateTimeEndString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;
        Record record = (Record) o;
        return trafficRating == record.trafficRating &&
                trafficAccidents == record.trafficAccidents &&
                recordDateTimeStartString.equals(record.recordDateTimeStartString) &&
                recordDateTimeEndString.equals(record.recordDateTimeEndString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trafficRating, trafficAccidents, recordDateTimeStartString, recordDateTimeEndString);
    }
}
