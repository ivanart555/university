package com.ivanart555.university.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.EntityNotFoundException;
import com.ivanart555.university.exception.QueryNotExecuteException;
import com.ivanart555.university.services.StudentService;

@Component
public class StudentServiceImpl implements StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
    private static final String QUERY_DIDNT_EXECUTE = "Query didn't execute. Check SQL query.";
    private static final String SOMETHING_WRONG_WITH_DAO = "Something got wrong with DAO.";
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
            throw new ServiceException("There are no Students in database");
        }
        LOGGER.info("All Students were received successfully.");

        return students;
    }

    @Override
    public List<Student> getAllActive() throws ServiceException {
        List<Student> students = studentDAO.getAllActive();
        if (students.isEmpty()) {
            throw new ServiceException("There are no active Students in database");
        }
        LOGGER.info("All active Students were received successfully.");

        return students;
    }

    @Override
    public Student getById(Integer id) throws ServiceException {
        Student student = null;
        try {
            studentDAO.getById(id);
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Student with id {} not found!", id);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Student by id.", e);
        }
        LOGGER.info("Student with id {} received successfully.", id);

        return student;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        studentDAO.delete(id);
        LOGGER.info("Student with id {} deleted successfully.", id);
    }

    @Override
    public void update(Student student) throws ServiceException {
        try {
            studentDAO.update(student);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            throw new ServiceException("Unable to update Student.", e);
        }
        LOGGER.info("Student with id {} updated successfully.", student.getId());
    }

    @Override
    public void create(Student student) throws ServiceException {
        try {
            studentDAO.create(student);
        } catch (DAOException e) {
            throw new ServiceException("Unable to create Student.", e);
        }
        LOGGER.info("Student with id {} created successfully.", student.getId());
    }

    @Override
    public void assignStudentToGroup(Student student, Integer groupId) throws ServiceException {
        try {
            groupDAO.getById(groupId);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(
                    "Failed to assign Student to Group. There is no Group with such id:" + groupId);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to assign Student to Group.", e);
        }

        checkGroupSizeLimitation(groupId);
        checkIfStudentIsNotActive(student);

        student.setGroupId(groupId);
        try {
            studentDAO.update(student);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            throw new ServiceException("Failed to assign Student to Group.", e);
        }
        LOGGER.info("Student with id {} assigned to Group with id: {} successfully.", student.getId(), groupId);
    }

    @Override
    public void assignStudentToCourse(Student student, Integer courseId) throws ServiceException {
        try {
            courseDAO.getById(courseId);
        } catch (EntityNotFoundException e) {
            throw new ServiceException(
                    "Failed to assign Student to Course. There is no Course with such id:" + courseId);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Course by id.", e);
        }

        checkIfStudentIsNotActive(student);

        try {
            studentDAO.addStudentToCourse(student.getId(), courseId);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Failed to assign Student to Course.", e);
        }
        LOGGER.info("Student with id {} assigned to Course with id: {} successfully.", student.getId(), courseId);
    }

    @Override
    public List<Lesson> getDaySchedule(Student student, LocalDate day) throws ServiceException {
        LocalDateTime startDateTime = day.atStartOfDay();
        LocalDateTime endDateTime = day.atTime(23, 59, 59);
        List<Lesson> lessons = null;
        try {
            lessons = lessonDAO.getByDateTimeIntervalAndStudentId(student.getId(), startDateTime, endDateTime);
        } catch (EntityNotFoundException e) {
            throw new ServiceException("Failed to get Student's day schedule.", e);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Student's day schedule.", e);
        }
        LOGGER.info("Student's day schedule received successfully.");

        return lessons;
    }

    private void checkIfStudentIsNotActive(Student student) throws ServiceException {
        if (!student.isActive()) {
            throw new ServiceException("Student is not active.");
        }
    }

    private void checkGroupSizeLimitation(Integer groupId) throws ServiceException {
        int expectedGroupSize = 0;
        try {
            expectedGroupSize = studentDAO.getStudentsByGroupId(groupId).size() + 1;
        } catch (EntityNotFoundException e) {
            throw new ServiceException("Failed to get Students by Group id", e);
        } catch (QueryNotExecuteException e) {
            LOGGER.error(QUERY_DIDNT_EXECUTE);
        } catch (DAOException e) {
            LOGGER.error(SOMETHING_WRONG_WITH_DAO);
            throw new ServiceException("Unable to get Students by Group id.", e);
        }

        int groupMaxSize = Integer.parseInt(env.getProperty("groupMaxSize"));

        if (expectedGroupSize > groupMaxSize) {
            throw new ServiceException(
                    "Failed to assign student to group. Group max size(" + groupMaxSize + ") exceeded.");
        }
    }

    @Override
    public Page<Student> findPaginated(Pageable pageable) throws ServiceException {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Student> allStudents = getAll();
        int studentsSize = allStudents.size();

        List<Student> list;

        if (studentsSize < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, studentsSize);
            list = allStudents.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), studentsSize);
    }
}
