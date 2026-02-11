package com.app.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.todo.dto.TodoDto;
import com.app.todo.service.ITodoService;



@RestController
@RequestMapping("/api/todos") // Base URL for all apis under this class
public class TodoController {

		// This Controller class has a dependency of service class
		
		@Autowired
		private ITodoService todoService;
		
		
		// Build add todo REST API
		@PostMapping
		public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto)
		{
			TodoDto detachedTod = todoService.addTodo(todoDto);
			
			return new ResponseEntity<>(detachedTod, HttpStatus.CREATED);
			
		}
	
	
}
