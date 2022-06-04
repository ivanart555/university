package com.ivanart555.university.api.rest_controller.integration_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.repository.GroupRepository;
import com.ivanart555.university.test_data.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class GroupsControllerIntegrationTest {
    private String jsonGroupsList;
    private String jsonGroup;
    private Group group;

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TestData testData;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
        group = testData.getTestGroups().get(0);
        jsonGroup = new ObjectMapper().writeValueAsString(testData.getTestGroups().get(0));
        jsonGroupsList = new ObjectMapper().writeValueAsString(testData.getTestGroups());
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindAll() throws Exception {
        List<Group> expectedGroups = testData.getTestGroups();
        groupRepository.saveAll(expectedGroups);

        mockMvc.perform(get("/api/v1/groups"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonGroupsList));
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindById() throws Exception {
        groupRepository.save(group);

        mockMvc.perform(get("/api/v1/groups/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonGroup));
    }

    @Test
    void shouldCreateGroup_whenCalledPost() throws Exception {
        mockMvc.perform(post("/api/v1/groups/")
                .content(jsonGroup)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/v1/groups/" + group.getId())));

        assertEquals(groupRepository.findById(1), Optional.of(group));
    }

    @Test
    void shouldUpdateGroup_whenCalledPut() throws Exception {
        Group initialGroup = testData.getTestGroups().get(1);
        groupRepository.save(initialGroup);

        mockMvc.perform(put("/api/v1/groups/", 1)
                .content(jsonGroup)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(groupRepository.findById(1), Optional.of(group));
    }

    @Test
    void should_whenCalledPut() throws Exception {
        Group initialGroup = testData.getTestGroups().get(1);
        groupRepository.save(initialGroup);

        mockMvc.perform(put("/api/v1/groups/", 1)
                .content(jsonGroup)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(groupRepository.findById(1), Optional.of(group));
    }


}
