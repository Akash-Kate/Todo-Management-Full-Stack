package com.app.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.todo.dto.TodoDto;
import com.app.todo.entity.Todo;
import com.app.todo.exception.ResourceNotFoundException;
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


	@Override
	public TodoDto getTodo(Long id) {
		
		Todo todo = todoRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Todo Not Found with Id = "+id));
		
		
		return modelMapper.map(todo, TodoDto.class);
		
		
	}


	@Override
	public List<TodoDto> getAllTodods() {
		
		
		List<Todo> todos =  todoRepo.findAll();
		
		// convert list of todos JPA into List of todos DTO and return 
		
		return todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class))
													   				.collect(Collectors.toList());
	}


	@Override
	public TodoDto updateTodo(TodoDto todoDto, Long id) {
		
		Todo persistentTodo =  todoRepo.findById(id)   // Got existing Todo Object from DB table 
		  .orElseThrow(() -> new ResourceNotFoundException("Todo Not Found With Id = "+id)); // If a todo with id does not exist in the DB then throw custom error
		
		
		persistentTodo.setTitle(todoDto.getTitle());
		persistentTodo.setDescription(todoDto.getDescription());
		persistentTodo.setCompleted(todoDto.isCompleted());
		
		Todo updatedTodo =  todoRepo.save(persistentTodo); // Save method performs both the insert as well as the update operation
		
		
		// Convert and send the JPA updated Enitity into DTO and reurn to controller
		return modelMapper.map(updatedTodo, TodoDto.class);
	}


	@Override
	public void deleteTodo(Long id) {
		
		
		Todo todo =  todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo Not Found with Id : "+id));
		
		todoRepo.deleteById(id);
		
	}


	@Override
	public TodoDto completeTodo(Long id) {
		
		Todo todo = todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo Not Found with id : "+id));
		
		
		todo.setCompleted(Boolean.TRUE);
		
		Todo updatedTodo =  todoRepo.save(todo);
		
		 // Convert JPA to DTO and return 
		return modelMapper.map(updatedTodo, TodoDto.class);
	}


	@Override
	public TodoDto inCompleteTodo(Long id) {
		
		Todo todo =  todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : "+id));
		
		todo.setCompleted(Boolean.FALSE);
		
		Todo updatedTodo =  todoRepo.save(todo);
		
		return modelMapper.map(updatedTodo, TodoDto.class);
	}

	
}






