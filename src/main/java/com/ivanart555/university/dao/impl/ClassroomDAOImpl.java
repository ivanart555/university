package com.ivanart555.university.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;

import static java.lang.String.format;

@Component
@Transactional
public class ClassroomDAOImpl implements ClassroomDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Classroom> getAll() {
        LOGGER.debug("Trying to get all Classrooms.");
        TypedQuery<Classroom> query = entityManager.createQuery("FROM Classroom", Classroom.class);
        return query.getResultList();
    }

    @Override
    public Classroom getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Classroom by id: {}", id);
        Classroom classroom = entityManager.find(Classroom.class, id);
        if (classroom == null) {
            String msg = format("Classroom with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }

        LOGGER.debug("Found:'{}'", classroom);

        return classroom;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Classroom by id: {}", id);
        Classroom classroom = entityManager.find(Classroom.class, id);

        if (classroom != null) {
            entityManager.remove(classroom);
        }
    }

    @Override
    public void update(Classroom classRoom) throws DAOException {
        LOGGER.debug("Trying to update {}", classRoom);
        entityManager.merge(classRoom);
    }

    @Override
    public void create(Classroom classRoom) throws DAOException {
        LOGGER.debug("Trying to create {}", classRoom);
        entityManager.persist(classRoom);
    }
}