package org.example.ToDoTasks.dto;

import org.example.ToDoTasks.model.Task;

import java.time.LocalDate;

public class TaskDTO {
    private String name;
    private String description;
    private String priority;
    private LocalDate dueDate;
    private boolean completed;

    public TaskDTO() {
    }

    // All-args constructor
    public TaskDTO(String name, String description, String priority, LocalDate dueDate, boolean completed) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
