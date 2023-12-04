package pan.artem.conspecterweb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pan.artem.conspecterweb.dto.SolvedTask;
import pan.artem.conspecterweb.dto.Task;
import pan.artem.conspecterweb.service.RepositoryServiceClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TaskControllerTest {

    @MockBean
    RepositoryServiceClient repositoryServiceClient;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    void showTask() throws Exception {
        when(repositoryServiceClient.getTask(
                "user", "repo-name", "conspect-name"
        )).thenReturn(
                new Task("repo-name", "conspect-name", "task-text")
        );

        String html = mockMvc.perform(get("/repo-name/conspect-name"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(html.contains("task-text"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void showTaskNull() throws Exception {
        when(repositoryServiceClient.getTask(
                "user", "repo-name", "conspect-name"
        )).thenReturn(null);

        String html = mockMvc.perform(get("/repo-name/conspect-name"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertTrue(html.contains("No task found"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void showResult() throws Exception {
        when(repositoryServiceClient.getCurrentTask("user")).thenReturn(
                Optional.of(new Task("repo-name", "conspect-name", "task-text"))
        );
        when(repositoryServiceClient.sendSolution("user", "solution")).thenReturn(
                new SolvedTask("answer", 7, 8, true)
        );

        String html = mockMvc.perform(post("/repo-name/conspect-name")
                        .param("solution", "solution"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertAll(
                () -> assertTrue(html.contains("task-text")),
                () -> assertTrue(html.contains("answer")),
                () -> assertTrue(html.contains("7")),
                () -> assertTrue(html.contains("Success"))
        );
    }

    @Test
    @WithMockUser(roles = "USER")
    void showResultNull() throws Exception {
        when(repositoryServiceClient.getCurrentTask("user")).thenReturn(
                Optional.empty()
        );

        String html = mockMvc.perform(post("/repo-name/conspect-name")
                        .param("solution", "solution"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertTrue(html.contains("No task found"));
    }
}