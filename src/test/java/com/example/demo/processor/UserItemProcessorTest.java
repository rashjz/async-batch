package com.example.demo.processor;

import com.example.demo.domain.User;
import com.example.demo.domain.UserApplication;
import com.example.demo.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserItemProcessorTest {

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
    @Mock
    private UserRepository userRepository;

    private UserItemProcessor userItemProcessor;

    @Before
    public void init() {
        this.userItemProcessor = new UserItemProcessor(userRepository, applicationEventPublisher);
    }

    @Test
    public void process_User_processedCorrectly() throws Exception {
        // Setup
        User user = getUser();

        // Execute
        UserApplication userApplication = userItemProcessor.process(user);

        // Validate
        assertEquals(userApplication.getUser(), user);
    }

    @Test
    public void process_validationOnExp_shouldPublishDataEvent() throws Exception {
        // Setup
        User user = getUser();

        doThrow(new RuntimeException()).when(userRepository).getAllUsersByRmCode(anyString());

        // Execute
        UserApplication userApplication = userItemProcessor.process(user);

        // Validate
        verify(applicationEventPublisher, times(1)).publishEvent(user);
        assertNotNull(userApplication);
    }

    private User getUser() {
        return User.builder().admin(false).status("a").userID(BigDecimal.ZERO).build();
    }
}
