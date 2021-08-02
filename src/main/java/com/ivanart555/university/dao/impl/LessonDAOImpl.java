package com.ivanart555.university.dao.impl;

import static java.lang.String.format;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.mappers.LessonMapper;

@Component
public class LessonDAOImpl implements LessonDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonDAOImpl.class);
    private final Environment env;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LessonDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    @Override
    public List<Lesson> getAll() {
        LOGGER.debug("Trying to get all Lessons.");
        return jdbcTemplate.query(env.getProperty("sql.lessons.get.all"), new LessonMapper());
    }

    @Override
    public Lesson getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Lesson by id: {}", id);

        Lesson lesson = new Lesson();
        try {
            lesson = jdbcTemplate.queryForObject(env.getProperty("sql.lessons.get.byId"), new Object[] { id },
                    new LessonMapper());
        } catch (EmptyResultDataAccessException e) {
            String msg = format("Lesson with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        } catch (DataAccessException e) {
            String msg = format("Unable to get Lesson with ID '%s'", id);
            throw new QueryNotExecuteException(msg);
        }
        LOGGER.debug("Found:'{}'", lesson);

        return lesson;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Lesson by id: {}", id);
        jdbcTemplate.update(env.getProperty("sql.lessons.delete"), id);
    }

    @Override
    public void update(Lesson lesson) throws DAOException {
        LOGGER.debug("Trying to update {}", lesson);

        try {
            jdbcTemplate.update(env.getProperty("sql.lessons.update"), lesson.getCourseId(), lesson.getRoomId(),
                    lesson.getLecturerId(),
                    lesson.getLessonStart(), lesson.getLessonEnd(), lesson.getId());
        } catch (DataAccessException e) {
            String msg = format("Unable to update '%s'", lesson);
            throw new QueryNotExecuteException(msg);
        }
    }

    @Override
    public void create(Lesson lesson) throws DAOException {
        LOGGER.debug("Trying to create {}", lesson);

        try {
            jdbcTemplate.update(env.getProperty("sql.lessons.create"), lesson.getCourseId(), lesson.getRoomId(),
                    lesson.getLecturerId(),
                    lesson.getLessonStart(), lesson.getLessonEnd());
        } catch (DataAccessException e) {
            String msg = format("Unable to create '%s'", lesson);
            throw new QueryNotExecuteException(msg);
        }
    }

    @Override
    public void assignLessonToGroup(Integer lessonId, Integer groupId) throws DAOException {
        LOGGER.debug("Trying to assign Lesson with id:{} to Group wit id:{}", lessonId, groupId);
        jdbcTemplate.update(env.getProperty("sql.lessons.add.lessonToGroup"), lessonId, groupId);
    }

    @Override
    public List<Lesson> getByDateTimeIntervalAndGroupId(Integer groupId, LocalDateTime lessonStart,
            LocalDateTime lessonEnd) throws DAOException {
        LOGGER.debug("Trying to get Lessons by date/time interval({},{}) and Group id:{}.", lessonStart, lessonEnd,
                groupId);

        List<Lesson> lessons = new ArrayList<>();
        try {
            lessons = jdbcTemplate.query(env.getProperty("sql.lessons.get.byDateTimeIntervalAndGroupId"),
                    new Object[] { groupId, lessonStart, lessonEnd, lessonStart, lessonEnd, lessonStart, lessonEnd },
                    new LessonMapper());
        } catch (EmptyResultDataAccessException e) {
            String msg = format("Lessons were not found by date/time interval('%s','%s') and Group id:'%s'.",
                    lessonStart, lessonEnd, groupId);
            throw new EntityNotFoundException(msg);
        } catch (DataAccessException e) {
            String msg = format("Unable to get Lessons by date/time interval for Group id:'%s'.", groupId);
            throw new QueryNotExecuteException(msg);
        }

        return lessons;
    }

    @Override
    public List<Lesson> getByDateTimeIntervalAndStudentId(Integer studentId, LocalDateTime lessonStart,
            LocalDateTime lessonEnd) throws DAOException {
        LOGGER.debug("Trying to get Lessons by date/time interval({},{}) and Student id:{}.", lessonStart, lessonEnd,
                studentId);

        List<Lesson> lessons = new ArrayList<>();
        try {
            lessons = jdbcTemplate.query(env.getProperty("sql.lessons.get.byDateTimeIntervalAndStudentId"),
                    new Object[] { studentId, lessonStart, lessonEnd, lessonStart, lessonEnd, lessonStart, lessonEnd },
                    new LessonMapper());
        } catch (EmptyResultDataAccessException e) {
            String msg = format("Lessons were not found by date/time interval('%s','%s') and Student id:'%s'.",
                    lessonStart, lessonEnd, studentId);
            throw new EntityNotFoundException(msg);
        } catch (DataAccessException e) {
            String msg = format("Unable to get Lessons by date/time interval for Student id:'%s'.", studentId);
            throw new QueryNotExecuteException(msg);
        }

        return lessons;
    }

    @Override
    public List<Lesson> getByDateTimeIntervalAndLecturerId(Integer lecturerId, LocalDateTime lessonStart,
            LocalDateTime lessonEnd) throws DAOException {
        LOGGER.debug("Trying to get Lessons by date/time interval({},{}) and Lecturer id:{}.", lessonStart, lessonEnd,
                lecturerId);

        List<Lesson> lessons = new ArrayList<>();
        try {
            lessons = jdbcTemplate.query(env.getProperty("sql.lessons.get.byDateTimeIntervalAndLecturerId"),
                    new Object[] { lecturerId, lessonStart, lessonEnd, lessonStart, lessonEnd, lessonStart, lessonEnd },
                    new LessonMapper());
        } catch (EmptyResultDataAccessException e) {
            String msg = format("Lessons were not found by date/time interval('%s','%s') and Lecturer id:'%s'.",
                    lessonStart, lessonEnd, lecturerId);
            throw new EntityNotFoundException(msg);
        } catch (DataAccessException e) {
            String msg = format("Unable to get Lessons by date/time interval for Lecturer id:'%s'.", lecturerId);
            throw new QueryNotExecuteException(msg);
        }

        return lessons;
    }
}
