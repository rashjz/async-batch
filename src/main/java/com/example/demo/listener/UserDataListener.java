package com.example.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDataListener implements JobExecutionListener {


    private final ApplicationEventPublisher publisher;

    @Autowired
    public UserDataListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        JobParameters jobParameters = jobExecution.getJobParameters();
//        publisher.publishEvent(.builder()

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job finished {}", jobExecution);
    }
}
