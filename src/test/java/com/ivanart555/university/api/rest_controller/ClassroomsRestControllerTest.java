package com.ivanart555.university.api.rest_controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanart555.university.entities.Classroom;
import com.ivanart555.university.services.ClassroomService;
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
class ClassroomsRestControllerTest {
    private MockMvc mockMvc;
    private String jsonClassroom;
    private String jsonClassroomsList;
    private Classroom classroom;

    @Autowired
    private TestData testData;

    @Mock
    private ClassroomService classroomService;

    @InjectMocks
    private ClassroomsRestController classroomsRestController;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(classroomsRestController).build();
        classroom = testData.getTestClassrooms().get(0);
        jsonClassroom = new ObjectMapper().writeValueAsString(testData.getTestClassrooms().get(0));
        jsonClassroomsList = new ObjectMapper().writeValueAsString(testData.getTestClassrooms());
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindAll() throws Exception {
        when(classroomService.findAll()).thenReturn(testData.getTestClassrooms());

        mockMvc.perform(get("/api/v1/classrooms"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonClassroomsList));

        verify(classroomService, only()).findAll();
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindById() throws Exception {
        when(classroomService.findById(anyInt())).thenReturn(classroom);

        mockMvc.perform(get("/api/v1/classrooms/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonClassroom));

        verify(classroomService, only()).findById(anyInt());
    }

    @Test
    void shouldParseJSONToObjectAndCallSave() throws Exception {
        when(classroomService.save(classroom)).thenReturn(classroom.getId());

        mockMvc.perform(post("/api/v1/classrooms")
                .content(jsonClassroom)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/v1/classrooms/" + classroom.getId())));

        Classroom expectedClassroom = classroom;
        verify(classroomService, only()).save(expectedClassroom);
    }

    @Test
    void shouldParseJSONToObjectAndCallUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/classrooms", 1)
                .content(jsonClassroom)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(classroomService, only()).save(classroom);
    }

    @Test
    void shouldCallDelete_whenCalledDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/classrooms/{id}", 1))
                .andExpect(status().isNoContent());

        verify(classroomService, only()).deleteById(anyInt());
    }
}
