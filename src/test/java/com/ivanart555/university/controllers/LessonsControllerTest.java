package com.ivanart555.university.controllers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ivanart555.university.config.TestContext;
import com.ivanart555.university.entities.Lesson;
import com.ivanart555.university.services.LessonService;

@SpringJUnitWebConfig(TestContext.class)
@ExtendWith(MockitoExtension.class)
class LessonsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LessonService lessonService;

    @Mock
    Page<Lesson> anyPage;

    @InjectMocks
    private LessonsController lessonsController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(lessonsController).build();
    }

    @Test
    void mockMvcTest() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnViewLessonsIndex_whenCalledLessonsGET() throws Exception {
        when(lessonService.findPaginated(any())).thenReturn(anyPage);
        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lessonPage"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(view().name("lessons/index"));
    }
    
    @Test
    void shouldRedirectToLessons_whenCalledLessonsPOST() throws Exception {
        mockMvc.perform(post("/lessons"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("lesson"))
                .andExpect(view().name("redirect:/lessons"));
    }

    @Test
    void shouldRedirectToLessons_whenCalledLessonsEditIdPATCH() throws Exception {
        mockMvc.perform(patch("/lessons/edit/20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("lesson"))
                .andExpect(view().name("redirect:/lessons"));
    }

    @Test
    void shouldRedirectToLessons_whenCalledLessonsDeleteIdDELETE() throws Exception {
        mockMvc.perform(delete("/lessons/delete/20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/lessons"));
    }
}
