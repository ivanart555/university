package com.ivanart555.university.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ivanart555.university.entities.Classroom;
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
    public String index(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) throws ServiceException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Page<Classroom> classroomPage = classroomService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("classroomPage", classroomPage);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", classroomPage.getTotalPages());

        return "classrooms/index";
    }

}
