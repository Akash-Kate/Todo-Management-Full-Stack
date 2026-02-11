package com.app.todo.service;

import java.util.List;

import com.app.todo.dto.TodoDto;

public interface ITodoService {

	TodoDto addTodo(TodoDto todoDto);
	
	TodoDto getTodo(Long id);
	
	List<TodoDto> getAllTodods();
	
	TodoDto updateTodo(TodoDto todoDto, Long id);
	
	void deleteTodo(Long id);
	
	TodoDto completeTodo(Long id);
	
	TodoDto inCompleteTodo(Long id);
	
	
}
