package com.example.demo.services;

import com.example.demo.models.Task;
import com.example.demo.repositories.TaskRepository;
import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
  @Autowired private TaskRepository taskRepository;

  public Task createNewTask(@NonNull Task task) {
    return taskRepository.save(task);
  }

  public List<Task> getAllTask() {
    return taskRepository.findAll();
  }

  public Task findTaskByTaskId(Long id) {
    return taskRepository.getById(id);
  }

  public List<Task> findAllCompletedTask() {
    return taskRepository.findByCompletedTrue();
  }

  public List<Task> findAllIncompleteTask() {
    return taskRepository.findByCompletedFalse();
  }

  public void deleteTask(Long id) {
    Task task = taskRepository.getById(id);
    if (task == null) {
      return;
    }
    taskRepository.delete(task);
  }

  public Task updateTask(@NonNull Task task) {
    return taskRepository.save(task);
  }
}
