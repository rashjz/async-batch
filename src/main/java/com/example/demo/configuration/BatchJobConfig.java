package com.example.demo.configuration;


import com.example.demo.domain.User;
import com.example.demo.domain.UserApplication;
import com.example.demo.event.UserEvent;
import com.example.demo.listener.UserDataListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Future;

@Slf4j
@Configuration
public class BatchJobConfig {

    private static final int CHUNK_SIZE = 10;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public BatchJobConfig(final JobBuilderFactory jobBuilderFactory, final StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step step1(
            final ItemReader<User> requestItemReader,
            final ItemProcessor<User, Future<UserApplication>> collectDataProcessor,
            final ItemWriter<Future<UserApplication>> collectedDataWriter) {
        return stepBuilderFactory.get("step1")
                .<User, Future<UserApplication>>chunk(CHUNK_SIZE)
                .reader(requestItemReader)
                .processor(collectDataProcessor)
                .writer(collectedDataWriter)
                .build();
    }

    @Bean
    public Job mainJob(Step step1, UserDataListener jobListener) {
        return jobBuilderFactory.get("mainJob")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .listener(jobListener)
                .build();
    }

}
