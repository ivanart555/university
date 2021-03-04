package com.ivanart555.university.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class GroupMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {

        Group group = new Group();
        group.setGroupId(rs.getInt("group_id"));
        group.setName(rs.getString("group_name"));

        return group;
    }
}
