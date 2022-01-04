package com.ivanart555.university.controllers_test;

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

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.controllers.CoursesController;
import com.ivanart555.university.entities.Course;
import com.ivanart555.university.services.CourseService;

@SpringJUnitWebConfig(TestSpringConfig.class)
@ExtendWith(MockitoExtension.class)
class CoursesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @Mock
    Page<Course> anyPage;

    @InjectMocks
    private CoursesController coursesController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(coursesController).build();
    }

    @Test
    void mockMvcTest() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnViewCoursesIndex_whenCalledCoursesGET() throws Exception {
        when(courseService.findPaginated(any())).thenReturn(anyPage);
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("coursePage"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("course"))
                .andExpect(view().name("courses/index"));
    }

    @Test
    void shouldRedirectToClassrooms_whenCalledClassroomsPOST() throws Exception {
        mockMvc.perform(post("/courses"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("course"))
                .andExpect(view().name("redirect:/courses"));
    }

    @Test
    void shouldRedirectToClassrooms_whenCalledClassroomsEditIdPATCH() throws Exception {
        mockMvc.perform(patch("/courses/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("course"))
                .andExpect(view().name("redirect:/courses"));
    }

    @Test
    void shouldRedirectToClassrooms_whenCalledClassroomsDeleteIdDELETE() throws Exception {
        mockMvc.perform(delete("/courses/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/courses"));
    }
}
