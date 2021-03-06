package com.example.demo.processor;

import com.example.demo.domain.User;
import com.example.demo.domain.UserApplication;
import com.example.demo.event.UserEvent;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserItemProcessor implements ItemProcessor<User, UserApplication> {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;

    public UserItemProcessor(final UserRepository userRepository,
                             final ApplicationEventPublisher publisher) {
        this.userRepository = userRepository;
        this.publisher = publisher;
    }

    @Override
    public UserApplication process(User user) {
        log.info("processing item " + user.toString());
        UserEvent userEvent = UserEvent.builder().user(user).admin(true).build();
        publisher.publishEvent(userEvent);
        return UserApplication.builder().user(user).build();
    }
}
