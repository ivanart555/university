package com.ivanart555.university.services.impl;

import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.GroupRepository;
import com.ivanart555.university.repository.LessonRepository;
import com.ivanart555.university.repository.StudentRepository;
import com.ivanart555.university.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class StudentServiceImpl implements StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
    private Environment env;
    private StudentRepository studentRepository;
    private GroupRepository groupRepository;
    private LessonRepository lessonRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, GroupRepository groupRepository,
                              LessonRepository lessonRepository,
                              Environment env) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.lessonRepository = lessonRepository;
        this.env = env;
    }

    @Override
    public List<Student> findAll() throws ServiceException {
        List<Student> students = studentRepository.findAll();
        LOGGER.info("All Students were received successfully.");
        return students;
    }

    @Override
    public Page<Student> findAll(Pageable pageable) throws ServiceException {
        Page<Student> students = studentRepository.findAll(pageable);
        LOGGER.info("All Students were received successfully.");
        return students;
    }

    @Override
    public List<Student> getAllActive() throws ServiceException {
        List<Student> students = studentRepository.getAllActive();
        LOGGER.info("All active Students were received successfully.");
        return students;
    }

    @Override
    public Student findById(Integer id) throws ServiceException {
        Student student = null;
        try {
            student = studentRepository.findById(id)
                    .orElseThrow(() -> new ServiceException(String.format("Student with id %d not found!", id)));
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Student with id {} not found!", id);
        }
        LOGGER.info("Student with id {} received successfully.", id);
        return student;
    }

    @Override
    public void delete(Integer id) throws ServiceException {
        studentRepository.deleteById(id);
        LOGGER.info("Student with id {} deleted successfully.", id);
    }

    @Override
    public void save(Student student) throws ServiceException {
        setNullGroupWhenNullId(student);

        if (student.getGroup() != null) {
            addStudentToGroup(student, student.getGroup());
        }

        studentRepository.save(student);
        LOGGER.info("Student with id {} updated successfully.", student.getId());
    }

    @Override
    public void addStudentToGroup(Student student, Group group) throws ServiceException {
        checkGroupSizeLimitation(group.getId());
        checkIfStudentIsNotActive(student);

        try {
            group = groupRepository.getById(group.getId());
        } catch (EntityNotFoundException e) {
            throw new ServiceException(
                    "Failed to assign Student to Group. There is no Group with such id:" + group.getId());
        }

        student.setGroup(group);
        LOGGER.info("Student with id:{} added to Group with id:{} successfully.", student.getId(), group.getId());
    }

    @Override
    public List<Lesson> getDaySchedule(Student student, LocalDate day) throws ServiceException {
        LocalDateTime startDateTime = day.atStartOfDay();
        LocalDateTime endDateTime = day.atTime(23, 59, 59);
        List<Lesson> lessons = null;
        try {
            lessons = lessonRepository
                    .findAllByGroupIdAndLessonStartLessThanEqualAndLessonEndGreaterThanEqual(
                            student.getGroup().getId(), endDateTime, startDateTime);
        } catch (EntityNotFoundException e) {
            throw new ServiceException("Failed to get Student's day schedule.", e);
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
            expectedGroupSize = studentRepository.findByGroupId(groupId).size() + 1;
        } catch (EntityNotFoundException e) {
            LOGGER.warn("Students with Group id {} not found!", groupId);
        }

        int groupMaxSize = Integer.parseInt(env.getProperty("groupMaxSize"));

        if (expectedGroupSize > groupMaxSize) {
            throw new ServiceException(
                    "Failed to assign student to group. Group max size(" + groupMaxSize + ") exceeded.");
        }
    }

    private void setNullGroupWhenNullId(Student student) {
        Group group = student.getGroup();

        if (group != null && group.getId() == null) {
            student.setGroup(null);
        }
    }

}
