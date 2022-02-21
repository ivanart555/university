package com.ivanart555.university.api.rest_controller;

import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.services.LessonService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/lessons")
public class LessonsRestController {
    private final LessonService lessonService;

    @GetMapping
    @ApiOperation("Find all lessons")
    public List<Lesson> findAll() {
        return lessonService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Find lesson by id")
    public Lesson findById(@PathVariable("id") int id) {
        return lessonService.findById(id);
    }

    @PostMapping()
    @ApiOperation("Create new lesson")
    public ResponseEntity<Object> create(@RequestBody Lesson lesson) {
        int id = lessonService.save(lesson);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update already existing lesson")
    @ResponseStatus(HttpStatus.OK)
    public Lesson update(@PathVariable("id") int id, @RequestBody Lesson lesson) {
        lesson.setId(id);
        lessonService.save(lesson);
        return lesson;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete lesson by id")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        lessonService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
