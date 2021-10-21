package com.epam.esm.controllers;

import com.epam.esm.exception.InvalidFieldException;
import com.epam.esm.exception.ResourceDuplicateException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exceptions.DaoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Locale;

@ControllerAdvice
public class GiftCertificateSystemExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String MESSAGE_KEY_NOT_FOUND = "message.exception.notFound";
    private static final String MESSAGE_KEY_BAD_ENTITY = "message.exception.badEntity";
    private static final String MESSAGE_KEY_ALREADY_EXIST = "message.exception.alreadyExist";
    private static final String MESSAGE_KEY_INTERNAL_ERROR = "message.exception.internal";
    private static final String MESSAGE_KEY_VALIDATE = "message.exception.validate";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                             WebRequest request) {
        Locale locale = request.getLocale();
        String message = ExceptionResponse.getMessageForLocale(MESSAGE_KEY_NOT_FOUND,locale);
        int errorCode = ResourceNotFoundException.getErrorCode();
        StringBuilder stringBuilder = new StringBuilder();
        String updateMessage = stringBuilder.append("(").append(message).append(" id = ").append(exception.getId()).
                append(") ").append(exception.getMessage()).toString();
        ExceptionResponse response = new ExceptionResponse(updateMessage,errorCode);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceDuplicateException.class)
    public ResponseEntity<ExceptionResponse> handleResourceDuplicateException(ResourceDuplicateException exception,
                                                                             WebRequest request) {
        Locale locale = request.getLocale();
        String message = ExceptionResponse.getMessageForLocale(MESSAGE_KEY_ALREADY_EXIST,locale);
        int errorCode = ResourceDuplicateException.getErrorCode();

        ExceptionResponse response = new ExceptionResponse(message,errorCode);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ExceptionResponse> handleResourceInvalidFieldException(InvalidFieldException exception,
                                                                              WebRequest request) {
        Locale locale = request.getLocale();
        String message = ExceptionResponse.getMessageForLocale(MESSAGE_KEY_BAD_ENTITY,locale);
        int errorCode = InvalidFieldException.getErrorCode();

        ExceptionResponse response = new ExceptionResponse(message,errorCode);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DaoException.class)
    public ResponseEntity<ExceptionResponse> handleDaoException(DaoException exception,
                                                                                 WebRequest request) {
        Locale locale = request.getLocale();
        String message = ExceptionResponse.getMessageForLocale(MESSAGE_KEY_INTERNAL_ERROR,locale);
        int errorCode = DaoException.getErrorCode();

        ExceptionResponse response = new ExceptionResponse(message,errorCode);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)

    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException exception,
                                                                                WebRequest request) {
        Locale locale = request.getLocale();
        String message = ExceptionResponse.getMessageForLocale(MESSAGE_KEY_VALIDATE,locale);
        StringBuilder stringBuilderMessage = new StringBuilder(message);

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            stringBuilderMessage.append(violation.getInvalidValue().toString()).append(". ");
            stringBuilderMessage.append(violation.getMessage());
        }
        int errorCode = 411;

        ExceptionResponse response = new ExceptionResponse(stringBuilderMessage.toString(),errorCode);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
