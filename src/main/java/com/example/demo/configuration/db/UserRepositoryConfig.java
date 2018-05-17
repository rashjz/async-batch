package com.example.demo.configuration.db;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.impl.UserRepositoryImpl;
import com.example.demo.repository.impl.sp.UserDataSP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class UserRepositoryConfig {
    private static final String SP_NAME = "name";
    private final DataSource dataSource;

    @Autowired
    public UserRepositoryConfig(@Qualifier("h2DataSource") final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl(new UserDataSP(dataSource, SP_NAME));
    }
}
