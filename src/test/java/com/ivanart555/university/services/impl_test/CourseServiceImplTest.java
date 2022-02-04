package com.ivanart555.university.services.impl_test;

import com.ivanart555.university.entities.Course;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.CourseRepository;
import com.ivanart555.university.services.impl.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private Course course;

    @InjectMocks
    private CourseServiceImpl courseServiceImpl;

    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAll() throws ServiceException {
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        when(courseRepository.findAll()).thenReturn(courses);

        courseServiceImpl.findAll();
        verify(courseRepository).findAll();
    }

    @Test
    void shouldInvokeFindByIdMethod_whenCalledFindById() throws ServiceException {
        when(courseRepository.findById(1)).thenReturn(Optional.of(new Course()));

        courseServiceImpl.findById(1);
        verify(courseRepository, only()).findById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        courseServiceImpl.delete(anyInt());
        verify(courseRepository).deleteById(anyInt());
    }

    @Test
    void shouldInvokeSaveMethod_whenCalledSave() throws ServiceException {
        courseServiceImpl.save(course);
        verify(courseRepository).save(course);
    }
}
