package com.softserve.if072.mvcapp.service;

import com.softserve.if072.common.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;

/**
 * The MessageService class is used to send messages to user.
 *
 * @author Roman Dyndyn
 */
@Service
public class MessageService {
    private final SimpMessagingTemplate messaging;
    private final MessageSource messageSource;

    @Autowired
    public MessageService(final SimpMessagingTemplate messaging, final MessageSource messageSource) {
        this.messaging = messaging;
        this.messageSource = messageSource;
    }

    /**
     * Sends message to user.
     *
     * @param code - the code to lookup up in messageSource
     * @param locale - the locale in which to do the lookup
     * @param user - current user unique identifier
     * @param args - an array of arguments that will be filled in
     *             for params within the message, or null if none.
     */
    public void broadcast(final String code, String locale, final int user, final Object... args) {
        locale = locale == null ? "en" : locale;
        final String msg = messageSource.getMessage(code, args, new Locale(locale));
        messaging.convertAndSend("/queue/notifications/" + user,
                new Message(msg, new Date()));
    }
}

