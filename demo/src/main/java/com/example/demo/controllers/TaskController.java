package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/tasks")
public class TaskController {
  @Autowired private TaskService taskService;

  @GetMapping("/")
  public ResponseEntity<List<Task>> getAllTask() {
    return ResponseEntity.ok(taskService.getAllTask());
  }

  @GetMapping("/completed")
  public ResponseEntity<List<Task>> getAllCompletedTask() {
    return ResponseEntity.ok(taskService.findAllCompletedTask());
  }

  @GetMapping("/incomplete")
  public ResponseEntity<List<Task>> getAllIncompleteTask() {
    return ResponseEntity.ok(taskService.findAllIncompleteTask());
  }

  @PostMapping("/")
  public ResponseEntity<Task> createTask(@RequestBody Task task) {
    return ResponseEntity.ok(taskService.createNewTask(task));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
    task.setId(id);
    return ResponseEntity.ok(taskService.updateTask(task));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteTask(@PathVariable Long id) {
    taskService.deleteTask(id);
    return ResponseEntity.ok(true);
  }
}
