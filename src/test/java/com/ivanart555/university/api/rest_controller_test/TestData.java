package com.ivanart555.university.api.rest_controller_test;

import com.ivanart555.university.entities.Group;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestData {

    public List<Group> getTestGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1, "AB-01"));
        groups.add(new Group(2, "BA-12"));
        groups.add(new Group(3, "AC-06"));
        return groups;
    }

}
