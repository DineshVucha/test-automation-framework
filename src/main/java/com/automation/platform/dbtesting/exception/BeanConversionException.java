package com.automation.platform.dbtesting.exception;

public class BeanConversionException extends RuntimeException{
    private String message;

    public BeanConversionException(String message) {
        super(message);
    }
}
