package com.ivanart555.university.repository;

import com.ivanart555.university.entities.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {

    @Query("FROM Lecturer l LEFT JOIN FETCH l.course WHERE l.active = TRUE")
    List<Lecturer> getAllActive();

}
