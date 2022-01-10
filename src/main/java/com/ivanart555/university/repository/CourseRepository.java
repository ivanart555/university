package com.ivanart555.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ivanart555.university.entities.Course;

@Transactional
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
