package com.ivanart555.university.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ivanart555.university.entities.Classroom;

public class ClassroomMapper implements RowMapper<Classroom> {

    @Override
    public Classroom mapRow(ResultSet rs, int rowNum) throws SQLException {

        Classroom classRoom = new Classroom();
        classRoom.setRoomId(rs.getInt("room_id"));
        classRoom.setRoomName(rs.getString("room_name"));

        return classRoom;
    }
}
