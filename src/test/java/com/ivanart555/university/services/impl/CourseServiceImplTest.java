package com.ivanart555.university.services.impl;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.ServiceException;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
    @Mock
    private CourseDAO courseDAO;
    @Mock
    private Course course;

    @InjectMocks
    private CourseServiceImpl courseServiceImpl;

    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAll() throws ServiceException {
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        when(courseDAO.getAll()).thenReturn(courses);

        courseServiceImpl.getAll();
        verify(courseDAO).getAll();
    }

    @Test
    void shouldInvokeGetByIdMethod_whenCalledGetById() throws ServiceException, DAOException {
        when(courseDAO.getById(anyInt())).thenReturn(course);

        courseServiceImpl.getById(anyInt());
        verify(courseDAO).getById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        courseServiceImpl.delete(anyInt());
        verify(courseDAO).delete(anyInt());
    }

    @Test
    void shouldInvokeUpdateMethod_whenCalledUpdate() throws ServiceException, DAOException {
        courseServiceImpl.update(course);
        verify(courseDAO).update(course);
    }

    @Test
    void shouldInvokeCreateMethod_whenCalledCreate() throws ServiceException, DAOException {
        courseServiceImpl.create(course);
        verify(courseDAO).create(course);
    }
}
