package com.ivanart555.university.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.mappers.StudentMapper;

@Component
public class StudentDAOImpl implements StudentDAO {
    private final Environment env;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query(env.getProperty("sql.students.get.all"), new StudentMapper());
    }
    
    @Override
    public List<Student> getAllActive() {
        return jdbcTemplate.query(env.getProperty("sql.students.get.all.active"), new StudentMapper());
    }

    @Override
    public Student getById(Integer id) {
        return jdbcTemplate
                .query(env.getProperty("sql.students.get.byId"), new Object[] { id },
                        new StudentMapper())
                .stream().findAny().orElse(null);
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(env.getProperty("sql.students.delete"), id);
    }

    @Override
    public void update(Student student) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.students.update"), student.getFirstName(), student.getLastName(),
                student.getGroupId(), student.getId());
    }

    @Override
    public void create(Student student) throws DAOException {
        KeyHolder key = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(env.getProperty("sql.students.create"),
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, student.getFirstName());
                ps.setString(2, student.getLastName());
                ps.setBoolean(3, student.isActive());
                return ps;
            }
        }, key);

        student.setId((int) key.getKeys().get("student_id"));
    }

    @Override
    public void addStudentToCourse(Integer studentId, Integer courseId) throws DAOException {
        jdbcTemplate.update(env.getProperty("sql.students.add.studentToCourse"), studentId, courseId);
    }

    @Override
    public List<Student> getStudentsByGroupId(Integer groupId) {
        return jdbcTemplate.query(env.getProperty("sql.students.get.studentsByGroupId"), new Object[] { groupId } , new StudentMapper());
    }
}