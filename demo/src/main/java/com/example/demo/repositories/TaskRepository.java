package com.example.demo.repositories;

import com.example.demo.models.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  public Task findByTask(String task);

  public List<Task> findByCompletedTrue();

  public List<Task> findByCompletedFalse();

  public List<Task> findAll();

  public Task getById(Long id);
}
