package com.ivanart555.university.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.LessonService;

@Controller
@RequestMapping("/lessons")
public class LessonsController {
    private static final String REDIRECT_LESSONS = "redirect:/lessons";
    private LessonService lessonService;

    @Autowired
    public LessonsController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping()
    public String index(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) throws ServiceException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Page<Lesson> lessonPage = lessonService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("lessonPage", lessonPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", lessonPage.getTotalPages());

        model.addAttribute("lesson", new Lesson());

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
    public String create(@ModelAttribute("lesson") Lesson lesson) throws ServiceException {
        lessonService.create(lesson);
        return REDIRECT_LESSONS;
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("lesson") Lesson lesson, @PathVariable("id") int id)
            throws ServiceException {
        lessonService.update(lesson);
        return REDIRECT_LESSONS;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws ServiceException {
        lessonService.delete(id);
        return REDIRECT_LESSONS;
    }
}
