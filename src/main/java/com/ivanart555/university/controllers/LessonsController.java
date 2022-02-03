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

import com.ivanart555.university.dto.LessonDto;

import com.ivanart555.university.exception.ServiceException;

import com.ivanart555.university.services.ClassroomService;
import com.ivanart555.university.services.CourseService;
import com.ivanart555.university.services.GroupService;
import com.ivanart555.university.services.LecturerService;
import com.ivanart555.university.services.LessonService;

@Controller
@RequestMapping("/lessons")
public class LessonsController {
    private static final String REDIRECT_LESSONS = "redirect:/lessons";
    private LessonService lessonService;
    private CourseService courseService;
    private LecturerService lecturerService;
    private ClassroomService classroomService;
    private GroupService groupService;

    @Autowired
    public LessonsController(LessonService lessonService, CourseService courseService, LecturerService lecturerService,
            ClassroomService classroomService, GroupService groupService) {
        this.lessonService = lessonService;
        this.courseService = courseService;
        this.lecturerService = lecturerService;
        this.classroomService = classroomService;
        this.groupService = groupService;
    }

    @GetMapping()
    public String index(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) throws ServiceException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Pageable sortedById = PageRequest.of(currentPage - 1, pageSize, Sort.by("id"));
        Page<LessonDto> lessonPage = lessonService.findAll(sortedById);

        model.addAttribute("lessonDto", new LessonDto());

        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("lecturers", lecturerService.getAllActive());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("groups", groupService.findAll());

        model.addAttribute("lessonPage", lessonPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", lessonPage.getTotalPages());
        int totalPages = lessonPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "lessons/index";
    }

    @PostMapping()
    public String create(@ModelAttribute("lessonDto") @Valid LessonDto lessonDto, BindingResult bindingResult)
            throws ServiceException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());

        lessonService.save(lessonDto);
        return REDIRECT_LESSONS;
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("lessonDto") @Valid LessonDto lessonDto, BindingResult bindingResult,
            @PathVariable("id") int id)
            throws ServiceException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());

        lessonService.save(lessonDto);
        return REDIRECT_LESSONS;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws ServiceException {
        lessonService.delete(id);
        return REDIRECT_LESSONS;
    }
}
