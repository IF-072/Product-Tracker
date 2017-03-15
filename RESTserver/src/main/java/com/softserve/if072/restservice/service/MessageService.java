package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.Message;
import com.softserve.if072.common.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageService {
    private SimpMessagingTemplate messaging;

    @Autowired
    public MessageService(SimpMessagingTemplate messaging) {
        this.messaging = messaging;
    }

    public void broadcastSpittle(String msg, User user) {
        messaging.convertAndSend("/queue/notifications/" + user.getId(),
                new Message(msg, new Date()));
    }
}
