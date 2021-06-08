package com.ivanart555.university.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.LecturerService;

@Component
public class LecturerServiceImpl implements LecturerService {
    private LecturerDAO lecturerDAO;
    private CourseDAO courseDAO;
    private GroupDAO groupDAO;
    private LessonDAO lessonDAO;

    @Autowired
    public LecturerServiceImpl(LecturerDAO lecturerDAO, CourseDAO courseDAO, GroupDAO groupDAO, LessonDAO lessonDAO) {
        this.lecturerDAO = lecturerDAO;
        this.courseDAO = courseDAO;
        this.groupDAO = groupDAO;
        this.lessonDAO = lessonDAO;
    }

    @Override
    public List<Lecturer> getAll() throws ServiceException {
        List<Lecturer> lecturers = lecturerDAO.getAll();
        if (lecturers.isEmpty()) {
            throw new ServiceException("There are no lecturers in database");
        }
        return lecturers;
    }

    @Override
    public List<Lecturer> getAllActive() throws ServiceException {
        List<Lecturer> lecturers = lecturerDAO.getAllActive();
        if (lecturers.isEmpty()) {
            throw new ServiceException("There are no active lecturers in database");
        }
        return lecturers;
    }

    @Override
    public Lecturer getById(Integer id) throws ServiceException {
        Lecturer lecturer = lecturerDAO.getById(id);
        if (lecturer == null) {
            throw new ServiceException("There is no lecturer with such id:" + id);
        }

        return lecturer;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        lecturerDAO.delete(id);
    }

    @Override
    public void update(Lecturer lecturer) throws ServiceException {
        try {
            lecturerDAO.update(lecturer);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update lecturer.", e);
        }
    }

    @Override
    public void create(Lecturer lecturer) throws ServiceException {
        try {
            lecturerDAO.create(lecturer);
        } catch (DAOException e) {
            throw new ServiceException("Unable to create lecturer.", e);
        }
    }

    @Override
    public void addLecturerToCourse(Lecturer lecturer, Integer courseId) throws ServiceException {
        Course course;
        course = courseDAO.getById(courseId);

        if (course == null) {
            throw new ServiceException(
                    "Failed to assign student to course. There is no course with such id:" + courseId);
        }

        checkIfLecturerIsNotActive(lecturer);

        try {
            lecturerDAO.addLecturerToCourse(lecturer.getId(), courseId);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign lecturer to course.", e);
        }
    }

    @Override
    public void addLecturerToGroup(Lecturer lecturer, Integer groupId) throws ServiceException {
        Group group;
        group = groupDAO.getById(groupId);

        if (group == null) {
            throw new ServiceException("Failed to assign lecturer to group. There is no group with such id:" + groupId);
        }

        checkIfLecturerIsNotActive(lecturer);

        try {
            lecturerDAO.addLecturerToGroup(lecturer.getId(), groupId);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign lecturer to course.", e);
        }
    }
    
    @Override
    public List<Lesson> getDaySchedule(Lecturer lecturer, LocalDate day) throws ServiceException {
        LocalDateTime startDateTime = day.atStartOfDay();
        LocalDateTime endDateTime = day.atTime(23, 59, 59);

        try {
            return lessonDAO.getByDateTimeIntervalAndLecturerId(lecturer.getId(), startDateTime, endDateTime);
        } catch (Exception e) {
            throw new ServiceException("Failed to get lecturer's day schedule.", e);
        }
    }
    
    private void checkIfLecturerIsNotActive(Lecturer lecturer) throws ServiceException {
        if (!lecturer.isActive()) {
            throw new ServiceException("Lecturer is not active.");
        }
    }
}
