package ru.ancndz.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ancndz.recordtracker.RecordVault;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RecordVaultTest {

    private RecordVault recordVault;

    private Record testRecord1;

    private Record testRecord2;

    private Record testRecord3;

    private Record testRecord4;

    @BeforeEach
    public void initTest() {
        recordVault = new RecordVault(new ArrayList<>());

        this.testRecord1 = new Record(3, 5, LocalDateTime.now().minusMinutes(50), LocalDateTime.now().minusMinutes(40));
        this.testRecord2 = new Record(6, 2, LocalDateTime.now().minusMinutes(30), LocalDateTime.now().minusMinutes(20));
        this.testRecord3 = new Record(4, 7, LocalDateTime.now().plusMinutes(10), LocalDateTime.now().plusMinutes(20));
        this.testRecord4 = new Record(6, 9, LocalDateTime.now().plusMinutes(30), LocalDateTime.now().plusMinutes(40));

    }

    @Test
    public void testAddRecord() {
        this.recordVault.addRecord(testRecord1);
        // Добавляем первую запись
        Assertions.assertEquals(testRecord1, this.recordVault.getHighestTrafficRecord());

        this.recordVault.addRecord(testRecord2);
        // Добавляем первую запись, лидер изменился
        Assertions.assertEquals(testRecord2, this.recordVault.getHighestTrafficRecord());
        // Сравним рейтинги:
        Assertions.assertTrue(this.recordVault.getHighestTrafficRecord().getTrafficRating() >
                testRecord1.getTrafficRating());

        this.recordVault.addRecord(testRecord3);
        // Добавляем следующую запись, лидер не изменился
        Assertions.assertNotEquals(testRecord3, this.recordVault.getHighestTrafficRecord());
        // Сравним рейтинги:
        Assertions.assertTrue(this.recordVault.getHighestTrafficRecord().getTrafficRating() >
                testRecord3.getTrafficRating());

        this.recordVault.addRecord(testRecord4);
        // Добавляем новую запись, лидер изменился
        Assertions.assertEquals(testRecord4, this.recordVault.getHighestTrafficRecord());
        // Сравним рейтинги:
        Assertions.assertEquals(testRecord2.getTrafficRating(), this.recordVault.getHighestTrafficRecord().getTrafficRating());
        Assertions.assertTrue(this.recordVault.getHighestTrafficRecord().getTrafficAccidents() > testRecord2.getTrafficAccidents());
    }

}