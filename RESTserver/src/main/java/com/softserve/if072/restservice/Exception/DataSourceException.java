package com.softserve.if072.restservice.Exception;

/**
 * This exception will be thrown if some entity is not found, or is null,
 * if the mandatory field is null
 */
public class DataSourceException extends Exception {

    public DataSourceException() {super(); }

    public DataSourceException(String message) { super(message); }

    public DataSourceException(String message, Throwable cause) { super(message, cause); }

    public DataSourceException(Throwable cause) { super(cause); }
}
