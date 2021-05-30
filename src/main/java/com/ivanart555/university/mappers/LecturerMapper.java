package com.ivanart555.university.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.ivanart555.university.entities.Lecturer;

@Component
public class LecturerMapper implements RowMapper<Lecturer> {

    @Override
    public Lecturer mapRow(ResultSet rs, int rowNum) throws SQLException {

        Lecturer lecturer = new Lecturer();
        lecturer.setId(rs.getInt("lecturer_id"));
        lecturer.setFirstName(rs.getString("lecturer_name"));
        lecturer.setLastName(rs.getString("lecturer_lastname"));
        lecturer.setActive(rs.getBoolean("active"));

        return lecturer;
    }
}
