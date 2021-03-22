package com.ivanart555.university.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Group;

@SpringJUnitConfig(TestSpringConfig.class)
class GroupMapperTest {

    @Autowired
    private GroupMapper groupMapper;

    @Test
    void shouldReturnGroup_WhenCalledMapRow() throws SQLException {
        Group expectedGroup = new Group(1, "AB-01");

        ResultSet rsMock = Mockito.mock(ResultSet.class);
        Mockito.when(rsMock.getInt("group_id")).thenReturn(1);
        Mockito.when(rsMock.getString("group_name")).thenReturn("AB-01");
        Mockito.when(rsMock.next()).thenReturn(true).thenReturn(false);

        assertEquals(expectedGroup, groupMapper.mapRow(rsMock, 1));
    }
}
