package com.app.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.todo.entity.Todo;

public interface ITodoRepository extends JpaRepository<Todo, Long> {

	
	
	
	
}
