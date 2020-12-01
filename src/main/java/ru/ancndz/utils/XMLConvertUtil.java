package ru.ancndz.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import java.io.IOException;

/**
 * Утилитарный класс для работы с XML
 */
public class XMLConvertUtil {
    /**
     * Парсинг объекта из XML
     * @param xml String строка с XML
     * @param type Тип десерилезуемого объекта
     * @param <T> Возвращаемый тип (эквивалентен типу второго параметра)
     * @return Десериализованный объект
     * @throws IOException
     */
    public static <T> T fromXml(String xml, Class<T> type) throws IOException {
        XmlMapper mapper = createXmlMapper();
        return mapper.readValue(xml, type);
    }

    /**
     * Сериализация объекта в XML
     * @param obj объект
     * @return String XML
     * @throws JsonProcessingException
     */
    public static String toXml(Object obj) throws JsonProcessingException {
        XmlMapper mapper = createXmlMapper();
        return mapper.writeValueAsString(obj);
    }

    /**
     * Настройка маппера
     * @return
     */
    private static XmlMapper createXmlMapper() {
        final XmlMapper mapper = new XmlMapper();
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        mapper.registerModule(module);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
}
