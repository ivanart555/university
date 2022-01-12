package com.ivanart555.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.ivanart555.university.entities.Group;

@Transactional
public interface GroupRepository extends JpaRepository<Group, Integer> {

}
