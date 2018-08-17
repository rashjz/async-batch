package com.example.demo.event;


import com.example.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;

@Slf4j
@Data
@Builder
public class UserEvent implements Serializable {
    private static final long serialVersionUID = 12L;

    private final User user;
    private final boolean admin;

    public UserEvent(  User user, boolean admin) {
         this.user = user;
        this.admin = admin;
    }
}
