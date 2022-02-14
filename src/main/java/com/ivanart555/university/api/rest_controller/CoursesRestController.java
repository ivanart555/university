package com.ivanart555.university.api.rest_controller;

import com.ivanart555.university.entities.Course;
import com.ivanart555.university.services.CourseService;
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
@RequestMapping("/api/v1/courses")
public class CoursesRestController {
    private final CourseService courseService;

    @GetMapping
    @ApiOperation("Find all courses")
    public List<Course> findAll() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Find course by id")
    public Course findById(@PathVariable("id") int id) {
        return courseService.findById(id);
    }

    @PostMapping()
    @ApiOperation("Create new course")
    public ResponseEntity<Object> create(@RequestBody Course course) {
        int id = courseService.save(course);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ApiOperation("Update already existing course")
    @ResponseStatus(HttpStatus.OK)
    public Course update(@PathVariable("id") int id, @RequestBody Course course) {
        course.setId(id);
        courseService.save(course);
        return course;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete course by id")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
