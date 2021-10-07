package com.ivanart555.university.dao.impl;

import static java.lang.String.format;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.mappers.StudentMapper;

@Component
public class StudentDAOImpl implements StudentDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAOImpl.class);
    private final Environment env;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDAOImpl(JdbcTemplate jdbcTemplate, Environment env) {
        this.jdbcTemplate = jdbcTemplate;
        this.env = env;
    }

    @Override
    public List<Student> getAll() {
        LOGGER.debug("Trying to get all Students.");
        return jdbcTemplate.query(env.getProperty("sql.students.get.all"), new StudentMapper());
    }

    @Override
    public List<Student> getAllActive() {
        LOGGER.debug("Trying to get all active Students.");
        return jdbcTemplate.query(env.getProperty("sql.students.get.all.active"), new StudentMapper());
    }

    @Override
    public Student getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Student by id: {}", id);
        Student student = new Student();
        try {
            student = jdbcTemplate.queryForObject(env.getProperty("sql.students.get.byId"), new Object[] { id },
                    new StudentMapper());
        } catch (EmptyResultDataAccessException e) {
            String msg = format("Student with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        } catch (DataAccessException e) {
            String msg = format("Unable to get Student with ID '%s'", id);
            throw new QueryNotExecuteException(msg);
        }
        LOGGER.debug("Found:'{}'", student);

        return student;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Student by id: {}", id);
        jdbcTemplate.update(env.getProperty("sql.students.delete"), id);
    }

    @Override
    public void update(Student student) throws DAOException {
        LOGGER.debug("Trying to update {}", student);

        try {
            jdbcTemplate.update(env.getProperty("sql.students.update"), student.getFirstName(), student.getLastName(),
                    student.getGroupId(), student.isActive(), student.getId());
        } catch (DataAccessException e) {
            String msg = format("Unable to update '%s'", student);
            throw new QueryNotExecuteException(msg);
        }
    }

    @Override
    public void create(Student student) throws DAOException {
        LOGGER.debug("Trying to create {}", student);

        KeyHolder key = new GeneratedKeyHolder();
        try {
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
        } catch (DataAccessException e) {
            String msg = format("Unable to create '%s'", student);
            throw new QueryNotExecuteException(msg);
        }

        student.setId((int) key.getKeys().get("student_id"));
    }

    @Override
    public void addStudentToCourse(Integer studentId, Integer courseId) throws DAOException {
        LOGGER.debug("Trying to add Student with id:{} to Course wit id:{}", studentId, courseId);
        jdbcTemplate.update(env.getProperty("sql.students.add.studentToCourse"), studentId, courseId);
    }

    @Override
    public List<Student> getStudentsByGroupId(Integer groupId) throws DAOException {
        LOGGER.debug("Trying to get Students by Group with id:{}", groupId);
        List<Student> students = new ArrayList<>();
        try {
            students = jdbcTemplate.query(env.getProperty("sql.students.get.studentsByGroupId"),
                    new Object[] { groupId },
                    new StudentMapper());
        } catch (EmptyResultDataAccessException e) {
            String msg = format("Students in Group with id: '%s' were not found.", groupId);
            throw new EntityNotFoundException(msg);
        } catch (DataAccessException e) {
            String msg = format("Unable to get Students in Group with id: '%s'", groupId);
            throw new QueryNotExecuteException(msg);
        }

        return students;
    }
}
