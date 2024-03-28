package com.example.demo.integration.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.DemoApplication;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserServiceIntegrationTest {

  @Autowired private UserService userService;

  @Autowired private UserRepository userRepository;

  @Test
  public void testCreateUser() {
    User user = new User("testUser", "testPassword");

    userService.createUser(user);

    List<User> users = userRepository.findAll();

    assertEquals(1, users.size());
    assertEquals(user.getUsername(), users.get(0).getUsername());
    assertEquals(user.getPassword(), users.get(0).getPassword());
  }
}
