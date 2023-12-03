package pan.artem.conspecterrepo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import pan.artem.conspecterrepo.TestDataSampleService;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MainControllerTest {

    private static final String baseUrl = "/api/";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestDataSampleService testDataSampleService;

    @BeforeEach
    void setUp() {
        testDataSampleService.setUp();
    }

    @Test
    void getRepositories() throws Exception {
        mockMvc.perform(get(baseUrl))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].name").value("repo-name")
                );
    }

    @Test
    void getConspects() throws Exception {
        mockMvc.perform(get(baseUrl + "repo-name")
                        .param("username", "user"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].name").value("conspect-name")
                );
    }

    @Test
    void getConspectsNotExists() throws Exception {
        mockMvc.perform(get(baseUrl + "not-exists")
                        .param("username", "user"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", hasSize(0))
                );
    }

    @Test
    void getTask() throws Exception {
        mockMvc.perform(get(baseUrl + "repo-name/conspect-name")
                        .param("username", "user"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.text").value("task2-text")
                );
    }

    @Test
    void getTaskNull() throws Exception {
        mockMvc.perform(get(baseUrl + "repo-name/not-exists")
                        .param("username", "user"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").doesNotExist()
                );
    }

    @Test
    void sendSolution() throws Exception {
        mockMvc.perform(get(baseUrl + "repo-name/conspect-name")
                .param("username", "user"));

        mockMvc.perform(post(baseUrl)
                        .param("username", "user")
                        .param("solution", "task2 answer"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.status").value(true)
                );
    }

    @Test
    void getCurrentTask() throws Exception {
        mockMvc.perform(get(baseUrl + "repo-name/conspect-name")
                .param("username", "user"));

        mockMvc.perform(get(baseUrl + "current")
                        .param("username", "user"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.text").value("task2-text")
                );
    }

    @Test
    void getCurrentTaskNull() throws Exception {
        mockMvc.perform(get(baseUrl + "current")
                        .param("username", "user"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$").doesNotExist()
                );
    }
}