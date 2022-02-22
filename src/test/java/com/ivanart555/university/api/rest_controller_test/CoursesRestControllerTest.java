package com.ivanart555.university.api.rest_controller_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanart555.university.api.rest_controller.CoursesRestController;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.services.CourseService;
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
class CoursesRestControllerTest {
    private MockMvc mockMvc;
    private String jsonCourse;
    private String jsonCoursesList;
    private Course course;

    @Autowired
    private TestData testData;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CoursesRestController coursesRestController;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(coursesRestController).build();
        course = testData.getTestCourses().get(0);
        jsonCourse = new ObjectMapper().writeValueAsString(testData.getTestCourses().get(0));
        jsonCoursesList = new ObjectMapper().writeValueAsString(testData.getTestCourses());
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindAll() throws Exception {
        when(courseService.findAll()).thenReturn(testData.getTestCourses());

        mockMvc.perform(get("/api/v1/courses"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonCoursesList));

        verify(courseService, only()).findAll();
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindById() throws Exception {
        when(courseService.findById(anyInt())).thenReturn(course);

        mockMvc.perform(get("/api/v1/courses/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonCourse));

        verify(courseService, only()).findById(anyInt());
    }

    @Test
    void shouldParseJSONToObjectAndCallSave() throws Exception {
        when(courseService.save(course)).thenReturn(course.getId());

        mockMvc.perform(post("/api/v1/courses")
                .content(jsonCourse)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/v1/courses/" + course.getId())));

        Course expectedCourse = course;
        verify(courseService, only()).save(expectedCourse);
    }

    @Test
    void shouldParseJSONToObjectAndCallUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/courses", 1)
                .content(jsonCourse)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(courseService, only()).save(course);
    }

    @Test
    void shouldCallDelete_whenCalledDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/courses/{id}", 1))
                .andExpect(status().isNoContent());

        verify(courseService, only()).deleteById(anyInt());
    }
}
