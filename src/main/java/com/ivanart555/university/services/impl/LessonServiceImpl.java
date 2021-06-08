package com.ivanart555.university.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.services.LessonService;

@Component
public class LessonServiceImpl implements LessonService {
    private LessonDAO lessonDAO;
    private CourseDAO courseDAO;
    private ClassroomDAO classroomDAO;
    private LecturerDAO lecturerDAO;
    private GroupDAO groupDAO;

    @Autowired
    public LessonServiceImpl(LessonDAO lessonDAO, CourseDAO courseDAO, ClassroomDAO classroomDAO,
            LecturerDAO lecturerDAO, GroupDAO groupDAO) {
        this.lessonDAO = lessonDAO;
        this.courseDAO = courseDAO;
        this.classroomDAO = classroomDAO;
        this.lecturerDAO = lecturerDAO;
        this.groupDAO = groupDAO;
    }

    @Override
    public List<Lesson> getAll() throws ServiceException {
        List<Lesson> lessons = lessonDAO.getAll();
        if (lessons.isEmpty()) {
            throw new ServiceException("There are no Lessons in database");
        }
        return lessons;
    }

    @Override
    public Lesson getById(Integer id) throws ServiceException {
        Lesson lesson = lessonDAO.getById(id);
        if (lesson == null) {
            throw new ServiceException("There is no Lesson with such id:" + id);
        }

        return lesson;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        lessonDAO.delete(id);
    }

    @Override
    public void update(Lesson lesson) throws ServiceException {
        try {
            lessonDAO.update(lesson);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Lesson.", e);
        }
    }

    @Override
    public void create(Lesson lesson) throws ServiceException {
        try {
            lessonDAO.create(lesson);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Lesson.", e);
        }
    }

    @Override
    public void assignLessonToCourse(Lesson lesson, Integer courseId) throws ServiceException {
        Course course;
        course = courseDAO.getById(courseId);

        if (course == null) {
            throw new ServiceException(
                    "Failed to assign lesson to course. There is no course with such id:" + courseId);
        }
        lesson.setCourseId(courseId);

        try {
            lessonDAO.update(lesson);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign lesson to course.", e);
        }
    }

    @Override
    public void assignLessonToClassroom(Lesson lesson, Integer roomId) throws ServiceException {
        Classroom classroom;
        classroom = classroomDAO.getById(roomId);

        if (classroom == null) {
            throw new ServiceException(
                    "Failed to assign lesson to classroom. There is no classroom with such id:" + roomId);
        }
        lesson.setRoomId(roomId);

        try {
            lessonDAO.update(lesson);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign lesson to classroom.", e);
        }
    }

    @Override
    public void assignLessonToLecturer(Lesson lesson, Integer lecturerId) throws ServiceException {
        Lecturer lecturer;
        lecturer = lecturerDAO.getById(lecturerId);

        if (lecturer == null) {
            throw new ServiceException(
                    "Failed to assign lesson to lecturer. There is no lecturer with such id:" + lecturerId);
        }
        lesson.setLecturerId(lecturerId);

        try {
            lessonDAO.update(lesson);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign lesson to lecturer.", e);
        }
    }

    @Override
    public void assignLessonToGroup(Lesson lesson, Integer groupId) throws ServiceException {
        Group group;
        group = groupDAO.getById(groupId);

        if (group == null) {
            throw new ServiceException(
                    "Failed to assign lesson to group. There is no group with such id:" + groupId);
        }

        try {
            lessonDAO.assignLessonToGroup(lesson.getId(), groupId);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign lesson to group.", e);
        }
    }
    
    @Override
    public void assignLessonToDateTimeForGroup(Lesson lesson, LocalDateTime lessonStart, LocalDateTime lessonEnd, Integer groupId)
            throws ServiceException {

        if (!timeIsFree(groupId, lessonStart, lessonEnd)) {
            throw new ServiceException("Failed to assign lesson to date and time. Date and time are occupied.");
        }

        lesson.setLessonStart(lessonStart);
        lesson.setLessonEnd(lessonEnd);

        try {
            lessonDAO.update(lesson);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign lesson to date and time.", e);
        }
    }

    private boolean timeIsFree(Integer groupId, LocalDateTime lessonStart, LocalDateTime lessonEnd) {
        List<Lesson> lessons = lessonDAO.getByDateTimeIntervalAndGroupId(groupId, lessonStart, lessonEnd);
        return lessons.isEmpty();
    }
}
