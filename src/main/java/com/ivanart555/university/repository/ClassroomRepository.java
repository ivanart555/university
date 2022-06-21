package com.ivanart555.university.repository;

import com.ivanart555.university.entities.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> , QuerydslPredicateExecutor {

}
