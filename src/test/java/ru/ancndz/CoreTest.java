package ru.ancndz;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ancndz.model.Record;

import java.time.LocalDateTime;

class CoreTest {

    Core core;
    Record testRecord1;
    Record testRecord2;
    Record testRecord3;
    Record testRecord4;


    @BeforeEach
    void setUp() {
        core = new Core();
        this.testRecord1 = new Record(3, 5, LocalDateTime.now().minusMinutes(50), LocalDateTime.now().minusMinutes(40));
        this.testRecord2 = new Record(6, 2, LocalDateTime.now().minusMinutes(30), LocalDateTime.now().minusMinutes(20));
        this.testRecord3 = new Record(4, 7, LocalDateTime.now().plusMinutes(10), LocalDateTime.now().plusMinutes(20));
        this.testRecord4 = new Record(6, 9, LocalDateTime.now().plusMinutes(30), LocalDateTime.now().plusMinutes(40));

    }

    @AfterEach
    void tearDown() {
        core.recordRepository.trimTable();
    }

    @Test
    void getHighestTrafficRecord() {
        core.addValue(testRecord1);
        Assertions.assertEquals(testRecord1, core.getHighestTrafficRecord());

        core.addValue(testRecord2);
        core.addValue(testRecord3);
        core.addValue(testRecord4);

        Assertions.assertEquals(testRecord4, core.getHighestTrafficRecord());
    }
}