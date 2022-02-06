package com.ivanart555.university.api.rest_controller;

import com.ivanart555.university.entities.Group;
import com.ivanart555.university.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupsRestController {
    private final GroupService groupService;

    @Autowired
    public GroupsRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> findAll() {
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    public Group findById(@PathVariable("id") int id) {
        return groupService.findById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Group group) {
        group.setId(0);
        groupService.save(group);
    }

    @PatchMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody Group group) {
        group.setId(id);
        groupService.save(group);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        groupService.delete(id);
    }
}
