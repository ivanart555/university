package com.ivanart555.university.test_data;

import com.ivanart555.university.entities.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestData {

    public List<Group> getTestGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "AB-01", null, null));
        groups.add(new Group(2, "BA-12", null, null));
        groups.add(new Group(3, "AC-06", null, null));
        return groups;
    }

    public List<Course> getTestCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course(1, "english", "English languadge learning.", null, null, null));
        courses.add(new Course(2, "astronomy", "Astronomy is the study of the sun, moon, stars, planets.", null, null, null));
        courses.add(new Course(3, "marketing", "Advertising, selling, and delivering products to consumers.", null, null, null));
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
        lecturers.add(new Lecturer(1, "Alex", "Smith", true, null));
        lecturers.add(new Lecturer(2, "Peter", "White", true, null));
        lecturers.add(new Lecturer(3, "Mary", "Black", true, null));
        return lecturers;
    }

    public List<Student> getTestStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Peter", "Jackson", true, null));
        students.add(new Student(2, "Alex", "Smith", true, null));
        students.add(new Student(3, "Ann", "White", true, null));
        return students;
    }

    public List<Lesson> getTestLessons() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(new Lesson(1, null, null, null, null, LocalDateTime.of(2021, 5, 18, 10, 00),
                LocalDateTime.of(2021, 5, 18, 11, 00)));
        lessons.add(new Lesson(2, null, null, null, null, LocalDateTime.of(2021, 5, 18, 11, 00),
                LocalDateTime.of(2021, 5, 18, 12, 00)));
        lessons.add(new Lesson(3, null, null, null, null, LocalDateTime.of(2021, 5, 18, 12, 00),
                LocalDateTime.of(2021, 5, 18, 13, 00)));
        return lessons;
    }


}
