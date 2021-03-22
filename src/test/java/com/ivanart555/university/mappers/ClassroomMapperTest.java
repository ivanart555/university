package com.ivanart555.university.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Classroom;

@SpringJUnitConfig(TestSpringConfig.class)
class ClassroomMapperTest {

    @Autowired
    private ClassroomMapper classroomMapper;

    @Test
    void shouldReturnClassroom_WhenCalledMapRow() throws SQLException {
        Classroom expectedClassroom = new Classroom(1, "100");
        
        ResultSet rsMock = Mockito.mock(ResultSet.class);
        Mockito.when(rsMock.getInt("room_id")).thenReturn(1);
        Mockito.when(rsMock.getString("room_name")).thenReturn("100");
        Mockito.when(rsMock.next()).thenReturn(true).thenReturn(false);
        
        assertEquals(expectedClassroom, classroomMapper.mapRow(rsMock, 1));
    }
}
