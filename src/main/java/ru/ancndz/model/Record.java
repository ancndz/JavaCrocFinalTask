package ru.ancndz.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Зарегистрированная запись с камер видеонаблюдения (пример)
 *
 * @author AntonU
 */
@XmlRootElement
public class Record {

    /**
     * Уникальный ID камеры (как пример), с которой пришли результаты
     */
    @XmlAttribute
    private long cameraID;

    /**
     * Время снятия данных
     */
    @XmlAttribute(name = "date")
    private LocalDateTime dateTime;

    @XmlAttribute(name = "duration of record")
    private Duration regDuration;

    /**
     * Число автомобилей, которые камера зарегистрировала с прошлого промежутка регистрации
     */
    @XmlTransient
    private int vehiclesReg;

    /**
     * Рейтинг трафика
     */
    @XmlElement(name = "Jams score")
    private int trafficScore;

    /**
     * Число зафиксированных ДТП с прошлого промежутка времени
     */
    @XmlElement(name = "Accidents")
    private int accidentReg;

    public Record(long cameraID, LocalDateTime dateTime, int vehiclesReg, int accidentReg) {
        this.cameraID = cameraID;
        this.dateTime = dateTime;
        this.vehiclesReg = vehiclesReg;
        this.accidentReg = accidentReg;
    }

    /**
     * Метод оценки трафика
     * Формула: (число машин / время (в минутах) записи)
     */
    public void computeTrafficScore() {
        if (this.regDuration != null && this.vehiclesReg != 0) {
            this.setTrafficScore((int) (this.getVehiclesReg() / (this.getRegDuration().getSeconds() / 60)));
        }
    }

    public long getCameraID() {
        return cameraID;
    }

    public void setCameraID(long cameraID) {
        this.cameraID = cameraID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Duration getRegDuration() {
        return regDuration;
    }

    public void setRegDuration(Duration regDuration) {
        this.regDuration = regDuration;
    }

    public int getVehiclesReg() {
        return vehiclesReg;
    }

    public void setVehiclesReg(int vehiclesReg) {
        this.vehiclesReg = vehiclesReg;
    }

    public int getTrafficScore() {
        return trafficScore;
    }

    public void setTrafficScore(int trafficScore) {
        this.trafficScore = trafficScore;
    }

    public int getAccidentReg() {
        return accidentReg;
    }

    public void setAccidentReg(int accidentReg) {
        this.accidentReg = accidentReg;
    }

    @Override
    public String toString() {
        return "Record {" +
                "\n\tcameraID = " + cameraID +
                "\n\t, dateTime = " + dateTime +
                "\n\t, vehiclesReg = " + vehiclesReg +
                "\n\t, accidentReg = " + accidentReg +
                "\n}";
    }
}
