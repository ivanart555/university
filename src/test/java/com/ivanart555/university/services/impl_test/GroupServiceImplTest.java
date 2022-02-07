package com.ivanart555.university.services.impl_test;

import com.ivanart555.university.entities.Group;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.GroupRepository;
import com.ivanart555.university.services.impl.GroupServiceImpl;
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
class GroupServiceImplTest {
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private Group group;

    @InjectMocks
    private GroupServiceImpl groupServiceImpl;

    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAll() throws ServiceException {
        List<Group> groups = new ArrayList<>();
        groups.add(group);
        when(groupRepository.findAll()).thenReturn(groups);

        groupServiceImpl.findAll();
        verify(groupRepository).findAll();
    }

    @Test
    void shouldInvokeFindByIdMethod_whenCalledFindById() throws ServiceException {
        when(groupRepository.findById(1)).thenReturn(Optional.of(new Group()));

        groupServiceImpl.findById(1);
        verify(groupRepository, only()).findById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        groupServiceImpl.delete(anyInt());
        verify(groupRepository).deleteById(anyInt());
    }

    @Test
    void shouldInvokeSaveMethod_whenCalledSave() throws ServiceException {
        groupServiceImpl.save(group);
        verify(groupRepository).save(group);
    }
}
