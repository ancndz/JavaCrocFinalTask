package ru.ancndz.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import java.io.IOException;

public class XMLConvertUtil {
    public static  <T> T fromXml(String xml, Class<T> type) throws IOException {
        XmlMapper mapper = createXmlMapper();
        return mapper.readValue(xml, type);
    }

    public static String toXml(Object obj) throws JsonProcessingException {
        XmlMapper mapper = createXmlMapper();
        return mapper.writeValueAsString(obj);
    }

    private static XmlMapper createXmlMapper() {
        final XmlMapper mapper = new XmlMapper();
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        mapper.registerModule(module);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
}
