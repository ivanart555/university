package com.ivanart555.university.api.controller;

import com.ivanart555.university.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class UniversityController {

    @GetMapping()
    public String index(Model model) throws ServiceException {

        return "university/home";
    }

}
