package com.example.demo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TaskControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private TaskService taskService;

  private Task task1;
  private Task task2;
  private Task task3;
  private List<Task> task_list;

  @BeforeEach
  void setUp() {
    task1 = new Task("Task1", false);
    task1.setId(1L);
    task2 = new Task("Task2", false);
    task1.setId(2L);
    task3 = new Task("Task3", true);
    task1.setId(3L);
    task_list = new ArrayList<>();
    task_list.add(task1);
    task_list.add(task2);
    task_list.add(task3);
  }

  @Test
  void getAllTaskShouldReturnTaskList() throws Exception {
    when(taskService.getAllTask()).thenReturn(task_list);
    // Perform the HTTP GET request and verify the response
    mockMvc
        .perform(get("/api/v1/tasks/"))
        .andExpect(status().isOk()) // Expect HTTP status code 200 OK
        .andExpect(jsonPath("$", hasSize(3))) // Expect the response to contain 3 tasks
        .andExpect(jsonPath("$[0].id").value(task1.getId())) // Expect the ID of the first task
        .andExpect(
            jsonPath("$[0].task").value(task1.getTask())) // Expect the task name of the first task
        .andExpect(
            jsonPath("$[0].completed")
                .value(task1.isCompleted())) // Expect the completed status of the first task
        .andExpect(jsonPath("$[1].id").value(task2.getId())) // Expect the ID of the second task
        .andExpect(
            jsonPath("$[1].task").value(task2.getTask())) // Expect the task name of the second task
        .andExpect(
            jsonPath("$[1].completed")
                .value(task2.isCompleted())) // Expect the completed status of the second task
        .andExpect(jsonPath("$[2].id").value(task3.getId())) // Expect the ID of the third task
        .andExpect(
            jsonPath("$[2].task").value(task3.getTask())) // Expect the task name of the third task
        .andExpect(
            jsonPath("$[2].completed")
                .value(task3.isCompleted())); // Expect the completed status of the third task
  }
}
