package com.ivanart555.university.api.rest_controller_test;

import com.ivanart555.university.entities.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestData {

    public List<Group> getTestGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "AB-01"));
        groups.add(new Group(2, "BA-12"));
        groups.add(new Group(3, "AC-06"));
        return groups;
    }

    public List<Course> getTestCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "math", ""));
        courses.add(new Course(2, "biology", ""));
        courses.add(new Course(3, "english", ""));
        return courses;
    }

    public List<Classroom> getTestClassrooms() {
        List<Classroom> classrooms = new ArrayList<>();
        classrooms.add(new Classroom(1, "100"));
        classrooms.add(new Classroom(2, "101"));
        classrooms.add(new Classroom(3, "102"));
        return classrooms;
    }

    public List<Lecturer> getTestLecturers() {
        List<Lecturer> lecturers = new ArrayList<>();
        lecturers.add(new Lecturer(1, "Alex", "Smith"));
        lecturers.add(new Lecturer(2, "Peter", "White"));
        lecturers.add(new Lecturer(3, "Mary", "Black"));
        return lecturers;
    }

    public List<Student> getTestStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Peter", "Jackson"));
        students.add(new Student(2, "Alex", "Smith"));
        students.add(new Student(3, "Ann", "White"));
        return students;
    }

    public List<Lesson> getTestLessons() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1, LocalDateTime.of(2021, 5, 18, 10, 00),
                LocalDateTime.of(2021, 5, 18, 11, 00)));
        lessons.add(new Lesson(2, LocalDateTime.of(2021, 5, 18, 11, 00),
                LocalDateTime.of(2021, 5, 18, 12, 00)));
        lessons.add(new Lesson(3, LocalDateTime.of(2021, 5, 18, 12, 00),
                LocalDateTime.of(2021, 5, 18, 13, 00)));
        return lessons;
    }


}
