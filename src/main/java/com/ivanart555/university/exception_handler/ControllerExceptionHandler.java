package com.ivanart555.university.exception_handler;

import com.ivanart555.university.exception.ValidationException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = "com.ivanart555.university.controllers")
public class ControllerExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ModelAndView handleValidationException(ValidationException e) {
        return prepareModel(e, "errorPage");
    }

    private ModelAndView prepareModel(ValidationException e, String view) {
        ModelAndView modelView = new ModelAndView(view);
        modelView.addObject("errors", e.getErrors());

        return modelView;
    }
}
