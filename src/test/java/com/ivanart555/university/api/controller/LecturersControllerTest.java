package com.ivanart555.university.api.controller;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Lecturer;
import com.ivanart555.university.services.CourseService;
import com.ivanart555.university.services.LecturerService;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(TestSpringConfig.class)
@ExtendWith(MockitoExtension.class)
class LecturersControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LecturerService lecturerService;

    @Mock
    private CourseService courseService;
    @Mock
    Page<Lecturer> anyPage;

    @InjectMocks
    private LecturersController lecturersController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(lecturersController).build();
    }

    @Test
    void mockMvcTest() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnViewLecturersIndex_whenCalledLecturersGET() throws Exception {
        when(lecturerService.findAll(any())).thenReturn(anyPage);
        mockMvc.perform(get("/lecturers"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("lecturerPage"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("lecturer"))
                .andExpect(view().name("lecturers/index"));
    }

    @Test
    void shouldRedirectToLecturers_whenCalledLecturersPOST() throws Exception {
        mockMvc.perform(post("/lecturers"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("lecturer"))
                .andExpect(view().name("redirect:/lecturers"));
    }

    @Test
    void shouldRedirectToLecturers_whenCalledLecturersEditIdPATCH() throws Exception {
        mockMvc.perform(patch("/lecturers/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("lecturer"))
                .andExpect(view().name("redirect:/lecturers"));
    }

    @Test
    void shouldRedirectToLecturers_whenCalledLecturersDeleteIdDELETE() throws Exception {
        mockMvc.perform(delete("/lecturers/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/lecturers"));
    }
}
