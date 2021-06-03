package com.ivanart555.university.services.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.dao.impl.ClassroomDAOImpl;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.ServiceException;

class ClassroomServiceImplTest {
    private ClassroomDAO classroomDAO = mock(ClassroomDAOImpl.class);
    private ClassroomServiceImpl classroomServiceImpl = new ClassroomServiceImpl(classroomDAO);
    private Classroom classroom = mock(Classroom.class);

    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAll() throws ServiceException {
        List<Classroom> classrooms = new ArrayList<>();
        classrooms.add(classroom);
        when(classroomDAO.getAll()).thenReturn(classrooms);

        classroomServiceImpl.getAll();
        verify(classroomDAO).getAll();
    }

    @Test
    void shouldInvokeGetByIdMethod_whenCalledGetById() throws ServiceException {
        when(classroomDAO.getById(1)).thenReturn(classroom);

        classroomServiceImpl.getById(1);
        verify(classroomDAO).getById(1);
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        classroomServiceImpl.delete(1);
        verify(classroomDAO).delete(1);
    }

    @Test
    void shouldInvokeUpdateMethod_whenCalledUpdate() throws ServiceException, DAOException {
        classroomServiceImpl.update(classroom);
        verify(classroomDAO).update(classroom);
    }

    @Test
    void shouldInvokeCreateMethod_whenCalledCreate() throws ServiceException, DAOException {
        classroomServiceImpl.create(classroom);
        verify(classroomDAO).create(classroom);
    }
}
