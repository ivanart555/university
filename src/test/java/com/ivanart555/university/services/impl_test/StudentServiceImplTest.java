package com.ivanart555.university.services.impl_test;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.CourseRepository;
import com.ivanart555.university.repository.GroupRepository;
import com.ivanart555.university.repository.LessonRepository;
import com.ivanart555.university.repository.StudentRepository;
import com.ivanart555.university.services.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(TestSpringConfig.class)
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private LessonRepository lessonRepository;
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
        when(studentRepository.findAll()).thenReturn(students);

        studentServiceImpl.findAll();
        verify(studentRepository).findAll();
    }

    @Test
    void shouldInvokeGetAllActiveMethod_whenCalledGetAllActive() throws ServiceException {
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentRepository.getAllActive()).thenReturn(students);

        studentServiceImpl.getAllActive();
        verify(studentRepository).getAllActive();
    }

    @Test
    void shouldInvokeFindByIdMethod_whenCalledFindById() throws ServiceException {
        when(studentRepository.findById(1)).thenReturn(Optional.of(new Student()));

        studentServiceImpl.findById(1);
        verify(studentRepository, only()).findById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        studentServiceImpl.delete(anyInt());
        verify(studentRepository).deleteById(anyInt());
    }

    @Test
    void shouldInvokeSaveMethod_whenCalledSave() throws ServiceException {
        studentServiceImpl.save(student);
        verify(studentRepository).save(student);
    }

    @Test
    void shouldInvokeGetByIdAndSetGroupToStudent_whenCalledAddStudentToGroup() throws ServiceException {
        Student student = new Student();
        student.setActive(true);

        when(groupRepository.getById(anyInt())).thenReturn(group);

        studentServiceImpl.addStudentToGroup(student, group);
        verify(groupRepository).getById(anyInt());
        assertEquals(group, student.getGroup());
    }

    @Test
    void shouldInvokeGetByDateTimeIntervalAndGroupIdMethod_whenCalledGetDaySchedule()
            throws ServiceException {
        Student student = new Student("Alex", "Black");
        student.setGroup(new Group(1, "AF-01"));
        studentServiceImpl.getDaySchedule(student, LocalDate.now());
        verify(lessonRepository).findAllByGroupIdAndLessonStartLessThanEqualAndLessonEndGreaterThanEqual(anyInt(), any(LocalDateTime.class),
                any(LocalDateTime.class));
    }
}
