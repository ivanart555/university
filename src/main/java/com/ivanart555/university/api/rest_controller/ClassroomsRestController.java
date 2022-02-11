package com.ivanart555.university.api.rest_controller;

import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.services.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/classrooms")
public class ClassroomsRestController {
    private final ClassroomService classroomService;

    @Autowired
    public ClassroomsRestController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public List<Classroom> findAll() {
        return classroomService.findAll();
    }

    @GetMapping("/{id}")
    public Classroom findById(@PathVariable("id") int id) {
        return classroomService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody Classroom classroom) {
        int id = classroomService.save(classroom);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Classroom update(@PathVariable("id") int id, @RequestBody Classroom classroom) {
        classroom.setId(id);
        classroomService.save(classroom);
        return classroom;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        classroomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
