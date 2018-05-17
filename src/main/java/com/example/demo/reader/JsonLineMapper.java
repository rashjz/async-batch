package com.example.demo.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
 import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.LineMapper;

import java.io.IOException;

@Slf4j
public class JsonLineMapper<T> implements LineMapper<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> clazz;

    public JsonLineMapper(final ObjectMapper objectMapper, final Class<T> clazz) {
        this.objectMapper = objectMapper;
        this.clazz = clazz;
    }


    @Override
    public T mapLine(String item, int lineNumber) throws Exception {
        try {
            return objectMapper.readValue(item, clazz);
        } catch (IOException e) {
            log.error("Error while retrieving Object from json for item={}", item);
            throw new Exception("Error while retrieving Object from json.", e);
        }
    }
}

