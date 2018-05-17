package com.example.demo.event;


import com.example.demo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Slf4j
public class UserEvent extends ApplicationEvent {

    private final User user;
    private final boolean admin;


    public UserEvent(Object source, User user) {
        super(source);
        this.user = user;
        this.admin = user.isAdmin();
    }

    public User getUser() {
        return user;
    }

    public boolean isAdmin() {
        return admin;
    }

}