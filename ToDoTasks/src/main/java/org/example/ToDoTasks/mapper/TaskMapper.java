package org.example.ToDoTasks.mapper;

import org.example.ToDoTasks.dto.TaskDTO;
import org.example.ToDoTasks.model.Task;

public class TaskMapper {
    public static TaskDTO toDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setName(task.getName());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setCompleted(task.isCompleted());
        return dto;
    }

    public static Task fromDTO(TaskDTO dto) {
        return new Task(
                dto.getName(),
                dto.getDescription(),
                dto.getPriority(),
                dto.getDueDate(),
                dto.isCompleted()
        );
    }
}
