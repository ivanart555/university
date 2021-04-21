package com.ivanart555.university.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ivanart555.university.entities.Student;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

        Student student = new Student();
        student.setId(rs.getInt("student_id"));
        student.setFirstName(rs.getString("student_name"));
        student.setLastName(rs.getString("student_lastname"));
        student.setGroupId(rs.getInt("group_id"));
        student.setActive(rs.getBoolean("active"));

        return student;
    }
}
