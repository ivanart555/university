package com.ivanart555.university.api.rest_controller_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanart555.university.api.rest_controller.GroupsRestController;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.services.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class GroupsRestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupsRestController groupsRestController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupsRestController).build();
    }

    @Test
    void findAllShouldReturnCorrectJson() throws Exception {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("AB-01"));
        groups.add(new Group("BA-12"));
        groups.add(new Group("AC-06"));

        String jsonGroup = new ObjectMapper().writeValueAsString(groups.get(0));
        String jsonListOfGroup = new ObjectMapper().writeValueAsString(groups);

        when(groupService.findAll()).thenReturn(groups);

        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonListOfGroup));

        verify(groupService, only()).findAll();
    }
}
