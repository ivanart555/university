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

import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;

@Component
@Transactional
public class LecturerDAOImpl implements LecturerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Lecturer> getAll() {
        LOGGER.debug("Trying to get all Lecturers.");
        TypedQuery<Lecturer> query = entityManager.createQuery("SELECT l FROM Lecturer l LEFT JOIN FETCH l.course",
                Lecturer.class);
        return query.getResultList();
    }

    @Override
    public List<Lecturer> getAllActive() {
        LOGGER.debug("Trying to get all active Lecturers.");
        TypedQuery<Lecturer> query = entityManager
                .createQuery("SELECT l FROM Lecturer l LEFT JOIN FETCH l.course WHERE s.active = TRUE", Lecturer.class);
        return query.getResultList();
    }

    @Override
    public Lecturer getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Lecturer by id: {}", id);
        Lecturer lecturer = entityManager.find(Lecturer.class, id);
        if (lecturer == null) {
            String msg = format("Lecturer with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }
        LOGGER.debug("Found:'{}'", lecturer);

        return lecturer;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Lecturer by id: {}", id);
        Lecturer lecturer = entityManager.find(Lecturer.class, id);

        if (lecturer != null) {
            entityManager.remove(lecturer);
        }
    }

    @Override
    public void update(Lecturer lecturer) throws DAOException {
        LOGGER.debug("Trying to update {}", lecturer);
        entityManager.merge(lecturer);
    }

    @Override
    public void create(Lecturer lecturer) throws DAOException {
        LOGGER.debug("Trying to create {}", lecturer);
        entityManager.persist(lecturer);
    }
}
