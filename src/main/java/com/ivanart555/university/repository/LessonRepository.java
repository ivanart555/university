package com.ivanart555.university.repository;

import com.ivanart555.university.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> , QuerydslPredicateExecutor {

    List<Lesson> findAllByGroupIdAndLessonStartLessThanEqualAndLessonEndGreaterThanEqual(Integer groupId,
                                                                                         LocalDateTime lessonEnd, LocalDateTime lessonStart);

    List<Lesson> findAllByLecturerIdAndLessonStartLessThanEqualAndLessonEndGreaterThanEqual(Integer groupId,
                                                                                            LocalDateTime lessonEnd, LocalDateTime lessonStart);
}
