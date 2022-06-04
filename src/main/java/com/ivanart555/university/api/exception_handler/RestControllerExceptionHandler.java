package com.ivanart555.university.api.exception_handler;

import com.ivanart555.university.entities.rest_response.RestExceptionResponse;
import com.ivanart555.university.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

@RestControllerAdvice(basePackages = "com.ivanart555.university.api.rest_controller")
public class RestControllerExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    public RestExceptionResponse handleValidationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            message.append(violation.getMessage().concat(";"));
        }

        return new RestExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                message.toString());
    }

    @ExceptionHandler(value = ServiceException.class)
    public RestExceptionResponse handleServiceException(Exception e) {
        return new RestExceptionResponse(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                e.getClass().getSimpleName(),
                e.getMessage());
    }

}
