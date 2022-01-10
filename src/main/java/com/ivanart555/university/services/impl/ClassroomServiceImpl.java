package com.ivanart555.university.services.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.ClassroomRepository;
import com.ivanart555.university.services.ClassroomService;

@Component
public class ClassroomServiceImpl implements ClassroomService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomServiceImpl.class);
    private ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomServiceImpl(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public List<Classroom> findAll() throws ServiceException {
        List<Classroom> classrooms = classroomRepository.findAll();
        LOGGER.info("All Classrooms received successfully.");

        return classrooms;
    }

    @Override
    public Page<Classroom> findAll(Pageable pageable) throws ServiceException {
        Page<Classroom> groups = classroomRepository.findAll(pageable);
        LOGGER.info("All Groups received successfully.");
        return groups;
    }

    @Override
    public Classroom findById(Integer id) throws ServiceException {
        Classroom classroom = null;
        try {
            classroom = classroomRepository.findById(id).orElseThrow(() -> new ServiceException(
                    String.format("Classroom with id %d not found!", id)));
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Classroom with id {} not found!", id);
        }
        LOGGER.info("Classroom with id {} received successfully.", id);

        return classroom;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        classroomRepository.deleteById(id);
        LOGGER.info("Classroom with id {} deleted successfully.", id);
    }

    @Override
    public void save(Classroom classroom) throws ServiceException {
        classroomRepository.save(classroom);
        LOGGER.info("Classroom with id {} saved successfully.", classroom.getId());
    }
}
