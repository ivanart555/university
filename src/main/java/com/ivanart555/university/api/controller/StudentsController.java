package com.ivanart555.university.api.controller;

import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.GroupService;
import com.ivanart555.university.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/students")
public class StudentsController {
    private static final String REDIRECT_STUDENTS = "redirect:/students";
    private StudentService studentService;
    private GroupService groupService;

    @Autowired
    public StudentsController(StudentService studentService, GroupService groupService) {
        this.studentService = studentService;
        this.groupService = groupService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) throws ServiceException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Pageable sortedById = PageRequest.of(currentPage - 1, pageSize, Sort.by("id"));
        Page<Student> studentPage = studentService.findAll(sortedById);

        model.addAttribute("studentPage", studentPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", studentPage.getTotalPages());

        model.addAttribute("groups", groupService.findAll());

        model.addAttribute("student", new Student());

        int totalPages = studentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "students/index";
    }

    @PostMapping
    public String create(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult)
            throws ServiceException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());

        studentService.save(student);
        return REDIRECT_STUDENTS;
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult,
                         @PathVariable("id") int id)
            throws ServiceException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());

        studentService.save(student);
        return REDIRECT_STUDENTS;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws ServiceException {
        studentService.delete(id);
        return REDIRECT_STUDENTS;
    }
}
