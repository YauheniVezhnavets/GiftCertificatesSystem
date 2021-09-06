package com.epam.esm.controllers;

import java.util.Locale;
import java.util.ResourceBundle;

public class ExceptionResponse {

    private static final String BUNDLE_BASE_NAME = "messages";
    private String errorMessage;
    private int errorCode;


    public ExceptionResponse(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale).getString(messageKey);
    }

    public int getCode() {
        return errorCode;
    }

    public String getMessage() {
        return errorMessage;
    }

    public void setMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
