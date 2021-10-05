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

import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.LecturerService;

@Controller
@RequestMapping("/lecturers")
public class LecturersController {
    private static final String REDIRECT_LECTURERS = "redirect:/lecturers";
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
    public String create(@ModelAttribute("lecturer") Lecturer lecturer) throws ServiceException {

        lecturerService.create(lecturer);
        return REDIRECT_LECTURERS;
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("lecturer") Lecturer lecturer, @PathVariable("id") int id)
            throws ServiceException {
        lecturerService.update(lecturer);
        return REDIRECT_LECTURERS;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws ServiceException {
        lecturerService.delete(id);
        return REDIRECT_LECTURERS;
    }
    
}
