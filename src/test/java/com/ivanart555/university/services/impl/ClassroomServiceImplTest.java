package com.ivanart555.university.services.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.ivanart555.university.dao.ClassroomDAO;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.DAOException;
import com.ivanart555.university.exception.ServiceException;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceImplTest {
    @Mock
    private ClassroomDAO classroomDAO;

    @Mock
    private Classroom classroom;

    @InjectMocks
    private ClassroomServiceImpl classroomServiceImpl;

    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAll() throws ServiceException {
        List<Classroom> classrooms = new ArrayList<>();
        classrooms.add(classroom);
        when(classroomDAO.getAll()).thenReturn(classrooms);

        classroomServiceImpl.getAll();
        verify(classroomDAO).getAll();
    }

    @Test
    void shouldInvokeGetByIdMethod_whenCalledGetById() throws ServiceException, DAOException {
        when(classroomDAO.getById(anyInt())).thenReturn(classroom);

        classroomServiceImpl.getById(anyInt());
        verify(classroomDAO).getById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        classroomServiceImpl.delete(anyInt());
        verify(classroomDAO).delete(anyInt());
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
