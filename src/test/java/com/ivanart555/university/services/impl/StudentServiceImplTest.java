package com.ivanart555.university.services.impl;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.dao.StudentDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.ServiceException;

@SpringJUnitConfig(TestSpringConfig.class)
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentDAO studentDAO;
    @Mock
    private GroupDAO groupDAO;
    @Mock
    private CourseDAO courseDAO;
    @Mock
    private LessonDAO lessonDAO;
    @Mock
    private Student student;
    @Mock
    private Group group;
    @Mock
    private Course course;

    @InjectMocks
    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAll() throws ServiceException {
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentDAO.getAll()).thenReturn(students);

        studentServiceImpl.getAll();
        verify(studentDAO).getAll();
    }

    @Test
    void shouldInvokeGetAllActiveMethod_whenCalledGetAllActive() throws ServiceException {
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentDAO.getAllActive()).thenReturn(students);

        studentServiceImpl.getAllActive();
        verify(studentDAO).getAllActive();
    }

    @Test
    void shouldInvokeGetByIdMethod_whenCalledGetById() throws ServiceException, DAOException {
        when(studentDAO.getById(anyInt())).thenReturn(student);

        studentServiceImpl.getById(anyInt());
        verify(studentDAO).getById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        studentServiceImpl.delete(anyInt());
        verify(studentDAO).delete(anyInt());
    }

    @Test
    void shouldInvokeUpdateMethod_whenCalledUpdate() throws ServiceException, DAOException {
        studentServiceImpl.update(student);
        verify(studentDAO).update(student);
    }

    @Test
    void shouldInvokeCreateMethod_whenCalledCreate() throws ServiceException, DAOException {
        studentServiceImpl.create(student);
        verify(studentDAO).create(student);
    }

    @Test
    void shouldInvokeGetByIdAndUpdateMethods_whenCalledAssignStudentToGroup() throws ServiceException, DAOException {
        when(groupDAO.getById(anyInt())).thenReturn(group);
        when(student.isActive()).thenReturn(true);

        studentServiceImpl.assignStudentToGroup(student, anyInt());
        verify(groupDAO).getById(anyInt());
        verify(studentDAO).update(student);
    }

    @Test
    void shouldInvokeGetByIdAndAddStudentToCourseMethods_whenCalledAssignStudentToCourse()
            throws ServiceException, DAOException {
        when(courseDAO.getById(anyInt())).thenReturn(course);
        when(student.isActive()).thenReturn(true);

        studentServiceImpl.assignStudentToCourse(student, anyInt());
        verify(courseDAO).getById(anyInt());
        verify(studentDAO).addStudentToCourse(anyInt(), anyInt());
    }

    @Test
    void shouldInvokeGetByDateTimeIntervalAndStudentIdMethod_whenCalledGetDaySchedule()
            throws ServiceException, DAOException {
        studentServiceImpl.getDaySchedule(student, LocalDate.now());
        verify(lessonDAO).getByDateTimeIntervalAndStudentId(anyInt(), any(LocalDateTime.class),
                any(LocalDateTime.class));
    }
}
