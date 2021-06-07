package com.ivanart555.university.services.impl;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.dao.CourseDAO;
import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.dao.LecturerDAO;
import com.ivanart555.university.dao.LessonDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.ServiceException;

@SpringJUnitConfig(TestSpringConfig.class)
@ExtendWith(MockitoExtension.class)
class LessonServiceImplTest {
    @Mock
    private LessonDAO lessonDAO;
    @Mock
    private CourseDAO courseDAO;
    @Mock
    private ClassroomDAO classroomDAO;
    @Mock
    private LecturerDAO lecturerDAO;
    @Mock
    private GroupDAO groupDAO;
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

    @InjectMocks
    private LessonServiceImpl lessonServiceImpl;

    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAll() throws ServiceException {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson);
        when(lessonDAO.getAll()).thenReturn(lessons);

        lessonServiceImpl.getAll();
        verify(lessonDAO).getAll();
    }

    @Test
    void shouldInvokeGetByIdMethod_whenCalledGetById() throws ServiceException {
        when(lessonDAO.getById(anyInt())).thenReturn(lesson);

        lessonServiceImpl.getById(anyInt());
        verify(lessonDAO).getById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        lessonServiceImpl.delete(anyInt());
        verify(lessonDAO).delete(anyInt());
    }

    @Test
    void shouldInvokeUpdateMethod_whenCalledUpdate() throws ServiceException, DAOException {
        lessonServiceImpl.update(lesson);
        verify(lessonDAO).update(lesson);
    }

    @Test
    void shouldInvokeCreateMethod_whenCalledCreate() throws ServiceException, DAOException {
        lessonServiceImpl.create(lesson);
        verify(lessonDAO).create(lesson);
    }

    @Test
    void shouldInvokeGetByIdAndUpdateMethods_whenCalledAssignLessonToGroup() throws ServiceException, DAOException {
        when(courseDAO.getById(anyInt())).thenReturn(course);

        lessonServiceImpl.assignLessonToCourse(lesson, anyInt());
        verify(courseDAO).getById(anyInt());
        verify(lessonDAO).update(lesson);
    }

    @Test
    void shouldInvokeGetByIdAndUpdateMethods_whenCalledAssignLessonToClassroom() throws ServiceException, DAOException {
        when(classroomDAO.getById(anyInt())).thenReturn(classroom);

        lessonServiceImpl.assignLessonToClassroom(lesson, anyInt());
        verify(classroomDAO).getById(anyInt());
        verify(lessonDAO).update(lesson);
    }

    @Test
    void shouldInvokeGetByIdAndUpdateMethods_whenCalledAssignLessonToLecturer() throws ServiceException, DAOException {
        when(lecturerDAO.getById(anyInt())).thenReturn(lecturer);

        lessonServiceImpl.assignLessonToLecturer(lesson, anyInt());
        verify(lecturerDAO).getById(anyInt());
        verify(lessonDAO).update(lesson);
    }

    @Test
    void shouldInvokeGetByIdAndAssignLessonToGroupMethods_whenCalledAssignLessonToGroup()
            throws ServiceException, DAOException {
        when(groupDAO.getById(anyInt())).thenReturn(group);

        lessonServiceImpl.assignLessonToGroup(lesson, anyInt());
        verify(groupDAO).getById(anyInt());
        verify(lessonDAO).assignLessonToGroup(anyInt(), anyInt());
    }

    @Test
    void shouldInvokeUpdateMethod_whenCalledAssignLessonToDateTimeForGroup() throws ServiceException, DAOException {
        lessonServiceImpl.assignLessonToDateTimeForGroup(lesson, any(LocalDateTime.class), any(LocalDateTime.class),
                anyInt());

        verify(lessonDAO).update(lesson);
    }
}
