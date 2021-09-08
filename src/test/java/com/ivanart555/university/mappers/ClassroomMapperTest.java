package com.ivanart555.university.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Classroom;

@SpringJUnitConfig(TestSpringConfig.class)
class ClassroomMapperTest {
    private ClassroomMapper classroomMapper;

    @Autowired
    private ClassroomMapperTest(ClassroomMapper classroomMapper){
        this.classroomMapper = classroomMapper;
    }

    @Test
    void shouldReturnClassroom_WhenCalledMapRow() throws SQLException {
        Classroom expectedClassroom = new Classroom(1, "100");

        ResultSet rsMock = mock(ResultSet.class);
        when(rsMock.getInt("room_id")).thenReturn(1);
        when(rsMock.getString("room_name")).thenReturn("100");
        when(rsMock.next()).thenReturn(true).thenReturn(false);

        assertEquals(expectedClassroom, classroomMapper.mapRow(rsMock, 1));
    }
}
