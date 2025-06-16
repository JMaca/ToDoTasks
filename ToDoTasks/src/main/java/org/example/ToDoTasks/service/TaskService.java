package org.example.ToDoTasks.service;

import org.example.ToDoTasks.dto.TaskDTO;
import org.example.ToDoTasks.exception.TaskNotFoundException;
import org.example.ToDoTasks.mapper.TaskMapper;
import org.example.ToDoTasks.model.Task;
import org.example.ToDoTasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Tells Spring this class is a service component
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired // Tell Spring to inject this class
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Create a task
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task newTask = TaskMapper.fromDTO(taskDTO);
        Task saved = taskRepository.save(newTask);
        return TaskMapper.toDTO(saved);
    }

    // Get Task by ID
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found")); // Spring exception handling if ID does not exist.
        return TaskMapper.toDTO(task);
    }

    // Update Task
    public TaskDTO updateTask(Long id, TaskDTO taskDto) {
        Task toBeUpdatedTask = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task " + id + " not found")); // Call repository to search/return Task
        // Update all fields - Currently required (PATCH updating/single attributes for update can be added in the future)
        toBeUpdatedTask.setName(taskDto.getName());
        toBeUpdatedTask.setDescription(taskDto.getDescription());
        toBeUpdatedTask.setDueDate(taskDto.getDueDate());
        toBeUpdatedTask.setPriority(taskDto.getPriority());
        toBeUpdatedTask.setCompleted(taskDto.isCompleted());

        // Call repository to save a newly updated task to DB
        Task updatedTask = taskRepository.save(toBeUpdatedTask);

        // Return the task after it has been converted to DTO by the TaskMapper
        return TaskMapper.toDTO(updatedTask);
    }

    // Delete Task
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found")); // Spring exception handling if ID does not exist.
        taskRepository.delete(task);
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
