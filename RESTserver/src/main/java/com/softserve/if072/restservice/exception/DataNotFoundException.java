package com.softserve.if072.restservice.exception;

/**
 * This exception will be thrown if some entity is not found, or is null,
 * if the mandatory field is null
 */
public class DataNotFoundException extends Exception {

    public DataNotFoundException() {super(); }

    public DataNotFoundException(String message) { super(message); }

    public DataNotFoundException(String message, Throwable cause) { super(message, cause); }

    public DataNotFoundException(Throwable cause) { super(cause); }
}
