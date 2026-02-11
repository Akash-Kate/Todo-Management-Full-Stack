package com.app.todo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.todo.dto.TodoDto;
import com.app.todo.entity.Todo;
import com.app.todo.repository.ITodoRepository;

@Service
public class TodoServiceImpl implements ITodoService{

	
	// This TodoService Impl class requires TodoRepository as a dependency
	
	@Autowired
	private ITodoRepository todoRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public TodoDto addTodo(TodoDto todoDto) {
		
		
		// convert todo DTO into todo JPA Entity
//		Todo todo = new Todo();
//		todo.setTitle(todoDto.getTitle());
//		todo.setDescriptiopn(todoDto.getDescription());
//		todo.setCompleted(todoDto.isCompleted());   
		
		// Because of externl third party ModelMapper class all this Biolder plate code has been reduced
		
		Todo todo = modelMapper.map(todoDto, Todo.class);  // Source , Destination
		
		// Save Todo JPA into DB
		Todo savedTodo = todoRepo.save(todo);
		
		
		// Convert JPA todo to todo DTO
//		TodoDto savedTodoDto = new TodoDto();
//		
//		savedTodoDto.setId(savedTodo.getId());
//		savedTodoDto.setTitle(savedTodo.getTitle());
//		savedTodoDto.setDescription(savedTodo.getDescriptiopn());
//		savedTodoDto.setTitle(savedTodo.getTitle());
//		savedTodoDto.setCompleted(savedTodo.isCompleted());
		
		TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class); 
		
		
		return savedTodoDto;
	}

	
	
	
}
