package com.ivanart555.university.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ivanart555.university.entities.Classroom;

@Component
public class ClassroomMapper implements RowMapper<Classroom> {

    @Override
    public Classroom mapRow(ResultSet rs, int rowNum) throws SQLException {

        Classroom classRoom = new Classroom();
        classRoom.setId(rs.getInt("room_id"));
        classRoom.setName(rs.getString("room_name"));

        return classRoom;
    }
}