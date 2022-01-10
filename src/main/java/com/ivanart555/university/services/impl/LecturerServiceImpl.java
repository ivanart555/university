package com.ivanart555.university.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.CourseRepository;
import com.ivanart555.university.repository.LecturerRepository;
import com.ivanart555.university.repository.LessonRepository;
import com.ivanart555.university.services.LecturerService;

@Component
public class LecturerServiceImpl implements LecturerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerServiceImpl.class);
    private LecturerRepository lecturerRepository;
    private CourseRepository courseRepository;
    private LessonRepository lessonRepository;

    @Autowired
    public LecturerServiceImpl(LecturerRepository lecturerRepository, CourseRepository courseRepository, LessonRepository lessonRepository) {
        this.lecturerRepository = lecturerRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Lecturer> findAll() throws ServiceException {
        List<Lecturer> lecturers = lecturerRepository.findAll();
        LOGGER.info("All Lecturers received successfully.");
        return lecturers;
    }

    @Override
    public Page<Lecturer> findAll(Pageable pageable) throws ServiceException {
        Page<Lecturer> lecturers = lecturerRepository.findAll(pageable);
        LOGGER.info("All Lecturers received successfully.");
        return lecturers;
    }

    @Override
    public List<Lecturer> getAllActive() throws ServiceException {
        List<Lecturer> lecturers = lecturerRepository.getAllActive();
        LOGGER.info("All active Lecturers received successfully.");
        return lecturers;
    }

    @Override
    public Lecturer findById(Integer id) throws ServiceException {
        Lecturer lecturer = null;
        try {
            lecturer = lecturerRepository.findById(id).orElseThrow(() -> new ServiceException(
                    String.format("Lecturer with id %d not found!", id)));
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Lecturer with id {} not found!", id);
        }
        LOGGER.info("Lecturer with id {} received successfully.", id);

        return lecturer;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        lecturerRepository.deleteById(id);
        LOGGER.info("Lecturer with id {} deleted successfully.", id);
    }

    @Override
    public void save(Lecturer lecturer) throws ServiceException {
        setNullCourseWhenNullId(lecturer);

        if (lecturer.getCourse() != null) {
            addLecturerToCourse(lecturer, lecturer.getCourse());
        }
        lecturerRepository.save(lecturer);
        LOGGER.info("Lecturer with id {} saved successfully.", lecturer.getId());
    }

    @Override
    public void addLecturerToCourse(Lecturer lecturer, Course course) throws ServiceException {
        checkIfLecturerIsNotActive(lecturer);

        try {
            course = courseRepository.getById(course.getId());
        } catch (EntityNotFoundException e) {
            throw new ServiceException(
                    "Failed to assign Lecturer to Course. There is no Course with such id:" + course.getId());
        }

        lecturer.setCourse(course);
        LOGGER.info("Lecturer with id:{} added to Course with id:{} successfully.", lecturer.getId(), course.getId());
    }

    @Override
    public List<Lesson> getDaySchedule(Lecturer lecturer, LocalDate day) throws ServiceException {
        LocalDateTime startDateTime = day.atStartOfDay();
        LocalDateTime endDateTime = day.atTime(23, 59, 59);
        List<Lesson> lessons = new ArrayList<>();

        try {
            lessons = lessonRepository.getByDateTimeIntervalAndLecturerId(lecturer.getId(), startDateTime, endDateTime);
        } catch (EntityNotFoundException e) {
            LOGGER.info("Schedule for Lecturer with id {} not found", lecturer.getId());
        }
        LOGGER.info("Schedule for Lecturer with id {} received successfully.", lecturer.getId());

        return lessons;
    }

    private void checkIfLecturerIsNotActive(Lecturer lecturer) throws ServiceException {
        if (!lecturer.isActive()) {
            throw new ServiceException("Lecturer is not active.");
        }
    }

    private void setNullCourseWhenNullId(Lecturer lecturer) {
        Course course = lecturer.getCourse();
        if (course != null && course.getId() == null) {
            lecturer.setCourse(null);
        }
    }
}
