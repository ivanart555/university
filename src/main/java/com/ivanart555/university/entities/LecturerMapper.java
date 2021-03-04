package com.ivanart555.university.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LecturerMapper implements RowMapper<Lecturer> {

    @Override
    public Lecturer mapRow(ResultSet rs, int rowNum) throws SQLException {

        Lecturer lecturer = new Lecturer();
        lecturer.setLecturerId(rs.getInt("lecturer_id"));
        lecturer.setFirstName(rs.getString("lecturer_name"));
        lecturer.setLastName(rs.getString("lecturer_lastname"));

        return lecturer;
    }
}
