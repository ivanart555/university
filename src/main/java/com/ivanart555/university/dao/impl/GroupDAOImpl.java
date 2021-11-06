package com.ivanart555.university.dao.impl;

import static java.lang.String.format;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.mappers.GroupMapper;

@Component
@Transactional
public class GroupDAOImpl implements GroupDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Group> getAll() {
        LOGGER.debug("Trying to get all Groups.");
        return entityManager.createQuery("FROM Group").getResultList();
    }

    @Override
    public Group getById(Integer id) throws DAOException {
        LOGGER.debug("Trying to get Group by id: {}", id);
        Group group = entityManager.find(Group.class, id);
        if (group == null) {
            String msg = format("Group with id '%s' not found", id);
            throw new EntityNotFoundException(msg);
        }

        LOGGER.debug("Found:'{}'", group);

        return group;
    }

    @Override
    public void delete(Integer id) {
        LOGGER.debug("Trying to delete Group by id: {}", id);
        Group group = entityManager.find(Group.class, id);

        if (group != null) {
            entityManager.remove(group);
        }
    }

    @Override
    public void update(Group group) throws DAOException {
        LOGGER.debug("Trying to update {}", group);
        entityManager.merge(group);
    }

    @Override
    public void create(Group group) throws DAOException {
        LOGGER.debug("Trying to create {}", group);
        entityManager.persist(group);
    }
}
