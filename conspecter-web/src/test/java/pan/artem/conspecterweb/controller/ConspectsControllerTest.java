package pan.artem.conspecterweb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pan.artem.conspecterweb.dto.Conspect;
import pan.artem.conspecterweb.service.RepositoryServiceClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConspectsControllerTest {

    @MockBean
    RepositoryServiceClient repositoryServiceClient;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER")
    void show() throws Exception {
        when(repositoryServiceClient.getConspects("user", "repo-name"))
                .thenReturn(List.of(new Conspect("conspect-name", 4, 3)));

        String html = mockMvc.perform(get("/repo-name"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertAll(
                () -> assertTrue(html.contains("conspect-name")),
                () -> assertTrue(html.contains("3")),
                () -> assertTrue(html.contains("4")),
                () -> assertTrue(html.contains("Back"))
        );
    }
}