package com.example.demo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.demo.models.Task;
import com.example.demo.repositories.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

  @Mock private TaskRepository taskRepository;

  @InjectMocks private TaskService taskService;

  @Test
  public void testCreateNewTask() {
    Task task = new Task();
    when(taskRepository.save(task)).thenReturn(task);

    Task createdTask = taskService.createNewTask(task);

    assertEquals(task, createdTask);
    verify(taskRepository, times(1)).save(task);
  }

  @Test
  public void testGetAllTask() {
    List<Task> tasks = new ArrayList<>();
    when(taskRepository.findAll()).thenReturn(tasks);

    List<Task> result = taskService.getAllTask();

    assertEquals(tasks, result);
    verify(taskRepository, times(1)).findAll();
  }

  @Test
  public void testFindTaskByTaskId() {
    Task task = new Task();
    task.setId(1L);
    when(taskRepository.getById(1L)).thenReturn(task);

    Task result = taskService.findTaskByTaskId(1L);

    assertEquals(task, result);
    verify(taskRepository, times(1)).getById(1L);
  }

  @Test
  public void testFindAllCompletedTask() {
    List<Task> tasks = new ArrayList<>();
    when(taskRepository.findByCompletedTrue()).thenReturn(tasks);

    List<Task> result = taskService.findAllCompletedTask();

    assertEquals(tasks, result);
    verify(taskRepository, times(1)).findByCompletedTrue();
  }

  @Test
  public void testFindAllIncompleteTask() {
    List<Task> tasks = new ArrayList<>();
    when(taskRepository.findByCompletedFalse()).thenReturn(tasks);

    List<Task> result = taskService.findAllIncompleteTask();

    assertEquals(tasks, result);
    verify(taskRepository, times(1)).findByCompletedFalse();
  }

  @Test
  public void testDeleteTask() {
    Task task = new Task();
    task.setId(1L);
    when(taskRepository.getById(1L)).thenReturn(task);

    taskService.deleteTask(1L);

    verify(taskRepository, times(1)).delete(task);
  }

  @Test
  public void testUpdateTask() {
    Task task = new Task();
    when(taskRepository.save(task)).thenReturn(task);

    Task updatedTask = taskService.updateTask(task);

    assertEquals(task, updatedTask);
    verify(taskRepository, times(1)).save(task);
  }
}
