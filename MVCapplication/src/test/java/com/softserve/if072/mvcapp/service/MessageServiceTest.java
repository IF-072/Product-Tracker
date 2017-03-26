package com.softserve.if072.mvcapp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * The MessageServiceTest class is used to test MessageService class methods
 *
 * @author Roman Dyndyn
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {
    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private MessageService messageService;
    private int userId;
    private String locale;

    @Before
    public void setup() {
        userId = 2;
        locale = "en";
    }

    @Test
    public void broadcast(){
        final String code = "storage.update";

        messageService.broadcast(code, locale, userId);

        verify(messageSource).getMessage(eq(code), any(), eq(new Locale(locale)));
    }

    @Test
    public void broadcast_ShouldChangeLocale(){
        final String code = "storage.update";
        final String paramLocale = null;

        messageService.broadcast(code, paramLocale, userId);

        verify(messageSource).getMessage(eq(code), any(), eq(new Locale(locale)));
    }
}
