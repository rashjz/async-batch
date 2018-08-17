package com.example.demo.configuration;

import com.example.demo.configuration.properties.ApplicationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    @Bean
    @ConfigurationProperties(prefix = "application")
    public ApplicationProperties configProperties() {
        return new ApplicationProperties();
    }

}
