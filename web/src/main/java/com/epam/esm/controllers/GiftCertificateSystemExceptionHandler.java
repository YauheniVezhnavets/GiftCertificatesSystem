package com.epam.esm.controllers;

import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exceptions.DaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
import java.util.ResourceBundle;

@ControllerAdvice
public class GiftCertificateSystemExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MESSAGE_KEY_NOT_FOUND = "message.exception.notFound";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                             WebRequest request) {
        Locale locale = request.getLocale();
        String message = ExceptionResponse.getMessageForLocale(MESSAGE_KEY_NOT_FOUND,locale);
        int errorCode = ResourceNotFoundException.getErrorCode();
        StringBuilder stringBuilder = new StringBuilder();
        String updateMessage = stringBuilder.append(message).append(" id = ").append(exception.getId()).
                append(")").toString();
        ExceptionResponse response = new ExceptionResponse(updateMessage,errorCode);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
