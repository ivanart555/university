package com.ivanart555.university.api.rest_controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.services.GroupService;
import com.ivanart555.university.test_data.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class GroupsRestControllerTest {
    private MockMvc mockMvc;
    private String jsonGroup;
    private String jsonGroupsList;
    private Group group;

    @Autowired
    private TestData testData;

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupsRestController groupsRestController;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(groupsRestController).build();
        group = testData.getTestGroups().get(0);
        jsonGroup = new ObjectMapper().writeValueAsString(testData.getTestGroups().get(0));
        jsonGroupsList = new ObjectMapper().writeValueAsString(testData.getTestGroups());
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindAll() throws Exception {
        when(groupService.findAll()).thenReturn(testData.getTestGroups());

        mockMvc.perform(get("/api/v1/groups"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonGroupsList));

        verify(groupService, only()).findAll();
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindById() throws Exception {
        when(groupService.findById(anyInt())).thenReturn(group);

        mockMvc.perform(get("/api/v1/groups/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonGroup));

        verify(groupService, only()).findById(anyInt());
    }

    @Test
    void shouldParseJSONToObjectAndCallSave() throws Exception {
        when(groupService.save(any(Group.class))).thenReturn(group.getId());

        mockMvc.perform(post("/api/v1/groups")
                .content(jsonGroup)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/v1/groups/" + group.getId())));

        verify(groupService, only()).save(any(Group.class));
    }

    @Test
    void shouldParseJSONToObjectAndCallUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/groups", 1)
                .content(jsonGroup)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(groupService, only()).save(any(Group.class));
    }

    @Test
    void shouldCallDelete_whenCalledDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/groups/{id}", 1))
                .andExpect(status().isNoContent());

        verify(groupService, only()).deleteById(anyInt());
    }
}
