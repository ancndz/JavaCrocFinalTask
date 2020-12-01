package ru.ancndz.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ancndz.model.Record;
import ru.ancndz.recordtracker.RecordVault;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class XMLConvertUtilTest {

    private RecordVault recordVault;

    private Record testRecord;

    private String xmlString;

    @BeforeEach
    public void initTest() {
        this.recordVault = new RecordVault(new ArrayList<>());
        this.testRecord = new Record(5, 2, LocalDateTime.now().minusMinutes(30), LocalDateTime.now().plusMinutes(30));
    }

    @Test
    public void testFromXml() throws IOException {
        this.recordVault.addRecord(this.testRecord);
        this.xmlString = XMLConvertUtil.toXml(this.recordVault.getHighestTrafficRecord());
        Record recordFromXml = XMLConvertUtil.fromXml(this.xmlString, Record.class);
        Assertions.assertEquals(this.recordVault.getHighestTrafficRecord(), recordFromXml);
    }

    @Test
    public void testToXml() throws JsonProcessingException {
        this.recordVault.addRecord(this.testRecord);
        this.xmlString = XMLConvertUtil.toXml(this.recordVault.getHighestTrafficRecord());
        System.out.println(xmlString);
    }
}