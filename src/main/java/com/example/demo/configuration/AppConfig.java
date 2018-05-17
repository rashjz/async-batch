package com.example.demo.configuration;

import com.example.demo.configuration.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public ApplicationProperties configProperties() {
        return new ApplicationProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties h2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Qualifier("h2DataSource")
    public DataSource h2DataSource(@Qualifier("h2DataSourceProperties") final DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager h2TransactionManager(@Qualifier("h2DataSource") final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
