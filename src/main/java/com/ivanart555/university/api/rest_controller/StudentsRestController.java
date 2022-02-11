package com.ivanart555.university.api.rest_controller;

import com.ivanart555.university.entities.Student;
import com.ivanart555.university.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentsRestController {
    private final StudentService studentService;

    @Autowired
    public StudentsRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public Student findById(@PathVariable("id") int id) {
        return studentService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody Student student) {
        int id = studentService.save(student);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Student update(@PathVariable("id") int id, @RequestBody Student student) {
        student.setId(id);
        studentService.save(student);
        return student;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}