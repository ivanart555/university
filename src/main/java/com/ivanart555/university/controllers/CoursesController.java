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

import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.CourseService;

@Controller
@RequestMapping("/courses")
public class CoursesController {
    private CourseService courseService;

    @Autowired
    public CoursesController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping()
    public String index(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) throws ServiceException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Page<Course> coursePage = courseService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("coursePage", coursePage);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", coursePage.getTotalPages());

        return "courses/index";
    }

}
