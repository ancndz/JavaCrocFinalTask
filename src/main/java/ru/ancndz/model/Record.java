package ru.ancndz.model;

import java.time.LocalDateTime;

/**
 * Зарегистрированная запись с камер видеонаблюдения (пример)
 *
 * @author AntonU
 */
public class Record {

    /**
     * Уникальный ID камеры (как пример), с которой пришли результаты
     */
    private long cameraID;

    /**
     * Время снятия данных
     */
    private LocalDateTime dateTime;

    /**
     * Число автомобилей, которые камера зарегистрировала с прошлого промежутка регистрации
     */
    private int vehiclesReg;

    /**
     * Число зафиксированных ДТП с прошлого промежутка времени
     */
    private int accidentReg;

    public Record(long cameraID, LocalDateTime dateTime, int vehiclesReg, int accidentReg) {
        this.cameraID = cameraID;
        this.dateTime = dateTime;
        this.vehiclesReg = vehiclesReg;
        this.accidentReg = accidentReg;
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

    public int getVehiclesReg() {
        return vehiclesReg;
    }

    public void setVehiclesReg(int vehiclesReg) {
        this.vehiclesReg = vehiclesReg;
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
