package com.ivanart555.university.services.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.ClassroomService;

@Component
public class ClassroomServiceImpl implements ClassroomService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomServiceImpl.class);
    private ClassroomDAO classroomDAO;

    @Autowired
    public ClassroomServiceImpl(ClassroomDAO classroomDAO) {
        this.classroomDAO = classroomDAO;
    }

    @Override
    public List<Classroom> getAll() throws ServiceException {
        List<Classroom> classrooms = classroomDAO.getAll();
        if (classrooms.isEmpty()) {
            throw new ServiceException("There are no Classrooms in database");
        }
        LOGGER.info("All Classrooms received successfully.");

        return classrooms;
    }

    @Override
    public Classroom getById(Integer id) throws ServiceException {
        Classroom classroom = null;
        try {
            classroom = classroomDAO.getById(id);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Classroom with id {} not found!", id);
        } catch (DAOException e) {
            LOGGER.error("Something got wrong with DAO.");
            throw new ServiceException("Unable to get Classroom by id.", e);
        }
        LOGGER.info("Classroom with id {} received successfully.", id);

        return classroom;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        classroomDAO.delete(id);
        LOGGER.info("Classroom with id {} deleted successfully.", id);
    }

    @Override
    public void update(Classroom classroom) throws ServiceException {
        try {
            classroomDAO.update(classroom);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Classroom.", e);
        }
        LOGGER.info("Classroom with id {} updated successfully.", classroom.getId());
    }

    @Override
    public void create(Classroom classroom) throws ServiceException {
        try {
            classroomDAO.create(classroom);
        } catch (DAOException e) {
            throw new ServiceException("Unable to create Classroom.", e);
        }
        LOGGER.info("Classroom with id {} created successfully.", classroom.getId());
    }

    @Override
    public Page<Classroom> findPaginated(Pageable pageable) throws ServiceException {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Classroom> allClassrooms = getAll();
        int classroomsSize = allClassrooms.size();

        List<Classroom> list;

        if (classroomsSize < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, classroomsSize);
            list = allClassrooms.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), classroomsSize);
    }
}
