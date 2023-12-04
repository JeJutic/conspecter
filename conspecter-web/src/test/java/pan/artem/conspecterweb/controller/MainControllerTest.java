package pan.artem.conspecterweb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pan.artem.conspecterweb.dto.Repository;
import pan.artem.conspecterweb.service.RepositoryServiceClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MainControllerTest {

    @MockBean
    RepositoryServiceClient repositoryServiceClient;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    void showMain() throws Exception {
        when(repositoryServiceClient.getRepositories()).thenReturn(
                List.of(new Repository("einstein", "abstract"))
        );

        String html = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertTrue(html.contains("einstein"));
        assertTrue(html.contains("abstract"));
    }

    @Test
    void showMainUnauthorized() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void notFound() throws Exception {
        String html = mockMvc.perform(get("/not/existing/path"))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        assertTrue(html.contains("No task found"));
    }
}