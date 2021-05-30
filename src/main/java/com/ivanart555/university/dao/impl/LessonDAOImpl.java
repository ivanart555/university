package com.ivanart555.university.dao.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.mappers.LessonMapper;

@Component
public class LessonDAOImpl implements LessonDAO {
    private final Environment env;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LessonDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    @Override
    public List<Lesson> getAll() {
        return jdbcTemplate.query(env.getProperty("sql.lessons.get.all"), new LessonMapper());
    }

    @Override
    public Lesson getById(Integer id) {
        return jdbcTemplate
                .query(env.getProperty("sql.lessons.get.byId"), new Object[] { id },
                        new LessonMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(env.getProperty("sql.lessons.delete"), id);
    }

    @Override
    public void update(Lesson lesson) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.lessons.update"), lesson.getCourseId(), lesson.getRoomId(),
                lesson.getLecturerId(),
                lesson.getLessonStart(), lesson.getLessonEnd(), lesson.getId());
    }

    @Override
    public void create(Lesson lesson) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.lessons.create"), lesson.getCourseId(), lesson.getRoomId(),
                lesson.getLecturerId(),
                lesson.getLessonStart(), lesson.getLessonEnd());
    }

    @Override
    public void assignLessonToGroup(Integer lessonId, Integer groupId) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.lessons.add.lessonToGroup"), lessonId, groupId);
    }

    @Override
    public List<Lesson> getByDateTimeIntervalAndGroupId(Integer groupId, LocalDateTime lessonStart,
            LocalDateTime lessonEnd) {
        return jdbcTemplate.query(env.getProperty("sql.lessons.get.byDateTimeIntervalAndGroupId"),
                new Object[] { groupId, lessonStart, lessonEnd, lessonStart, lessonEnd, lessonStart, lessonEnd },
                new LessonMapper());
    }

    @Override
    public List<Lesson> getByDateTimeIntervalAndStudentId(Integer studentId, LocalDateTime lessonStart,
            LocalDateTime lessonEnd) {
        return jdbcTemplate.query(env.getProperty("sql.lessons.get.byDateTimeIntervalAndStudentId"),
                new Object[] { studentId, lessonStart, lessonEnd, lessonStart, lessonEnd, lessonStart, lessonEnd },
                new LessonMapper());
    }

    @Override
    public List<Lesson> getByDateTimeIntervalAndLecturerId(Integer lecturerId, LocalDateTime lessonStart,
            LocalDateTime lessonEnd) {
        return jdbcTemplate.query(env.getProperty("sql.lessons.get.byDateTimeIntervalAndLecturerId"),
                new Object[] { lecturerId, lessonStart, lessonEnd, lessonStart, lessonEnd, lessonStart, lessonEnd },
                new LessonMapper());
    }
}
