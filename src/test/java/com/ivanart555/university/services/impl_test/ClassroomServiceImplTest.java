package com.ivanart555.university.services.impl_test;

import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.exception.ServiceException;
import com.ivanart555.university.repository.ClassroomRepository;
import com.ivanart555.university.services.impl.ClassroomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceImplTest {
    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private Classroom classroom;

    @Mock
    private Pageable pageable;

    @InjectMocks
    private ClassroomServiceImpl classroomServiceImpl;

    @Test
    void shouldInvokeGetAllMethod_whenCalledGetAll() throws ServiceException {
        List<Classroom> classrooms = new ArrayList<>();
        classrooms.add(classroom);
        when(classroomRepository.findAll()).thenReturn(classrooms);

        classroomServiceImpl.findAll();
        verify(classroomRepository).findAll();
    }

    @Test
    void shouldInvokeFindByIdMethod_whenCalledFindById() throws ServiceException {
        when(classroomRepository.findById(1)).thenReturn(Optional.of(new Classroom()));

        classroomServiceImpl.findById(1);
        verify(classroomRepository, only()).findById(anyInt());
    }

    @Test
    void shouldInvokeDeleteMethod_whenCalledDelete() throws ServiceException {
        classroomServiceImpl.delete(anyInt());
        verify(classroomRepository).deleteById(anyInt());
    }

    @Test
    void shouldInvokeSaveMethod_whenCalledSave() throws ServiceException {
        when(classroomRepository.save(classroom)).thenReturn(classroom);
        classroomServiceImpl.save(classroom);
        verify(classroomRepository).save(classroom);
    }
}
