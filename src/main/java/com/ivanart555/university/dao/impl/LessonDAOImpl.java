package com.ivanart555.university.dao.impl;

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

    @Autowired
    private Environment env;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LessonDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
    public void delete(Integer id) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.lessons.delete"), id);
    }

    @Override
    public void update(Lesson lesson) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.lessons.update"), lesson.getCourseId(), lesson.getRoomId(),
                lesson.getLessonStart(), lesson.getLessonEnd(), lesson.getId());
    }

    @Override
    public void create(Lesson lesson) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.lessons.create"), lesson.getCourseId(), lesson.getRoomId(),
                lesson.getLessonStart(), lesson.getLessonEnd());
    }
}
