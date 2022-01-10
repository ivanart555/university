package com.ivanart555.university.services.impl_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.CourseRepository;
import com.ivanart555.university.repository.GroupRepository;
import com.ivanart555.university.repository.LecturerRepository;
import com.ivanart555.university.repository.LessonRepository;
import com.ivanart555.university.services.impl.LecturerServiceImpl;

@SpringJUnitConfig(TestSpringConfig.class)
@ExtendWith(MockitoExtension.class)
class LecturerServiceImplTest {
    @Mock
    private LecturerRepository lecturerRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private LessonRepository lessonRepository;
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
        when(lecturerRepository.findAll()).thenReturn(lecturers);

        lecturerServiceImpl.findAll();
        verify(lecturerRepository).findAll();
    }

    @Test
    void shouldInvokeGetAllActiveMethod_whenCalledGetAllActive() throws ServiceException {
        List<Lecturer> lecturers = new ArrayList<>();
        lecturers.add(lecturer);
        when(lecturerRepository.getAllActive()).thenReturn(lecturers);

        lecturerServiceImpl.getAllActive();
        verify(lecturerRepository).getAllActive();
    }

    @Test
    void shouldInvokeFindByIdMethod_whenCalledFindById() throws ServiceException {
        when(lecturerRepository.findById(1)).thenReturn(Optional.of(new Lecturer()));

        lecturerServiceImpl.findById(1);
        verify(lecturerRepository, only()).findById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        lecturerServiceImpl.delete(anyInt());
        verify(lecturerRepository).deleteById(anyInt());
    }

    @Test
    void shouldInvokeSaveMethod_whenCalledSave() throws ServiceException {
        lecturerServiceImpl.save(lecturer);
        verify(lecturerRepository).save(lecturer);
    }

    @Test
    void shouldInvokeGetByIdAndSetCourseToLecturer_whenCalledAddLecturerToCourse()
            throws ServiceException {
        Lecturer lecturer = new Lecturer();
        lecturer.setActive(true);

        when(courseRepository.getById(anyInt())).thenReturn(course);

        lecturerServiceImpl.addLecturerToCourse(lecturer, course);
        verify(courseRepository).getById(anyInt());
        assertEquals(course, lecturer.getCourse());
    }

    @Test
    void shouldInvokeGetByDateTimeIntervalAndLecturerIdMethod_whenCalledGetDaySchedule()
            throws ServiceException {
        lecturerServiceImpl.getDaySchedule(lecturer, LocalDate.now());
        verify(lessonRepository).findAllByLecturerIdAndLessonStartLessThanEqualAndLessonEndGreaterThanEqual(anyInt(), any(LocalDateTime.class),
                any(LocalDateTime.class));
    }
}
