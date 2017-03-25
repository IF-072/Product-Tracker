package com.softserve.if072.common.model;

import java.util.Date;

/**
 * The Message class is used to send notification to user.
 *
 * @author Roman Dyndyn
 */
public class Message {

    private String data;
    private Date date;

    public Message(final String data, final Date date) {
        this.data = data;
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }
}
