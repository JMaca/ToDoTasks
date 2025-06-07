package org.example.ToDoTasks.model;

import jakarta.persistence.*;

import java.time.LocalDate;


/*SQL TRANSLATION - If I were to not use Persistence API then I would define the table in PostgreSQL like this:
    CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    name VARCHAR (255),
    description TEXT,
    priority VARCHAR(50),
    due_date DATE,
    completed BOOLEAN
    );
*/

@Entity
@Table(name = "tasks")
public class Task {
    @Id // Tells JPA this is the key of "tasks" table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Allows JPA to auto generate the value for each Task.
    private long id; // Unique key to identify different Tasks
    private String name; // Name for Task
    private String description; // Description of Task
    private String priority; // Priority of Task -> High , Medium , Low.

    @Column(name = "due_date") // Tells JPA to create a separate column called "due_date".
    private LocalDate dueDate; // Date of required completion.

    private boolean completed; // True or false of whether task has been completed.

    public Task() {
    }

    public Task(String name, String description, String priority, LocalDate dueDate, boolean completed) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public long getId() {
        return id;
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
