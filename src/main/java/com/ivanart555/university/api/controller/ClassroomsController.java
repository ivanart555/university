package com.ivanart555.university.api.controller;

import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.ClassroomService;
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
@RequestMapping("/classrooms")
public class ClassroomsController {
    private static final String REDIRECT_CLASSROOMS = "redirect:/classrooms";
    private ClassroomService classroomService;

    @GetMapping()
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) throws ServiceException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Pageable sortedById = PageRequest.of(currentPage - 1, pageSize, Sort.by("id"));
        Page<Classroom> classroomPage = classroomService.findAll(sortedById);

        model.addAttribute("classroomPage", classroomPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", classroomPage.getTotalPages());

        model.addAttribute("classroom", new Classroom());

        int totalPages = classroomPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "classrooms/index";
    }

    @PostMapping()
    public String create(@ModelAttribute("classroom") @Valid Classroom classroom, BindingResult bindingResult)
            throws ServiceException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());

        classroomService.save(classroom);
        return REDIRECT_CLASSROOMS;
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("classroom") @Valid Classroom classroom, BindingResult bindingResult,
                         @PathVariable("id") int id)
            throws ServiceException {

        if (bindingResult.hasErrors())
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());

        classroomService.save(classroom);
        return REDIRECT_CLASSROOMS;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws ServiceException {
        classroomService.deleteById(id);
        return REDIRECT_CLASSROOMS;
    }
}
