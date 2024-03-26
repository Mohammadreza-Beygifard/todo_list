package com.example.demo.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {

  String BASE_URL = "/api/v1/tasks";

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void testGetAllTasks() {

    ResponseEntity<String> response =
        restTemplate.getForEntity("http://localhost:" + port + BASE_URL + "/", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testTaskCreate() {
    String url = "http://localhost:" + port + BASE_URL + "/";

    String requestBody = "{\"task\":\"Practice\",\"completed\":\"false\"}";

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<String> response =
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());

    ResponseEntity<String> get_response = restTemplate.getForEntity(url, String.class);

    assertEquals(HttpStatus.OK, get_response.getStatusCode());
    String expected_response_body = "[{\"id\":1,\"task\":\"Practice\",\"completed\":false}]";
    assertEquals(expected_response_body, get_response.getBody());
  }
}
