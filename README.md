# todo_list docker compose Spring Boot MySQL

## Overview

Welcome to the Todo List web application repository! Here, you'll find a fully functional application built using Spring Boot and MySQL, serving as a solid foundation for your server development projects.

## Tutorial

For a detailed tutorial on how to use this project, check out the [YouTube playlist](https://www.youtube.com/playlist?list=PLyJPITz5u3sqmsxwE06ZM55zQCIhjqoX_).

### Dockerized Environment

The entire application has been dockerized for easy deployment and management. To get started, ensure you have Docker installed on your machine by following the [official Docker documentation](https://docs.docker.com/engine/install/).

### Getting Started

To launch the application, navigate to the root directory of the project and run the following command:

```bash
docker-compose up --build -d
```

This command builds and starts the application within a Docker container. The application will be accessible on port 8080.

### Shutdown

To bring down the application and associated containers, use the following command:

```bash
docker-compose down --rmi all
```

The `--rmi all` flag removes all associated images. If you wish to retain the images for future use, simply omit this flag from the command.

### Testing

This application comes with various test examples, including unit tests, integration tests, and sanity tests. Feel free to extend these tests based on your specific requirements and use cases. For the integration test, the application uses the test profile and read `demo/src/main/resources/application-test.properties` for configuring the app. For the Test profile, an in-memory database, h2, is loaded and used.

With this setup, you're ready to start building and extending the Todo List application according to your project needs. Happy coding!

## Task Management API

This part provides an overview of the RESTful API endpoints available in the `TaskController` class of the Spring Boot application.

### Overview

The `TaskController` class defines endpoints for managing tasks. It provides functionalities to retrieve, create, update, and delete tasks.
The `AuthenticationController` class defines the endpoints to register users and log into the application.

### Authentication

The Authentication Controller handles user authentication and registration within the application. It provides endpoints for user login and registration.

#### Endpoints

- **Login**: `/api/v1/auth/login`

  - Method: `POST`
  - Description: Authenticates a user with the provided credentials and generates a JWT token upon successful authentication.
  - Request Body:
    - `username`: Username of the user.
    - `password`: Password of the user.
  - Response:
    - HTTP Status Code:
      - `200 OK` - Successful authentication. Returns a JWT token in the response body.
      - `401 Unauthorized` - Authentication failed.
  - Sample Request:
    ```json
    {
      "username": "exampleUser",
      "password": "examplePassword"
    }
    ```
  - Sample Response:
    ```json
    {
      "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMjIiLCJpYXQiOjE3MTIwNjYxNzAsImV4cCI6MTcxMjEwMjE3MH0.Ad5l9PZBjfnJI2hFFoNN0Qy2Zp3r6i7z3w7PspQJEICg8HFUmspJMfNbYEyk51j1kSaKL10gVxoICcK3tTthIQ",
      "tokenType": "Bearer "
    }
    ```

- **Register**: `/api/v1/auth/register`
  - Method: `POST`
  - Description: Registers a new user in the system.
  - Request Body:
    - `username`: Username of the new user.
    - `password`: Password of the new user.
  - Response:
    - HTTP Status Code:
      - `200 OK` - User registration successful.
  - Sample Request:
    ```json
    {
      "username": "newUser",
      "password": "newUserPassword"
    }
    ```

### Authorization

Requests to all controllers, except the AuthController, should include an Authorization Header with the generated JWT Token in the format Bearer <JWT Token>.

### Task Controller

The base URL for accessing the API endpoints is `/api/v1/tasks`.

### Endpoints

#### Get All Tasks

- **Method**: `GET`
- **URL**: `/`
- **Description**: Retrieves all tasks.
- **Response**: Returns a list of all tasks.

#### Get All Completed Tasks

- **Method**: `GET`
- **URL**: `/completed`
- **Description**: Retrieves all completed tasks.
- **Response**: Returns a list of all completed tasks.

#### Get All Incomplete Tasks

- **Method**: `GET`
- **URL**: `/incomplete`
- **Description**: Retrieves all incomplete tasks.
- **Response**: Returns a list of all incomplete tasks.

#### Create Task

- **Method**: `POST`
- **URL**: `/`
- **Description**: Creates a new task.
- **Request Body**: Task object in JSON format.
- **Response**: Returns the created task.

#### Update Task

- **Method**: `PUT`
- **URL**: `/{id}`
- **Description**: Updates an existing task.
- **Path Variable**: `id` (ID of the task to be updated).
- **Request Body**: Updated task object in JSON format.
- **Response**: Returns the updated task.

#### Delete Task

- **Method**: `DELETE`
- **URL**: `/{id}`
- **Description**: Deletes an existing task.
- **Path Variable**: `id` (ID of the task to be deleted).
- **Response**: Returns `true` upon successful deletion.

### Sample Usage

#### Retrieve All Tasks

```
GET /api/v1/tasks/
```

#### Create New Task

```
POST /api/v1/tasks/
Request Body:
{
  "task": "Sample Task",
  "completed": false
}
```

#### Update Task

```
PUT /api/v1/tasks/{id}
Request Body:
{
  "id": 1,
  "task": "Updated Task",
  "completed": true
}
```

#### Delete Task

```
DELETE /api/v1/tasks/{id}
```

### Notes

- Replace `{id}` with the actual ID of the task when making requests to update or delete a specific task.
- All endpoints return appropriate HTTP status codes to indicate the success or failure of the operation.
- It is important to configure the my.cnf file to restrict remote connections to your database and only allow connections from specific IP addresses or ranges. This helps protect your database from unauthorized access.
