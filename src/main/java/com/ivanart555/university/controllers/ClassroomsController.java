package com.ivanart555.university.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.ClassroomService;

@Controller
@RequestMapping("/classrooms")
public class ClassroomsController {
    private ClassroomService classroomService;

    @Autowired
    public ClassroomsController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping()
    public String index(Model model) throws ServiceException {

        model.addAttribute("classrooms", classroomService.getAll());

        return "classrooms/index";
    }

}
