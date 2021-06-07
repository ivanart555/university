package com.ivanart555.university.services.impl;

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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.ServiceException;

@SpringJUnitConfig(TestSpringConfig.class)
@ExtendWith(MockitoExtension.class)
class LecturerServiceImplTest {
    @Mock
    private LecturerDAO lecturerDAO;
    @Mock
    private CourseDAO courseDAO;
    @Mock
    private GroupDAO groupDAO;
    @Mock
    private LessonDAO lessonDAO;
    @Mock
    private Lecturer lecturer;
    @Mock
    private Group group;
    @Mock
    private Course course;
    
    @InjectMocks
    private LecturerServiceImpl lecturerServiceImpl;
  
    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAll() throws ServiceException {
        List<Lecturer> lecturers = new ArrayList<>();
        lecturers.add(lecturer);
        when(lecturerDAO.getAll()).thenReturn(lecturers);

        lecturerServiceImpl.getAll();
        verify(lecturerDAO).getAll();
    }

    @Test
    void shouldInvokeGetAllActiveMethod_whenCalledGetAllActive() throws ServiceException {
        List<Lecturer> lecturers = new ArrayList<>();
        lecturers.add(lecturer);
        when(lecturerDAO.getAllActive()).thenReturn(lecturers);

        lecturerServiceImpl.getAllActive();
        verify(lecturerDAO).getAllActive();
    }

    @Test
    void shouldInvokeGetByIdMethod_whenCalledGetById() throws ServiceException {
        when(lecturerDAO.getById(anyInt())).thenReturn(lecturer);

        lecturerServiceImpl.getById(anyInt());
        verify(lecturerDAO).getById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        lecturerServiceImpl.delete(anyInt());
        verify(lecturerDAO).delete(anyInt());
    }

    @Test
    void shouldInvokeUpdateMethod_whenCalledUpdate() throws ServiceException, DAOException {
        lecturerServiceImpl.update(lecturer);
        verify(lecturerDAO).update(lecturer);
    }

    @Test
    void shouldInvokeCreateMethod_whenCalledCreate() throws ServiceException, DAOException {
        lecturerServiceImpl.create(lecturer);
        verify(lecturerDAO).create(lecturer);
    }
    
    @Test
    void shouldInvokeGetByIdAndAddLecturerToCourseMethods_whenCalledAddLecturerToCourse() throws ServiceException, DAOException {
        when(courseDAO.getById(anyInt())).thenReturn(course);
        when(lecturer.isActive()).thenReturn(true);

        lecturerServiceImpl.addLecturerToCourse(lecturer, anyInt());
        verify(courseDAO).getById(anyInt());
        verify(lecturerDAO).addLecturerToCourse(anyInt(), anyInt());
    }
    
    @Test
    void shouldInvokeGetByIdAndAddLecturerToGroupMethods_whenCalledAddLecturerToGroup() throws ServiceException, DAOException {
        when(groupDAO.getById(anyInt())).thenReturn(group);
        when(lecturer.isActive()).thenReturn(true);

        lecturerServiceImpl.addLecturerToGroup(lecturer, anyInt());
        verify(groupDAO).getById(anyInt());
        verify(lecturerDAO).addLecturerToGroup(anyInt(), anyInt());
    }
    
    @Test
    void shouldInvokeGetByDateTimeIntervalAndLecturerIdMethod_whenCalledGetDaySchedule()
            throws ServiceException, DAOException {
        lecturerServiceImpl.getDaySchedule(lecturer, LocalDate.now());
        verify(lessonDAO).getByDateTimeIntervalAndLecturerId(anyInt(), any(LocalDateTime.class),
                any(LocalDateTime.class));
    }    
}
