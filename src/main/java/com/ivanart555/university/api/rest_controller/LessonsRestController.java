package com.ivanart555.university.api.rest_controller;

import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lessons")
public class LessonsRestController {
    private final LessonService lessonService;

    @Autowired
    public LessonsRestController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<Lesson> findAll() {
        return lessonService.findAll();
    }

    @GetMapping("/{id}")
    public Lesson findById(@PathVariable("id") int id) {
        return lessonService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody Lesson lesson) {
        int id = lessonService.save(lesson);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Lesson update(@PathVariable("id") int id, @RequestBody Lesson lesson) {
        lesson.setId(id);
        lessonService.save(lesson);
        return lesson;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
