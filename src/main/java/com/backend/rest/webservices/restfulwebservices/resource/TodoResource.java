package com.backend.rest.webservices.restfulwebservices.resource;
import com.backend.rest.webservices.restfulwebservices.repository.TodoRepository;
import com.backend.rest.webservices.restfulwebservices.todo.Todo;
import com.backend.rest.webservices.restfulwebservices.todo.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoResource {
    private TodoService todoService;

    private TodoRepository todoRepository;

    public TodoResource(TodoService todoService, TodoRepository todoRepository){
        this.todoService = todoService;
        this.todoRepository = todoRepository;
    }
    @GetMapping(path = "/users/{username}/todos")
    public List<Todo> retrieveTodos(@PathVariable String username){

        //return todoService.findByUsername(username);
        return todoRepository.findByUsername(username);
    }

    @GetMapping(path = "/users/{username}/todos/{id}")
    public Todo retrieveTodosById(@PathVariable String username ,@PathVariable int id){
       // return todoService.findById(id);
        return todoRepository.findById(id).get();
    }

    @GetMapping(path = "/users/path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping(path = "/basicauth")
    public String basicAuthCheck() {
        return "Success";
    }

    @DeleteMapping(path = "/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable String username , @PathVariable int id){
//        todoService.deleteById(id);
//        return ResponseEntity.noContent().build();//it will return no content as a response
       todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/users/{username}/todos/{id}")
    public Todo updateTodoById(@PathVariable int id, @RequestBody Todo todo){
       // todoService.updateTodo(todo);
        todoRepository.save(todo);
        return todo;
    }

    @PostMapping(path = "/users/{username}/todos")
    public Todo createTodo(@PathVariable String username, @RequestBody Todo todo){
//        Todo createdTodo = todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), todo.isDone());
//        return createdTodo;
        todo.setUsername(username);
        todo.setId(null);//we are setting id as null because save method perform both update and create functionality of jpa,
        //if id already exists it will update todo otherwise it will create new todo 
        return todoRepository.save(todo);
    }
}

class HelloWorldBean {

    private String message;

    public HelloWorldBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HelloWorldBean [message=" + message + "]";
    }
}

