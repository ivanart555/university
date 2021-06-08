package com.ivanart555.university.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.services.StudentService;

@Component
public class StudentServiceImpl implements StudentService {
    private Environment env;
    private StudentDAO studentDAO;
    private GroupDAO groupDAO;
    private CourseDAO courseDAO;
    private LessonDAO lessonDAO;

    @Autowired
    public StudentServiceImpl(StudentDAO studentDAO, GroupDAO groupDAO, CourseDAO courseDAO, LessonDAO lessonDAO,
            Environment env) {
        this.studentDAO = studentDAO;
        this.groupDAO = groupDAO;
        this.courseDAO = courseDAO;
        this.lessonDAO = lessonDAO;
        this.env = env;
    }

    @Override
    public List<Student> getAll() throws ServiceException {
        List<Student> students = studentDAO.getAll();
        if (students.isEmpty()) {
            throw new ServiceException("There are no students in database");
        }
        return students;
    }

    @Override
    public List<Student> getAllActive() throws ServiceException {
        List<Student> students = studentDAO.getAllActive();
        if (students.isEmpty()) {
            throw new ServiceException("There are no active students in database");
        }
        return students;
    }

    @Override
    public Student getById(Integer id) throws ServiceException {
        Student student = studentDAO.getById(id);
        if (student == null) {
            throw new ServiceException("There is no student with such id:" + id);
        }
        return student;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        studentDAO.delete(id);
    }

    @Override
    public void update(Student student) throws ServiceException {
        try {
            studentDAO.update(student);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update student.", e);
        }
    }

    @Override
    public void create(Student student) throws ServiceException {
        try {
            studentDAO.create(student);
        } catch (DAOException e) {
            throw new ServiceException("Unable to create Student.", e);
        }
    }

    @Override
    public void assignStudentToGroup(Student student, Integer groupId) throws ServiceException {
        Group group;
        group = groupDAO.getById(groupId);

        if (group == null) {
            throw new ServiceException("Failed to assign student to group. There is no group with such id:" + groupId);
        }

        checkGroupSizeLimitation(groupId);
        checkIfStudentIsNotActive(student);

        student.setGroupId(groupId);
        try {
            studentDAO.update(student);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign student to group.", e);
        }
    }

    @Override
    public void assignStudentToCourse(Student student, Integer courseId) throws ServiceException {
        Course course;
        course = courseDAO.getById(courseId);

        if (course == null) {
            throw new ServiceException(
                    "Failed to assign student to course. There is no course with such id:" + courseId);
        }

        checkIfStudentIsNotActive(student);

        try {
            studentDAO.addStudentToCourse(student.getId(), courseId);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign student to course.", e);
        }
    }

    @Override
    public List<Lesson> getDaySchedule(Student student, LocalDate day) throws ServiceException {
        LocalDateTime startDateTime = day.atStartOfDay();
        LocalDateTime endDateTime = day.atTime(23, 59, 59);

        try {
            return lessonDAO.getByDateTimeIntervalAndStudentId(student.getId(), startDateTime, endDateTime);
        } catch (Exception e) {
            throw new ServiceException("Failed to get student's day schedule.", e);
        }
    }

    private void checkIfStudentIsNotActive(Student student) throws ServiceException {
        if (!student.isActive()) {
            throw new ServiceException("Student is not active.");
        }
    }

    private void checkGroupSizeLimitation(Integer groupId) throws ServiceException {
        int expectedGroupSize = studentDAO.getStudentsByGroupId(groupId).size() + 1;
        int groupMaxSize = Integer.parseInt(env.getProperty("groupMaxSize"));

        if (expectedGroupSize > groupMaxSize) {
            throw new ServiceException(
                    "Failed to assign student to group. Group max size(" + groupMaxSize + ") exceeded.");
        }
    }
}
