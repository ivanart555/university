package com.ivanart555.university.repository;

import com.ivanart555.university.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GroupRepository extends JpaRepository<Group, Integer> , QuerydslPredicateExecutor {

}
