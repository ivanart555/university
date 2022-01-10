package com.ivanart555.university.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ivanart555.university.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    @Query("FROM Lesson l WHERE l.group.id = :groupId AND (l.lessonStart BETWEEN :lessonStart AND :lessonEnd OR l.lessonEnd BETWEEN :lessonStart AND :lessonEnd OR l.lessonStart <= :lessonStart AND l.lessonEnd >= :lessonEnd) ORDER BY l.id")
    List<Lesson> getByDateTimeIntervalAndGroupId(
            @Param("groupId") Integer groupId,
            @Param("lessonStart") LocalDateTime lessonStart,
            @Param("lessonEnd") LocalDateTime lessonEnd);

    @Query("SELECT l FROM Lesson l WHERE l.lecturer.id = :lecturerId AND (l.lessonStart BETWEEN :lessonStart AND :lessonEnd OR l.lessonEnd BETWEEN :lessonStart AND :lessonEnd OR l.lessonStart <= :lessonStart AND l.lessonEnd >= :lessonEnd) ORDER BY l.id")
    List<Lesson> getByDateTimeIntervalAndLecturerId(
            @Param("lecturerId") Integer lecturerId,
            @Param("lessonStart") LocalDateTime lessonStart,
            @Param("lessonEnd") LocalDateTime lessonEnd);
}
