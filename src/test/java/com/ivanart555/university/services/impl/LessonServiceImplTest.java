package com.ivanart555.university.services.impl;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.dto.LessonDto;
import com.ivanart555.university.entities.*;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(TestSpringConfig.class)
@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {
    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private ClassroomRepository classroomRepository;
    @Mock
    private LecturerRepository lecturerRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private Lesson lesson;
    @Mock
    private Course course;
    @Mock
    private Lecturer lecturer;
    @Mock
    private Classroom classroom;
    @Mock
    private Group group;
    @Mock
    private LessonDto lessonDto;

    @InjectMocks
    private LessonServiceImpl lessonServiceImpl;

    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAll() throws ServiceException {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson);
        when(lessonRepository.findAll()).thenReturn(lessons);

        lessonServiceImpl.findAll();
        verify(lessonRepository).findAll();
    }

    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAllDto() throws ServiceException {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson);
        when(lessonRepository.findAll()).thenReturn(lessons);

        lessonServiceImpl.getAllDto();
        verify(lessonRepository).findAll();
    }

    @Test
    void shouldInvokeFindByIdMethod_whenCalledFindById() throws ServiceException {
        when(lessonRepository.findById(1)).thenReturn(Optional.of(new Lesson()));

        lessonServiceImpl.findById(1);
        verify(lessonRepository, only()).findById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        lessonServiceImpl.deleteById(anyInt());
        verify(lessonRepository).deleteById(anyInt());
    }

    @Test
    void shouldInvokeSaveMethod_whenCalledSave() throws ServiceException {
        when(lessonRepository.save(lesson)).thenReturn(lesson);
        lessonServiceImpl.save(lesson);
        verify(lessonRepository).save(lesson);
    }

    @Test
    void shouldInvokeSaveMethod_whenCalledSaveWithLessonDto() throws ServiceException {
        Lesson lesson = new Lesson();
        when(lessonRepository.save(lesson)).thenReturn(lesson);
        lessonServiceImpl.save(lessonDto);
        verify(lessonRepository).save(lesson);
    }

    @Test
    void shouldInvokeSaveMethod_whenCalledAssignLessonToDateTimeForGroup() throws ServiceException {
        Lesson lesson = new Lesson(1, null, null, null, null, null, null);
        lessonServiceImpl.assignLessonToDateTimeForGroup(lesson, any(LocalDateTime.class), any(LocalDateTime.class),
                anyInt());

        verify(lessonRepository).save(any(Lesson.class));
    }
}
