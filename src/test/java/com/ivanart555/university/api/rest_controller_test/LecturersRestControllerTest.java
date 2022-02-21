package com.ivanart555.university.api.rest_controller_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanart555.university.api.rest_controller.LecturersRestController;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.services.LecturerService;
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
class LecturersRestControllerTest {
    private MockMvc mockMvc;
    private String jsonLecturer;
    private String jsonLecturersList;
    private Lecturer lecturer;

    @Autowired
    private TestData testData;

    @Mock
    private LecturerService lecturerService;

    @InjectMocks
    private LecturersRestController lecturersRestController;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(lecturersRestController).build();
        lecturer = testData.getTestLecturers().get(0);
        jsonLecturer = new ObjectMapper().writeValueAsString(testData.getTestLecturers().get(0));
        jsonLecturersList = new ObjectMapper().writeValueAsString(testData.getTestLecturers());
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindAll() throws Exception {
        when(lecturerService.findAll()).thenReturn(testData.getTestLecturers());

        mockMvc.perform(get("/api/v1/lecturers"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLecturersList));

        verify(lecturerService, only()).findAll();
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindById() throws Exception {
        when(lecturerService.findById(anyInt())).thenReturn(lecturer);

        mockMvc.perform(get("/api/v1/lecturers/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLecturer));

        verify(lecturerService, only()).findById(anyInt());
    }

    @Test
    void shouldParseJSONToObjectAndCallSave() throws Exception {
        when(lecturerService.save(lecturer)).thenReturn(lecturer.getId());

        mockMvc.perform(post("/api/v1/lecturers")
                .content(jsonLecturer)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/v1/lecturers/" + lecturer.getId())));


        Lecturer expectedLecturer = lecturer;
        verify(lecturerService, only()).save(expectedLecturer);
    }

    @Test
    void shouldParseJSONToObjectAndCallUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/lecturers/{id}", 1)
                .content(jsonLecturer)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(lecturerService, only()).save(lecturer);
    }

    @Test
    void shouldCallDelete_whenCalledDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/lecturers/{id}", 1))
                .andExpect(status().isNoContent());

        verify(lecturerService, only()).deleteById(anyInt());
    }
}
