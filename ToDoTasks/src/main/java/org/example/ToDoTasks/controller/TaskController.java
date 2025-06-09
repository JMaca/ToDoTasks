package org.example.ToDoTasks.controller;

import org.example.ToDoTasks.dto.TaskDTO;
import org.example.ToDoTasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Tells Spring that this class handles RESTful requests, and return values in JSON form.
@RequestMapping("/tasks") // Specifies base URL endpoint for this controller
public class TaskController {

    @Autowired // Injects class ( dependency injection ) Spring creates the required object.
    private TaskService taskService;

    //CRUD methods Create, Read, Update, Delete - HTTP status = 2XX success, 4XX Client Error, 5XX Server Error
    @PostMapping // POST method (Create)
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) { // RequestBody - Tells Spring to convert JSON into object.
        TaskDTO created = taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET method (Read)
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // PUT method (Update)
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDto) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDto));
    }

    // DELETE method (Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
