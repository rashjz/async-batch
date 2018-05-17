package com.example.demo.controller;


import com.example.demo.domain.User;
import com.example.demo.event.UserEvent;
import com.example.demo.configuration.properties.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RestController
public class MainController {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private DataSourceProperties h2DataSourceProperties;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    @Qualifier("h2DataSource")
    private DataSource dataSource;

    @RequestMapping(value = "/user/{name}/admin/{role}", method = RequestMethod.GET)
    public void userLogin(@PathVariable("name") String name, @PathVariable("role") boolean isAdmin) {
        User user = User.builder().username(name).admin(isAdmin).status("ACTIVE").build();
        UserEvent userEvent = new UserEvent(this, user);
        applicationEventPublisher.publishEvent(userEvent);
    }

    @RequestMapping(value = "/test/config", method = RequestMethod.GET)
    public String testGetConfig() throws SQLException {
//        log.info("config file  " + applicationProperties.getUrl());
        try (Connection connection = dataSource.getConnection()) {
            log.info("datasource file  " + connection);
        }
        return h2DataSourceProperties.getUrl();
    }
}
