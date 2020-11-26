package ru.ancndz.model;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RecordVaultTest {

    private final RecordVault recordVault = new RecordVault(new ArrayList<>());

    // Трафик = 16
    private final Record testRecordCamera1 = new Record(
            1L, LocalDateTime.now(), 1000, 4
    );

    // Трафик = 83
    private final Record testRecordCamera2 = new Record(
            2L, LocalDateTime.now().plusMinutes(2), 5000, 2
    );

    // Трафик = 66
    private final Record testRecordCamera1Future = new Record(
            1L, LocalDateTime.now().plusMinutes(30), 2000, 4
    );

    // Трафик = 200
    private final Record testRecordCamera2Future = new Record(
            2L, LocalDateTime.now().plusMinutes(32), 6000, 7
    );

    @Test
    public void testAddRecord() {
        this.recordVault.addRecord(testRecordCamera1);
        // Добавляем первую запись с камеры 1
        Assertions.assertEquals(testRecordCamera1, this.recordVault.getHigherTrafficRecord());

        this.recordVault.addRecord(testRecordCamera2);
        // Добавляем первую запись с камеры 2, лидер изменился
        Assertions.assertEquals(testRecordCamera2, this.recordVault.getHigherTrafficRecord());
        // Сравним рейтинги:
        Assertions.assertTrue(this.recordVault.getHigherTrafficRecord().getTrafficScore() >
                testRecordCamera1.getTrafficScore());

        this.recordVault.addRecord(testRecordCamera1Future);
        // Добавляем следующую запись с камеры 1, лидер не изменился
        Assertions.assertNotEquals(testRecordCamera1Future, this.recordVault.getHigherTrafficRecord());
        // Сравним рейтинги:
        Assertions.assertTrue(this.recordVault.getHigherTrafficRecord().getTrafficScore() >
                testRecordCamera1Future.getTrafficScore());

        this.recordVault.addRecord(testRecordCamera2Future);
        // Добавляем новую запись с камеры 2, лидер изменился
        Assertions.assertEquals(testRecordCamera2Future, this.recordVault.getHigherTrafficRecord());
        // Сравним рейтинги:
        Assertions.assertTrue(this.recordVault.getHigherTrafficRecord().getTrafficScore() >
                testRecordCamera2.getTrafficScore());
    }

}