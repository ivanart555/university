package com.ivanart555.university.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.CourseService;
import com.ivanart555.university.services.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupsController {
    private static final String REDIRECT_GROUPS = "redirect:/groups";
    private GroupService groupService;
    private CourseService courseService;

    @Autowired
    public GroupsController(GroupService groupService, CourseService courseService) {
        this.groupService = groupService;
        this.courseService = courseService;
    }

    @GetMapping()
    public String index(Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) throws ServiceException {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Pageable sortedById = PageRequest.of(currentPage - 1, pageSize, Sort.by("id"));
        Page<Group> groupPage = groupService.findAll(sortedById);
        
        
        model.addAttribute("groupPage", groupPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", groupPage.getTotalPages());

        model.addAttribute("allCourses", courseService.findAll());
        model.addAttribute("group", new Group());

        int totalPages = groupPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "groups/index";
    }

    @PostMapping()
    public String create(@ModelAttribute("group") Group group) throws ServiceException {
        groupService.save(group);
        return REDIRECT_GROUPS;
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("group") Group group, @PathVariable("id") int id)
            throws ServiceException {
        groupService.save(group);
        return REDIRECT_GROUPS;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) throws ServiceException {
        groupService.delete(id);
        return REDIRECT_GROUPS;
    }

}
