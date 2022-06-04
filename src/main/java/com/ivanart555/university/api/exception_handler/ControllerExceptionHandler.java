package com.ivanart555.university.api.exception_handler;

import com.ivanart555.university.exception.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ValidationException;

@ControllerAdvice(basePackages = "com.ivanart555.university.api.controller")
public class ControllerExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ModelAndView handleValidationException(ValidationException e) {
        return prepareModel(e, "errorPage");
    }

    @ExceptionHandler(ServiceException.class)
    public ModelAndView handleServiceException(ServiceException e) {
        return prepareModel(e, "errorPage");
    }

    private ModelAndView prepareModel(Exception e, String view) {
        ModelAndView modelView = new ModelAndView(view);
        modelView.addObject("title", e.getClass().getSimpleName());
        modelView.addObject("message", e.getMessage());
        return modelView;
    }

}
