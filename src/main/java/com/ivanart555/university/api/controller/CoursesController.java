package com.ivanart555.university.api.controller;

import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
@Controller
@RequestMapping("/courses")
public class CoursesController {
    private static final String REDIRECT_COURSES = "redirect:/courses";
    private CourseService courseService;

    @GetMapping()
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) throws ServiceException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Pageable sortedById = PageRequest.of(currentPage - 1, pageSize, Sort.by("id"));
        Page<Course> coursePage = courseService.findAll(sortedById);

        model.addAttribute("coursePage", coursePage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", coursePage.getTotalPages());

        model.addAttribute("course", new Course());

        int totalPages = coursePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "courses/index";
    }

    @PostMapping()
    public String create(@ModelAttribute("course") @Valid Course course, BindingResult bindingResult)
            throws ServiceException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());

        courseService.save(course);
        return REDIRECT_COURSES;
    }

    @PatchMapping("/edit")
    public String update(@ModelAttribute("course") @Valid Course course, BindingResult bindingResult)
            throws ServiceException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());

        courseService.save(course);
        return REDIRECT_COURSES;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws ServiceException {
        courseService.deleteById(id);
        return REDIRECT_COURSES;
    }
}
