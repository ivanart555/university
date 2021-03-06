package com.ivanart555.university.api.controller;

import com.ivanart555.university.config.TestSpringConfig;
import com.ivanart555.university.entities.Group;
import com.ivanart555.university.services.CourseService;
import com.ivanart555.university.services.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
class GroupsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GroupService groupService;

    @Mock
    private CourseService courseService;

    @Mock
    Page<Group> anyPage;

    @InjectMocks
    private GroupsController groupsController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupsController).build();
    }

    @Test
    void mockMvcTest() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnViewGroupsIndex_whenCalledGroupsGET() throws Exception {
        when(groupService.findAll(any(PageRequest.class))).thenReturn(anyPage);
        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("groupPage"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("group"))
                .andExpect(view().name("groups/index"));
    }

    @Test
    void shouldRedirectToGroups_whenCalledGroupsPOST() throws Exception {
        mockMvc.perform(post("/groups"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("group"))
                .andExpect(view().name("redirect:/groups"));
    }

    @Test
    void shouldRedirectToGroups_whenCalledGroupsEditIdPATCH() throws Exception {
        mockMvc.perform(patch("/groups/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("group"))
                .andExpect(view().name("redirect:/groups"));
    }

    @Test
    void shouldRedirectToGroups_whenCalledGroupsDeleteIdDELETE() throws Exception {
        mockMvc.perform(delete("/groups/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/groups"));
    }
}
