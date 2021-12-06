package com.ivanart555.university.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.LecturerService;

@Component
public class LecturerServiceImpl implements LecturerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LecturerServiceImpl.class);
    private static final String QUERY_DIDNT_EXECUTE = "Query didn't execute. Check SQL query.";
    private static final String SOMETHING_WRONG_WITH_DAO = "Something got wrong with DAO.";
    private LecturerDAO lecturerDAO;
    private CourseDAO courseDAO;
    private LessonDAO lessonDAO;

    @Autowired
    public LecturerServiceImpl(LecturerDAO lecturerDAO, CourseDAO courseDAO, LessonDAO lessonDAO) {
        this.lecturerDAO = lecturerDAO;
        this.courseDAO = courseDAO;
        this.lessonDAO = lessonDAO;
    }

    @Override
    public List<Lecturer> getAll() throws ServiceException {
        List<Lecturer> lecturers = lecturerDAO.getAll();
        if (lecturers.isEmpty()) {
            throw new ServiceException("There are no Lecturers in database");
        }
        LOGGER.info("All Lecturers received successfully.");

        return lecturers;
    }

    @Override
    public List<Lecturer> getAllActive() throws ServiceException {
        List<Lecturer> lecturers = lecturerDAO.getAllActive();
        if (lecturers.isEmpty()) {
            throw new ServiceException("There are no active Lecturers in database");
        }
        LOGGER.info("All active Lecturers received successfully.");

        return lecturers;
    }

    @Override
    public Lecturer getById(Integer id) throws ServiceException {
        Lecturer lecturer = null;
        try {
            lecturer = lecturerDAO.getById(id);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Lecturer with id {} not found!", id);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Lecturer by id.", e);
        }
        LOGGER.info("Lecturer with id {} received successfully.", id);

        return lecturer;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        lecturerDAO.delete(id);
        LOGGER.info("Lecturer with id {} deleted successfully.", id);
    }

    @Override
    public void update(Lecturer lecturer) throws ServiceException {
        setNullCourseWhenZeroId(lecturer, lecturer.getCourse().getId());

        if (lecturer.getCourse() != null) {
            addLecturerToCourse(lecturer, lecturer.getCourse().getId());
        }

        try {
            lecturerDAO.update(lecturer);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Lecturer.", e);
        }
        LOGGER.info("Lecturer with id {} updated successfully.", lecturer.getId());
    }

    @Override
    public void create(Lecturer lecturer) throws ServiceException {
        setNullCourseWhenZeroId(lecturer, lecturer.getCourse().getId());

        try {
            lecturerDAO.create(lecturer);
        } catch (DAOException e) {
            throw new ServiceException("Unable to create Lecturer.", e);
        }
        LOGGER.info("Lecturer with id {} created successfully.", lecturer.getId());
    }

    @Override
    public void addLecturerToCourse(Lecturer lecturer, Integer courseId) throws ServiceException {
        Course course = null;

        try {
            course = courseDAO.getById(courseId);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(
                    "Failed to assign Lecturer to Course. There is no Course with such id:" + courseId);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Course by id.", e);
        }

        checkIfLecturerIsNotActive(lecturer);

        lecturer.setCourse(course);
        LOGGER.info("Lecturer with id:{} added to Course with id:{} successfully.", lecturer.getId(), courseId);
    }

    @Override
    public List<Lesson> getDaySchedule(Lecturer lecturer, LocalDate day) throws ServiceException {
        LocalDateTime startDateTime = day.atStartOfDay();
        LocalDateTime endDateTime = day.atTime(23, 59, 59);
        List<Lesson> lessons = new ArrayList<>();

        try {
            lessons = lessonDAO.getByDateTimeIntervalAndLecturerId(lecturer.getId(), startDateTime, endDateTime);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Schedule for Lecturer with id {} not found", lecturer.getId());
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Schedule for Lecturer.", e);
        }
        LOGGER.info("Schedule for Lecturer with id {} received successfully.", lecturer.getId());

        return lessons;
    }

    private void checkIfLecturerIsNotActive(Lecturer lecturer) throws ServiceException {
        if (!lecturer.isActive()) {
            throw new ServiceException("Lecturer is not active.");
        }
    }

    @Override
    public Page<Lecturer> findPaginated(Pageable pageable) throws ServiceException {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Lecturer> allLecturers = getAll();
        int lecturersSize = allLecturers.size();

        List<Lecturer> list;

        if (lecturersSize < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, lecturersSize);
            list = allLecturers.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), lecturersSize);
    }

    public void setNullCourseWhenZeroId(Lecturer lecturer, int courseId) {
        if (courseId == 0) {
            lecturer.setCourse(null);
        }
    }
}
