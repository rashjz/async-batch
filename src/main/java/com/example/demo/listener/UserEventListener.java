package com.example.demo.listener;


import com.example.demo.event.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventListener {


    @EventListener
    public void handleUserEvent(UserEvent event) {
        //do some operations
        log.info("data received from listener " + event.getUser().getUsername());
    }
}