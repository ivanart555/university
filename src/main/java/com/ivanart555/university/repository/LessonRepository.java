package com.ivanart555.university.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivanart555.university.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    List<Lesson> findAllByGroupIdAndLessonStartLessThanEqualAndLessonEndGreaterThanEqual(Integer groupId,
            LocalDateTime lessonEnd, LocalDateTime lessonStart);

    List<Lesson> findAllByLecturerIdAndLessonStartLessThanEqualAndLessonEndGreaterThanEqual(Integer groupId,
            LocalDateTime lessonEnd, LocalDateTime lessonStart);
}
