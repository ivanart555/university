package com.ivanart555.university.api.rest_controller_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ivanart555.university.api.rest_controller.LessonsRestController;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.services.LessonService;
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
class LessonsRestControllerTest {
    private MockMvc mockMvc;
    private String jsonLesson;
    private String jsonLessonsList;
    private Lesson lesson;

    @Autowired
    private TestData testData;

    @Mock
    private LessonService lessonService;

    @InjectMocks
    private LessonsRestController lessonsRestController;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(lessonsRestController).build();
        lesson = testData.getTestLessons().get(0);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        jsonLesson = mapper.writeValueAsString(testData.getTestLessons().get(0));
        jsonLessonsList = mapper.writeValueAsString(testData.getTestLessons());
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindAll() throws Exception {
        when(lessonService.findAll()).thenReturn(testData.getTestLessons());

        mockMvc.perform(get("/api/v1/lessons"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLessonsList));

        verify(lessonService, only()).findAll();
    }

    @Test
    void shouldReturnCorrectJson_whenCalledFindById() throws Exception {
        when(lessonService.findById(anyInt())).thenReturn(lesson);

        mockMvc.perform(get("/api/v1/lessons/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLesson));


        verify(lessonService, only()).findById(anyInt());
    }

    @Test
    void shouldParseJSONToObjectAndCallSave() throws Exception {
        when(lessonService.save(lesson)).thenReturn(lesson.getId());

        mockMvc.perform(post("/api/v1/lessons")
                .content(jsonLesson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/v1/lessons/" + lesson.getId())));

        Lesson expectedLesson = lesson;
        verify(lessonService, only()).save(expectedLesson);
    }

    @Test
    void shouldParseJSONToObjectAndCallUpdate() throws Exception {
        mockMvc.perform(put("/api/v1/lessons/{id}", 1)
                .content(jsonLesson)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        verify(lessonService, only()).save(lesson);
    }

    @Test
    void shouldCallDelete_whenCalledDelete() throws Exception {
        mockMvc.perform(delete("/api/v1/lessons/{id}", 1))
                .andExpect(status().isNoContent());

        verify(lessonService, only()).delete(anyInt());
    }
}
