package com.example.demo.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.transform.LineAggregator;

import java.io.IOException;

@Slf4j
public class JsonLineAggregator<T> implements LineAggregator<T> {

    private final ObjectMapper objectMapper;

    public JsonLineAggregator(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String aggregate(final T item) {
        try {
            return objectMapper.writeValueAsString(item);
        } catch (IOException e) {
            log.error("Error while creating JSON string from collected data for item={}", item);
           return "";
        }
    }
}
