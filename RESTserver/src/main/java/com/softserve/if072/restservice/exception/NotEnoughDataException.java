package com.softserve.if072.restservice.exception;

/**
 * The NotEnoughDateException class
 *
 * @author Igor Kryviuk
 */
public class NotEnoughDataException extends Exception {
    private final String productName;


    public NotEnoughDataException(String message, String productName) {
        super(message);
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}
