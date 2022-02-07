package com.ivanart555.university.api.rest_controller_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanart555.university.api.rest_controller.GroupsRestController;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.services.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonGroupsList));

        verify(groupService, only()).findAll();
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindById() throws Exception {
        when(groupService.findById(anyInt())).thenReturn(group);

        mockMvc.perform(get("/api/groups/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonGroup));

        verify(groupService, only()).findById(anyInt());
    }

    @Test
    void shouldParseJSONToObjectAndCallSave() throws Exception {
        mockMvc.perform(post("/api/groups")
                .content(jsonGroup)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated());

        Group expectedGroup = group;
        expectedGroup.setId(0);
        verify(groupService, only()).save(expectedGroup);
    }

    @Test
    void shouldParseJSONToObjectAndCallUpdate() throws Exception {
        mockMvc.perform(patch("/api/groups/{id}", 1)
                .content(jsonGroup)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(groupService, only()).save(group);
    }

    @Test
    void shouldCallDelete_whenCalledDelete() throws Exception {
        mockMvc.perform(delete("/api/groups/{id}", 1))
                .andExpect(status().isOk());

        verify(groupService, only()).delete(anyInt());
    }
}
