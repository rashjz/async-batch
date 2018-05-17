package com.example.demo;

import com.example.demo.configuration.properties.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobParametersNotFoundException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.JobLauncherCommandLineRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, BatchAutoConfiguration.class})
public class DemoApplication extends JobLauncherCommandLineRunner {
    private final ApplicationProperties applicationProperties;

    @Autowired
    public DemoApplication(JobLauncher jobLauncher, JobExplorer jobExplorer, ApplicationProperties applicationProperties) {
        super(jobLauncher, jobExplorer);
        this.applicationProperties = applicationProperties;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext application = new SpringApplicationBuilder()
                .sources(DemoApplication.class)
                .main(DemoApplication.class)
                .addCommandLineProperties(true)
                .web(false)
                .run(args);
        System.exit(SpringApplication.exit(application));
    }

    @Override
    public void run(String... strings) throws JobExecutionException {
        log.info("As Of Report batch started.");
        super.run(strings);
    }

    @Override
    protected void execute(Job job, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException, JobParametersNotFoundException {
        log.info("Start execute {} and use {}", job, jobParameters);
        Map<String, JobParameter> parameters = jobParameters.getParameters();
        for (Map.Entry<String, JobParameter> next : parameters.entrySet()) {
            if (next.getKey().startsWith("-")) {
                parameters.remove(next.getKey());
            }
        }
        super.execute(job, new JobParameters(parameters));
    }
}
