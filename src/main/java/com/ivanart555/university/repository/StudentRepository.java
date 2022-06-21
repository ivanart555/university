package com.ivanart555.university.repository;

import com.ivanart555.university.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StudentRepository extends JpaRepository<Student, Integer> , QuerydslPredicateExecutor {

    @Query("FROM Student s LEFT JOIN FETCH s.group WHERE s.active = TRUE ORDER BY s.id")
    List<Student> getAllActive();

    List<Student> findByGroupId(Integer groupId);
}
