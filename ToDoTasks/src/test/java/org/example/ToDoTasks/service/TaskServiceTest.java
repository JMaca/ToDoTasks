package org.example.ToDoTasks.service;

import org.example.ToDoTasks.dto.TaskDTO;
import org.example.ToDoTasks.exception.TaskNotFoundException;
import org.example.ToDoTasks.model.Task;
import org.example.ToDoTasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }

    @Test
    @DisplayName("Should create a new task and save it")
    void testCreateTask() {

        // Given - Fixed date(LocalDate), Fixed Task DTO as the method requires.
        LocalDate fixedDate = LocalDate.of(2025, 1, 1);
        TaskDTO newTaskDTO = new TaskDTO("Test Task", "Test Description", "High", fixedDate, false);

        // Create Task with manual set ID - Since we Mock the database entry for testing purposes (Database automatically assigns ID).
        Task savedTask = new Task("Test Task", "Test Description", "High", fixedDate, false);
        savedTask.setID(1L);

        // Mock saving Task and returning entity.
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // Call the method we are testing
        TaskDTO result = taskService.createTask(newTaskDTO);

        // Verify the result is converted back into a TaskDTO object, and values match to confirm the method returns correct data.
        assertNotNull(result);
        assertEquals("Test Task", result.getName());
        assertEquals("Test Description", result.getDescription());
        assertEquals("High", result.getPriority());
        assertEquals(fixedDate, result.getDueDate());
        assertFalse(result.isCompleted());

        // Verify that taskRepository.save() was only called once.
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());

        // Verify the capturedTask is converted into a Task object before saving
        Task capturedTask = taskCaptor.getValue();
        assertEquals("Test Task", capturedTask.getName());
        assertEquals("Test Description", capturedTask.getDescription());
        assertEquals("High", capturedTask.getPriority());
        assertEquals(fixedDate, capturedTask.getDueDate());
        assertFalse(capturedTask.isCompleted());
    }

    @Test
    @DisplayName("Should return task when found by ID")
    void testGetTaskById() {
        // Given - Fixed date(LocalDate), Fixed Id to pass into method.
        LocalDate fixedDate = LocalDate.of(2025, 1, 1);
        Long id = 1L;

        // Create Task with manual set ID and save - Since we Mock the database entry for testing purposes (Database automatically assigns ID).
        Task savedTask = new Task("Test Task", "Test Description", "High", fixedDate, false);
        savedTask.setID(id);

        // Mock repository to return the savedTask when findById is called.
        when(taskRepository.findById(id)).thenReturn(Optional.of(savedTask));

        // Call the method we are testing
        TaskDTO result = taskService.getTaskById(id);

        // Verify the result fields are correct
        assertNotNull(result);
        assertEquals("Test Task", result.getName());
        assertEquals("Test Description", result.getDescription());
        assertEquals("High", result.getPriority());
        assertEquals(fixedDate, result.getDueDate());
        assertFalse(result.isCompleted());

        // Verify that the repository was called correctly
        verify(taskRepository).findById(id);
    }

    @Test
    @DisplayName("Should throw TaskNotFoundException when task is not found by ID")
    void testGetTaskByIdNotFound() {
        // Given fixed ID
        Long id = 1L;

        // Mock the repository to return empty (task not found)
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // Expect TaskNotFoundException to be thrown
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(id));

        // Verify repository was called correctly
        verify(taskRepository).findById(id);
    }

    @Test
    @DisplayName("Should update an existing task successfully")
    void testUpdateTaskSuccess() {
        // Given - Fixed Id, Fixed LocalDate, Fixed Task with manual Id set(Mock Repository)
        Long id = 1L;
        LocalDate fixedDate = LocalDate.of(2025, 1, 1);
        Task existingTask = new Task("Old Task", "Old Description", "Low", fixedDate, false);
        existingTask.setID(id);

        // Create an updated DTO object required to pass into the method.
        TaskDTO updateDTO = new TaskDTO("Updated Task", "Updated Description", "High", fixedDate, true);

        // Mock repository returns after saving update.
        Task mockUpdatedTask = new Task("Updated Task", "Updated Description", "High", fixedDate, true);
        mockUpdatedTask.setID(id);

        // Mock repository methods so we can focus on testing TaskService methods.
        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(mockUpdatedTask);

        // Call method to test.
        TaskDTO result = taskService.updateTask(id, updateDTO);

        // Verify values are correct.
        assertNotNull(result);
        assertEquals("Updated Task", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals("High", result.getPriority());
        assertEquals(fixedDate, result.getDueDate());
        assertTrue(result.isCompleted());

        // Verify method was called once, and the repository was called once.
        verify(taskRepository).findById(id);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw TaskNotFoundException when updating non-existing task")
    void testUpdateTaskNotFound() {
        // Given - Fixed id, Fixed LocalDate, Fixed DTO object
        Long id = 1L;
        LocalDate fixedDate = LocalDate.of(2025, 1, 1);
        TaskDTO updateDTO = new TaskDTO("Updated Task", "Updated Description", "High", fixedDate, true);

        // Mock repository method
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // Expect TaskNotFoundException to be thrown
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(id, updateDTO));

        // Verify method was called once, and the repository was called once.
        verify(taskRepository).findById(id);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Should delete an existing task successfully")
    void testDeleteTaskSuccess() {
        // Given - Fixed Id, Fixed LocalDate, Fixed Task with manual Id set(Mock Repository).
        Long id = 1L;
        LocalDate fixedDate = LocalDate.of(2025, 1, 1);
        Task existingTask = new Task("Task to delete", "Some description", "Medium", fixedDate, false);
        existingTask.setID(id);
        when(taskRepository.findById(id)).thenReturn(Optional.of(existingTask));

        // Call method for testing.
        taskService.deleteTask(id);

        // Verify method was called once, and the repository was called once.
        verify(taskRepository).findById(id);
        verify(taskRepository).delete(existingTask);
    }

    @Test
    @DisplayName("Should throw TaskNotFoundException when trying to delete a non-existent task")
    void testDeleteTaskNotFound() {
        // Given - Fixed id (Mock Repository)
        Long id = 999L;
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        // Call method - Expect exception to be thrown
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(id));

        // Verify method was called once, and the repository was called once.
        verify(taskRepository).findById(id);
        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    @DisplayName("Should return all tasks")
    void testGetAllTasks() {

        // Given-Fixed two Tasks, Mock Repository
        List<Task> mockTasks = List.of(
                new Task("Task 1", "Desc 1", "High", LocalDate.of(2025, 1, 1), false),
                new Task("Task 2", "Desc 2", "Low", LocalDate.of(2025, 1, 2), true)
        );
        when(taskRepository.findAll()).thenReturn(mockTasks);

        // Call method for testing
        List<Task> result = taskService.getAllTasks();

        // Verify result matches and that method was called once.
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getName());
        assertEquals("Task 2", result.get(1).getName());
        verify(taskRepository).findAll();
    }
}