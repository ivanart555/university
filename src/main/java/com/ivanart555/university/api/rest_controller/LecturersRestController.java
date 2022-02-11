package com.ivanart555.university.api.rest_controller;

import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.services.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lecturers")
public class LecturersRestController {
    private final LecturerService lecturerService;

    @Autowired
    public LecturersRestController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @GetMapping
    public List<Lecturer> findAll() {
        return lecturerService.findAll();
    }

    @GetMapping("/{id}")
    public Lecturer findById(@PathVariable("id") int id) {
        return lecturerService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody Lecturer lecturer) {
        int id = lecturerService.save(lecturer);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Lecturer update(@PathVariable("id") int id, @RequestBody Lecturer lecturer) {
        lecturer.setId(id);
        lecturerService.save(lecturer);
        return lecturer;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        lecturerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
