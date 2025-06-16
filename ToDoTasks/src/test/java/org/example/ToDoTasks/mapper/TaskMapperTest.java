package org.example.ToDoTasks.mapper;

import org.example.ToDoTasks.dto.TaskDTO;
import org.example.ToDoTasks.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskMapperTest {

    @Test // JUnit annotation - Tells a framework that this is a test
    @DisplayName("Should map Task to TaskDTO correctly") // Display offers readability/insight for why we have this test
    void testToDTO(){ // Create a Task with known values, convert them to DTO and check for accuracy.
        LocalDate fixedDate = LocalDate.of(2025, 1, 1); // fixed date
        Task task = new Task("Test Task", "Test Description", "High", fixedDate, false);
        TaskDTO dto = TaskMapper.toDTO(task);

        assertEquals(task.getName(), dto.getName());
        assertEquals(task.getDescription(), dto.getDescription());
        assertEquals(task.getPriority(), dto.getPriority());
        assertEquals(task.getDueDate(), dto.getDueDate());
        assertEquals(task.isCompleted(), dto.isCompleted());
    }

    @Test
    @DisplayName("Should correctly map TaskDTO to Task")
    void testFromDTO(){
        LocalDate fixedDate = LocalDate.of(2025, 1, 1);
        TaskDTO dto = new TaskDTO("Test Task", "Test Description", "High", fixedDate, false);
        Task task = TaskMapper.fromDTO(dto);

        assertEquals(dto.getName(), task.getName());
        assertEquals(dto.getDescription(), task.getDescription());
        assertEquals(dto.getPriority(), task.getPriority());
        assertEquals(dto.getDueDate(), task.getDueDate());
        assertEquals(dto.isCompleted(), task.isCompleted());
    }
}

