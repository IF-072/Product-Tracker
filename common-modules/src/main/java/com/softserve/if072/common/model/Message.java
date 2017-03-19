package com.softserve.if072.common.model;

import java.util.Date;

/**
 * The Message class is used to send notification to user.
 *
 * @author Roman Dyndyn
 */
public class Message {

    private String message;
    private Date date;

    public Message(final String message, final Date date) {
        this.message = message;
        this.date = date;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }
}
