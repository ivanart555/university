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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.ClassroomService;

@Controller
@RequestMapping("/classrooms")
public class ClassroomsController {
    private static final String REDIRECT_CLASSROOMS = "redirect:/classrooms";
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
        int pageSize = size.orElse(10);

        Page<Classroom> classroomPage = classroomService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("classroomPage", classroomPage);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", classroomPage.getTotalPages());

        int totalPages = classroomPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "classrooms/index";
    }

    @GetMapping("/new")
    public String newClassroom(@ModelAttribute("classroom") Classroom classroom) {
        return "classrooms/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("classroom") Classroom classroom) throws ServiceException {
        classroomService.create(classroom);
        return REDIRECT_CLASSROOMS;
    }
    
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) throws ServiceException {
        model.addAttribute("classroom", classroomService.getById(id));
        return "classrooms/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("classroom") Classroom classroom, @PathVariable("id") int id)
            throws ServiceException {
        classroomService.update(classroom);
        return REDIRECT_CLASSROOMS;
    }


    @RequestMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) throws ServiceException {
        classroomService.delete(id);
        return REDIRECT_CLASSROOMS;
    }
}
