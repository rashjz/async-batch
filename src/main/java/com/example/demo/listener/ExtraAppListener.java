package com.example.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Collections;


@Slf4j
public class ExtraAppListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        log.info("adding extra properties to app");
        PropertySource<?> propertySource = new MapPropertySource("example", Collections.singletonMap("example.secret", "the-secret-key"));
        event.getEnvironment().getPropertySources().addFirst(propertySource);
    }
}
