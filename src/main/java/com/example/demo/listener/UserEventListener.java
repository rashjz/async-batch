package com.example.demo.listener;


import com.example.demo.collector.UserDataCollector;
import com.example.demo.event.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventListener {

    private final UserDataCollector userDataCollector;

    @Autowired
    public UserEventListener(UserDataCollector userDataCollector) {
        this.userDataCollector = userDataCollector;
    }

    @EventListener
    public void handleUserEvent(UserEvent event) {
        userDataCollector.save(event.getUser());
        log.info("data received from listener " + event.getUser().getUsername());
    }
}
