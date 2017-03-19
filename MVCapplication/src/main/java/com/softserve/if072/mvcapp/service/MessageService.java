package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;

@Service
public class MessageService {
    private SimpMessagingTemplate messaging;
    private MessageSource messageSource;

    @Autowired
    public MessageService(SimpMessagingTemplate messaging, MessageSource messageSource) {
        this.messaging = messaging;
        this.messageSource = messageSource;
    }

    public void broadcast(String code, String locale, int user, Object... args) {
        locale = locale == null ? "en" : locale;
        String msg = messageSource.getMessage(code, args, new Locale(locale));
        messaging.convertAndSend("/queue/notifications/" + user,
                new Message(msg, new Date()));
    }
}

