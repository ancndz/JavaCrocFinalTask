package ru.ancndz.xmlConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ancndz.objects.Record;
import ru.ancndz.objects.RecordVault;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class JAXBConverterTest {

    private RecordVault recordVault;

    private Record testRecord;

    private final JAXBConverter jaxbConverter = new JAXBConverter();

    private String xmlString;

    @BeforeEach
    public void initTest() {
        this.recordVault = new RecordVault(new ArrayList<>());
        this.testRecord = new Record(5, 2, LocalDateTime.now().minusMinutes(30), LocalDateTime.now().plusMinutes(30));
    }

    @Test
    public void testFromXml() throws IOException {
        this.recordVault.addRecord(this.testRecord);
        this.xmlString = jaxbConverter.toXml(this.recordVault.getHigherTrafficRecord());
        Record recordFromXml = jaxbConverter.fromXml(this.xmlString, Record.class);
        Assertions.assertEquals(this.recordVault.getHigherTrafficRecord(), recordFromXml);
    }

    @Test
    public void testToXml() throws JsonProcessingException {
        this.recordVault.addRecord(this.testRecord);
        this.xmlString = jaxbConverter.toXml(this.recordVault.getHigherTrafficRecord());
        System.out.println(xmlString);
    }
}