package com.ivanart555.university.services.impl;

import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.ClassroomRepository;
import com.ivanart555.university.services.ClassroomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class ClassroomServiceImpl implements ClassroomService {
    private final ClassroomRepository classroomRepository;

    @Override
    public List<Classroom> findAll() throws ServiceException {
        List<Classroom> classrooms = classroomRepository.findAll();
        log.info("All Classrooms received successfully.");

        return classrooms;
    }

    @Override
    public Page<Classroom> findAll(Pageable pageable) throws ServiceException {
        Page<Classroom> groups = classroomRepository.findAll(pageable);
        log.info("All Groups received successfully.");
        return groups;
    }

    @Override
    public Classroom findById(Integer id) throws ServiceException {
        Classroom classroom = null;
        try {
            classroom = classroomRepository.findById(id).orElseThrow(() -> new ServiceException(
                    String.format("Classroom with id %d not found!", id)));
        } catch (EntityNotFoundException e) {
            log.warn("Classroom with id {} not found!", id);
        }
        log.info("Classroom with id {} received successfully.", id);

        return classroom;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        classroomRepository.deleteById(id);
        log.info("Classroom with id {} deleted successfully.", id);
    }

    @Override
    public int save(Classroom classroom) throws ServiceException {
        Classroom createdClassroom = classroomRepository.save(classroom);
        log.info("Classroom with id {} saved successfully.", createdClassroom.getId());
        return createdClassroom.getId();
    }
}
