package org.example.ToDoTasks.controller;

import org.example.ToDoTasks.dto.TaskDTO;
import org.example.ToDoTasks.model.Task;
import org.example.ToDoTasks.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    private TaskService taskService;
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        taskService = mock(TaskService.class);  // manually mock
        taskController = new TaskController(taskService);  // constructor injection
    }

    @Test
    @DisplayName("Should create a new task and return 201")
    void testCreateTask() {
         // Given - Fixed Date, Fixed TaskDTOs
        LocalDate fixedDate = LocalDate.of(2025, 1, 1);
        TaskDTO request = new TaskDTO("Test Task", "Test Description", "High", fixedDate, false);
        TaskDTO saved = new TaskDTO("Test Task", "Test Description", "High", fixedDate, false);

        // Call method to test
        when(taskService.createTask(request)).thenReturn(saved);

        // Get response and check that it matches, get correct status code
        var response = taskController.createTask(request);

        assertEquals(201, response.getStatusCode().value());

        assertNotNull(response.getBody(), "Response body should not be null");
        assertEquals("Test Task", response.getBody().getName());
        assertEquals("High", response.getBody().getPriority());
        assertEquals(fixedDate, response.getBody().getDueDate());
        assertFalse(response.getBody().isCompleted());
    }

    @Test
    @DisplayName("Should return a task with a specific ID from taskService")
    void testGetTaskByID() {
        // Given - Fixed Date, Fixed TaskDTO, Task ID
        Long taskId = 1L;
        LocalDate fixedDate = LocalDate.of(2025, 1, 1);
        TaskDTO expectedDTO = new TaskDTO("Test Task", "Test Description", "High", fixedDate, false);

        // mock service
        when(taskService.getTaskById(taskId)).thenReturn(expectedDTO);

        // Call method to test
        var result = taskController.getTaskById(taskId);

        //Check to see if result is correct
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals("Test Task", result.getBody().getName());
    }

    @Test
    @DisplayName("Should get all tasks from taskService")
    void testGetAllTasks() {
        // Given - Fixed Date, Fixed TaskDTO, Task ID
        LocalDate fixedDate = LocalDate.of(2025, 1, 1);
        Task task1 = new Task("Task 1", "Desc 1", "High", fixedDate, false);
        Task task2 = new Task("Task 2", "Desc 2", "Low", fixedDate, true);

        task1.setID(1L);
        task2.setID(2L);

        List<Task> tasks = List.of(task1, task2);
        // Mock Task Service
        when(taskService.getAllTasks()).thenReturn(tasks);

        // Call method to test
        var response = taskController.getAllTasks();

        // Check for correct values
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        TaskDTO dto1 = response.getBody().get(0);
        TaskDTO dto2 = response.getBody().get(1);

        assertEquals("Task 1", dto1.getName());
        assertEquals("High", dto1.getPriority());

        assertEquals("Task 2", dto2.getName());
        assertEquals("Low", dto2.getPriority());
    }

    @Test
    @DisplayName("Should update/replace a task")
    void testUpdateTask() {
        // Given - Fixed Date, Fixed TaskDTO
        Long taskId = 1L;
        LocalDate fixedDate = LocalDate.of(2025, 1, 1);
        TaskDTO updatedTask = new TaskDTO("Updated Task", "Updated Desc", "Medium", fixedDate, true);

        // Mock the service to return the updated task
        when(taskService.updateTask(eq(taskId), any(TaskDTO.class))).thenReturn(updatedTask);

        // Call method to test
        var response = taskController.updateTask(taskId, updatedTask);

        // Check for correct values
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Updated Task", response.getBody().getName());
        assertEquals("Medium", response.getBody().getPriority());
        assertTrue(response.getBody().isCompleted());
    }

    @Test
    @DisplayName("Should create a new task")
    void testDeleteTask() {
        // Given - Fixed Date, Fixed TaskDTO
        Long taskId = 1L;

        // Mock the service method (void method)
        doNothing().when(taskService).deleteTask(taskId);

        // call method to test
        var response = taskController.deleteTask(taskId);

        // check
        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());  // Since it's 204 No Content
    }
}
