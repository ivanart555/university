package com.ivanart555.university.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final List<ObjectError> errors;

    public ValidationException(String message, List<ObjectError> errors) {
        super(message);
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }
}
