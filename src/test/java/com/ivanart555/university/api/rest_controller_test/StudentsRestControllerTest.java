package com.ivanart555.university.api.rest_controller_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanart555.university.api.rest_controller.StudentsRestController;
import com.ivanart555.university.entities.Student;
import com.ivanart555.university.services.StudentService;
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
class StudentsRestControllerTest {
    private MockMvc mockMvc;
    private String jsonStudent;
    private String jsonStudentsList;
    private Student student;

    @Autowired
    private TestData testData;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentsRestController studentsRestController;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(studentsRestController).build();
        student = testData.getTestStudents().get(0);
        jsonStudent = new ObjectMapper().writeValueAsString(testData.getTestStudents().get(0));
        jsonStudentsList = new ObjectMapper().writeValueAsString(testData.getTestStudents());
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindAll() throws Exception {
        when(studentService.findAll()).thenReturn(testData.getTestStudents());

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStudentsList));

        verify(studentService, only()).findAll();
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindById() throws Exception {
        when(studentService.findById(anyInt())).thenReturn(student);

        mockMvc.perform(get("/api/v1/students/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStudent));

        verify(studentService, only()).findById(anyInt());
    }

    @Test
    void shouldParseJSONToObjectAndCallSave() throws Exception {
        when(studentService.save(student)).thenReturn(student.getId());

        mockMvc.perform(post("/api/v1/students")
                .content(jsonStudent)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/v1/students/" + student.getId())));

        Student expectedStudent = student;
        verify(studentService, only()).save(expectedStudent);
    }

    @Test
    void shouldParseJSONToObjectAndCallUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/students", 1)
                .content(jsonStudent)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(studentService, only()).save(student);
    }

    @Test
    void shouldCallDelete_whenCalledDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/students/{id}", 1))
                .andExpect(status().isNoContent());

        verify(studentService, only()).deleteById(anyInt());
    }
}
