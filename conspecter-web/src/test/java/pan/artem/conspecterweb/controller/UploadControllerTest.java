package pan.artem.conspecterweb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pan.artem.conspecterweb.exception.RepositoryInitializationException;
import pan.artem.conspecterweb.exception.RepositoryServiceInternalErrorException;
import pan.artem.conspecterweb.service.RepositoryServiceClient;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UploadControllerTest {

    @MockBean
    RepositoryServiceClient repositoryServiceClient;
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void uploadRepository() throws Exception {
        mockMvc.perform(post("/upload/author/repo-name")
                        .param("url", "url"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void uploadRepositoryBadInit() throws Exception {
        doThrow(RepositoryInitializationException.class).when(repositoryServiceClient)
                .uploadRepository("author", "repo-name", "url");

        mockMvc.perform(post("/upload/author/repo-name")
                        .param("url", "url"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void uploadRepositoryInternalError() throws Exception {
        doThrow(RepositoryServiceInternalErrorException.class).when(repositoryServiceClient)
                .uploadRepository("author", "repo-name", "url");

        mockMvc.perform(post("/upload/author/repo-name")
                        .param("url", "url"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(roles = "USER")
    void uploadRepositoryNotPermitted() throws Exception {
        mockMvc.perform(post("/upload/author/repo-name")
                        .param("url", "url"))
                .andExpect(status().isForbidden());
    }
}