package com.example.demo.configuration;

import com.example.demo.domain.User;
import com.example.demo.domain.UserApplication;
 import com.example.demo.processor.AsyncItemProcessor;
import com.example.demo.reader.UserItemReader;
import com.example.demo.repository.UserRepository;
import com.example.demo.writer.AsyncItemWriter;
import com.example.demo.writer.JsonLineAggregator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.concurrent.Future;

@Configuration
public class BatchJobComponentConfig {

    @Bean
    public ItemReader<User> userDataReader(final UserRepository userRepository) {
        UserItemReader<User> requestReader = new UserItemReader<>(userRepository::getAllUsers);
        requestReader.setSaveState(false);

        return requestReader;
    }

    @Bean
    public ItemWriter<Future<UserApplication>> asyncUserItemWriter(final ItemWriter<UserApplication> userItemWriter) {
        AsyncItemWriter<UserApplication> userApplicationAsyncItemWriter = new AsyncItemWriter<>();
        userApplicationAsyncItemWriter.setDelegate(userItemWriter);
        return userApplicationAsyncItemWriter;
    }

    @Bean
    public ItemWriter<UserApplication> userItemWriter(final ObjectMapper objectMapper) {
        FlatFileItemWriter<UserApplication> flatFileWriter = new FlatFileItemWriter<>();
        flatFileWriter.setLineAggregator(new JsonLineAggregator<>(objectMapper));
        flatFileWriter.setResource(new ClassPathResource("sample-data.csv"));
        return flatFileWriter;
    }

    @Bean
    public ItemProcessor<User, Future<UserApplication>> asyncUserProcessor(
            final ItemProcessor<User, UserApplication> userItemProcessor) {
        AsyncItemProcessor<User, UserApplication> futurePackProcessor = new AsyncItemProcessor<>();
        futurePackProcessor.setDelegate(userItemProcessor);

        return futurePackProcessor;
    }

}
