package com.ivanart555.university.services.impl_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.ivanart555.university.services.impl.StudentServiceImpl;

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
    void shouldInvokeGetByIdAndSetGroupToStudent_whenCalledAddStudentToGroup() throws ServiceException, DAOException {
        Student student = new Student();
        student.setActive(true);

        when(groupDAO.getById(anyInt())).thenReturn(group);

        studentServiceImpl.addStudentToGroup(student, group);
        verify(groupDAO).getById(anyInt());
        assertEquals(group, student.getGroup());
    }

    @Test
    void shouldInvokeGetByDateTimeIntervalAndGroupIdMethod_whenCalledGetDaySchedule()
            throws ServiceException, DAOException {
        Student student = new Student("Alex", "Black");
        student.setGroup(new Group(1, "AF-01"));
        studentServiceImpl.getDaySchedule(student, LocalDate.now());
        verify(lessonDAO).getByDateTimeIntervalAndGroupId(anyInt(), any(LocalDateTime.class),
                any(LocalDateTime.class));
    }
}
