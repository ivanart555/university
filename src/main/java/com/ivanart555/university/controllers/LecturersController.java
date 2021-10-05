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

import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.LecturerService;

@Controller
@RequestMapping("/lecturers")
public class LecturersController {
    private LecturerService lecturerService;

    @Autowired
    public LecturersController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @GetMapping()
    public String index(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) throws ServiceException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Page<Lecturer> lecturerPage = lecturerService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("lecturerPage", lecturerPage);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", lecturerPage.getTotalPages());

        return "lecturers/index";
    }

}
