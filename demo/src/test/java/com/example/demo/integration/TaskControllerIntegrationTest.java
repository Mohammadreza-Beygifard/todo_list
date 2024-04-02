package com.example.demo.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerIntegrationTest {

  String BASE_URL = "/api/v1/tasks/";

  @Autowired private MockMvc mockMvc;

  @Test
  public void testGetAllTasks() throws Exception {
    mockMvc.perform(get(BASE_URL)).andExpect(status().isOk());
  }

  @Test
  public void testTaskCreate() throws Exception {
    String requestBody = "{\"task\":\"Practice\",\"completed\":\"false\"}";

    mockMvc
        .perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isOk());

    mockMvc
        .perform(get(BASE_URL))
        .andExpect(status().isOk())
        .andExpect(content().json("[{\"id\":1,\"task\":\"Practice\",\"completed\":false}]"));
  }
}
