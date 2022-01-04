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
import com.ivanart555.university.controllers.LessonsController;
import com.ivanart555.university.dto.LessonDto;
import com.ivanart555.university.services.ClassroomService;
import com.ivanart555.university.services.CourseService;
import com.ivanart555.university.services.GroupService;
import com.ivanart555.university.services.LecturerService;
import com.ivanart555.university.services.LessonService;

@SpringJUnitWebConfig(TestSpringConfig.class)
@ExtendWith(MockitoExtension.class)
class LessonsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LessonService lessonService;
    @Mock
    private CourseService courseService;
    @Mock
    private LecturerService lecturerService;
    @Mock
    private ClassroomService classroomService;
    @Mock
    private GroupService groupService;

    @Mock
    Page<LessonDto> anyPage;

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
                .andExpect(model().attributeExists("lessonDto"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("lecturers"))
                .andExpect(model().attributeExists("classrooms"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(view().name("lessons/index"));
    }

    @Test
    void shouldRedirectToLessons_whenCalledLessonsPOST() throws Exception {
        mockMvc.perform(post("/lessons"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("lessonDto"))
                .andExpect(view().name("redirect:/lessons"));
    }

    @Test
    void shouldRedirectToLessons_whenCalledLessonsEditIdPATCH() throws Exception {
        mockMvc.perform(patch("/lessons/edit/20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("lessonDto"))
                .andExpect(view().name("redirect:/lessons"));
    }

    @Test
    void shouldRedirectToLessons_whenCalledLessonsDeleteIdDELETE() throws Exception {
        mockMvc.perform(delete("/lessons/delete/20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/lessons"));
    }
}
