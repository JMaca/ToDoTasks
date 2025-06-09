package org.example.ToDoTasks.repository;

import org.example.ToDoTasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // With JpaRepository from Spring, the common methods for CRUD are automatically provided at runtime.
    // Custom queries can be supported and simplified/translated into PostgresSQL.

    /*SQL
     SELECT * FROM tasks
     WHERE due_Date = dueDate;
     */
    List<Task> findByDueDate(LocalDate dueDate);

    /*SQL
     SELECT * FROM tasks
     WHERE completed = true;
     */
    List<Task> findByCompleted(boolean completed);

    /*SQL
     SELECT * FROM tasks;
     WHERE priority = 'High'
     */
    List<Task> findByPriority(String priority);

    /*SQL
     SELECT * FROM tasks
     WHERE priority = 'High' AND due_date = dueDate
     */
    List<Task> findByPriorityAndDueDate(String priority, LocalDate dueDate);

    /*SQL
     SELECT * FROM tasks
     WHERE priority = 'High' AND due_date = dueDate AND completed = true;
     */
    List<Task> findByPriorityAndDueDateAndCompleted(String priority, LocalDate dueDate, Boolean completed);
}
