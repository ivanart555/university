package com.ivanart555.university.repository;

import com.ivanart555.university.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CourseRepository extends JpaRepository<Course, Integer> {

}
