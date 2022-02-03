package com.ivanart555.university.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.ServiceException;

import com.ivanart555.university.services.CourseService;
import com.ivanart555.university.services.LecturerService;

@Controller
@RequestMapping("/lecturers")
public class LecturersController {
    private static final String REDIRECT_LECTURERS = "redirect:/lecturers";
    private LecturerService lecturerService;
    private CourseService courseService;

    @Autowired
    public LecturersController(LecturerService lecturerService, CourseService courseService) {
        this.lecturerService = lecturerService;
        this.courseService = courseService;
    }

    @GetMapping()
    public String index(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) throws ServiceException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Pageable sortedById = PageRequest.of(currentPage - 1, pageSize, Sort.by("id"));
        Page<Lecturer> lecturerPage = lecturerService.findAll(sortedById);

        model.addAttribute("lecturerPage", lecturerPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", lecturerPage.getTotalPages());

        model.addAttribute("courses", courseService.findAll());

        model.addAttribute("lecturer", new Lecturer());

        int totalPages = lecturerPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "lecturers/index";
    }

    @PostMapping()
    public String create(@ModelAttribute("lecturer") @Valid Lecturer lecturer, BindingResult bindingResult)
            throws ServiceException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());

        lecturerService.save(lecturer);
        return REDIRECT_LECTURERS;
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("lecturer") @Valid Lecturer lecturer, BindingResult bindingResult,
            @PathVariable("id") int id)
            throws ServiceException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());

        lecturerService.save(lecturer);
        return REDIRECT_LECTURERS;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws ServiceException {
        lecturerService.delete(id);
        return REDIRECT_LECTURERS;
    }
}
