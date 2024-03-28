package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  public void createUser(@NonNull User user) {

    userRepository.save(user);
  }

  public List<User> getAllUsers() {

    return userRepository.findAll();
  }

  public User getUserByUsername(@NonNull String username) {
    return userRepository.findByUsername(username);
  }

  public void deleteUser(@NonNull User user) {

    userRepository.delete(user);
  }
}
