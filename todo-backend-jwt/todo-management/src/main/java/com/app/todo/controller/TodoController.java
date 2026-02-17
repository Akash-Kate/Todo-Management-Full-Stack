package com.app.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.todo.dto.TodoDto;
import com.app.todo.service.ITodoService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/todos") // Base URL for all apis under this class
public class TodoController {

		// This Controller class has a dependency of service class
		
		@Autowired
		private ITodoService todoService;
		
		
		// Build add todo REST API
		@PreAuthorize("hasRole('ADMIN')") // Method Level Security -> Only ADMIN user can access this Add Todo REST API
		@PostMapping
		public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto)
		{
			TodoDto detachedTod = todoService.addTodo(todoDto);
			
			return new ResponseEntity<>(detachedTod, HttpStatus.CREATED);
			
		}
	
		
		// Build get todo REST API
		@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
		@GetMapping("{id}")
		public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId)
		{
			TodoDto todo =  todoService.getTodo(todoId);
			
			
			return new ResponseEntity<>(todo, HttpStatus.OK);
		}
		
		
		// Build get all todos REST API
		@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
		@GetMapping
		public ResponseEntity<List<TodoDto>> getAllTodods()
		{
			List<TodoDto> todos =  todoService.getAllTodods();
			
			
			// return new ResponseEntity<>(todos, HttpStatus.OK);
			
			return ResponseEntity.ok(todos);   // This method is shortcut for the above method
		}
		
		
		
		// Build Update TODO REST API
		@PreAuthorize("hasRole('ADMIN')")
		@PutMapping("{id}")
		public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto, @PathVariable("id") Long todoId)
		{
			TodoDto updatedTodo =   todoService.updateTodo(todoDto, todoId);
			
			return ResponseEntity.ok(updatedTodo);
		}
		
		// Build delete Todo REST API
		@PreAuthorize("hasRole('ADMIN')")
		@DeleteMapping("{id}")
		public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId)
		{
			todoService.deleteTodo(todoId);
			
			return ResponseEntity.ok("Todo Deleted Successfully");
		}
		
		
		// Build COMPLETE Todo Rest Api
		@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
		@PatchMapping("{id}/complete")
		public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId)
		{
			TodoDto updatedTodo = todoService.completeTodo(todoId);
			
			
			return ResponseEntity.ok(updatedTodo);
		}
		
		
		@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
		@PatchMapping("{id}/incomplete")
		public ResponseEntity<TodoDto> inColeteTodo(@PathVariable("id") Long todoId)
		{
			TodoDto updatedTodo = todoService.inCompleteTodo(todoId);
			
			return ResponseEntity.ok(updatedTodo);
		}
		
		
		
		
		
		
}


































