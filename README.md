# todo_list docker compose Spring boot MySQL

## Overview

Welcome to the Todo List web application repository! Here, you'll find a fully-functional application built using Spring Boot and MySQL, serving as a solid foundation for your server development projects.

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

### Base URL

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
- Ensure proper authentication and authorization mechanisms are implemented before deploying this API to production environments.
- It is important to configure the my.cnf file to restrict remote connections to your database and only allow connections from specific IP addresses or ranges. This helps protect your database from unauthorized access.
