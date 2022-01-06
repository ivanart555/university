package com.ivanart555.university.dao.impl;

import static java.lang.String.format;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;

@Component
@Transactional
public class StudentDAOImpl implements StudentDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Student> getAll() {
        LOGGER.debug("Trying to get all Students.");
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s LEFT JOIN FETCH s.group ORDER BY s.id",
                Student.class);
        return query.getResultList();
    }

    @Override
    public List<Student> getAllActive() {
        LOGGER.debug("Trying to get all active Students.");
        TypedQuery<Student> query = entityManager.createQuery(
                "SELECT s FROM Student s LEFT JOIN FETCH s.group WHERE s.active = TRUE ORDER BY s.id",
                Student.class);
        return query.getResultList();
    }

    @Override
    public Student getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Student by id: {}", id);
        Student student = entityManager.find(Student.class, id);
        if (student == null) {
            String msg = format("Student with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.debug("Found:'{}'", student);

        return student;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Student by id: {}", id);
        Student student = entityManager.find(Student.class, id);

        if (student != null) {
            entityManager.remove(student);
        }
    }

    @Override
    public void update(Student student) throws DAOException {
        LOGGER.debug("Trying to update {}", student);
        entityManager.merge(student);
    }

    @Override
    public void create(Student student) throws DAOException {
        LOGGER.debug("Trying to create {}", student);
        entityManager.persist(student);
    }

    @Override
    public List<Student> getStudentsByGroupId(Integer groupId) throws DAOException {
        LOGGER.debug("Trying to get Students by Group with id:{}", groupId);
        TypedQuery<Student> query = entityManager
                .createQuery("SELECT s FROM Student s WHERE s.group.id = :groupId",
                        Student.class)
                .setParameter("groupId", groupId);
        return query.getResultList();
    }
}
