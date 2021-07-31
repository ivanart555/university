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

import com.ivanart555.university.dao.GroupDAO;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.exception.DAOException;

@ExtendWith(MockitoExtension.class)
class GroupServiceImplTest {
    @Mock
    private GroupDAO groupDAO;
    @Mock
    private Group group;

    @InjectMocks
    private GroupServiceImpl groupServiceImpl;

    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAll() throws ServiceException {
        List<Group> groups = new ArrayList<>();
        groups.add(group);
        when(groupDAO.getAll()).thenReturn(groups);

        groupServiceImpl.getAll();
        verify(groupDAO).getAll();
    }

    @Test
    void shouldInvokeGetByIdMethod_whenCalledGetById() throws ServiceException, DAOException {
        when(groupDAO.getById(anyInt())).thenReturn(group);

        groupServiceImpl.getById(anyInt());
        verify(groupDAO).getById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        groupServiceImpl.delete(anyInt());
        verify(groupDAO).delete(anyInt());
    }

    @Test
    void shouldInvokeUpdateMethod_whenCalledUpdate() throws ServiceException, DAOException {
        groupServiceImpl.update(group);
        verify(groupDAO).update(group);
    }

    @Test
    void shouldInvokeCreateMethod_whenCalledCreate() throws ServiceException, DAOException {
        groupServiceImpl.create(group);
        verify(groupDAO).create(group);
    }
}
