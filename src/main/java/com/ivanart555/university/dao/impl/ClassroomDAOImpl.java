package com.ivanart555.university.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.CourseMapper;
import com.ivanart555.university.exception.DAOException;

@Component
public class ClassroomDAOImpl implements ClassroomDAO {

    @Autowired
    Environment env;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassroomDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Classroom> getAll() {
        return jdbcTemplate.query(env.getProperty("sql.courses.get.all"), new CourseMapper());
    }

    @Override
    public Classroom getById(Integer id) {
        return jdbcTemplate
                .query(env.getProperty("sql.courses.get.byId"), new Object[] { id },
                        new CourseMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public void delete(Integer id) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.courses.delete"), id);
    }

    @Override
    public void update(Classroom classRoom) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.courses.update"), classRoom.getCourseName(),
                course.getCourseDescription(),
                course.getCourseId());
    }

    @Override
    public void create(Classroom classRoom) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.courses.create"), classRoom.getCourseName(),
                classRoom.getCourseDescription());
    }
}
