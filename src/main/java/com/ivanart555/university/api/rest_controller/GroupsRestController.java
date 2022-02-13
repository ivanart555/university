package com.ivanart555.university.api.rest_controller;

import com.ivanart555.university.entities.Group;
import com.ivanart555.university.services.GroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupsRestController {
    private final GroupService groupService;

    @Autowired
    public GroupsRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    @ApiOperation("Find all groups")
    public List<Group> findAll() {
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Find group by id")
    public Group findById(@PathVariable("id") int id) {
        return groupService.findById(id);
    }

    @PostMapping()
    @ApiOperation("Create new group")
    public ResponseEntity<Object> create(@RequestBody Group group) {
        int id = groupService.save(group);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update already existing group")
    @ResponseStatus(HttpStatus.OK)
    public Group update(@PathVariable("id") int id, @RequestBody Group group) {
        group.setId(id);
        groupService.save(group);
        return group;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete group by id")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
