package com.ivanart555.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ivanart555.university.entities.Student;

@Transactional
public interface StudentRepository extends JpaRepository<Student, Integer> {
    
    @Query("FROM Student s LEFT JOIN FETCH s.group WHERE s.active = TRUE ORDER BY s.id")
    List<Student> getAllActive();
      
    List<Student> findByGroupId(Integer groupId);
}
