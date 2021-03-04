package com.ivanart555.university.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.entities.StudentMapper;
import com.ivanart555.university.exception.DAOException;

@Component
public class StudentDAOImpl implements StudentDAO {

    @Autowired
    Environment env;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query(env.getProperty("sql.students.get.all"), new StudentMapper());
    }

    @Override
    public Student getById(Integer id) {
        return jdbcTemplate
                .query(env.getProperty("sql.students.get.byId"), new Object[] { id },
                        new StudentMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public void delete(Integer id) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.students.delete"), id);
    }

    @Override
    public void update(Student student) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.students.update"), student.getFirstName(), student.getLastName(),
                student.getGroupId(), student.getStudentId());
    }

    @Override
    public void create(Student student) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.students.create"), student.getFirstName(), student.getLastName());
    }
}
