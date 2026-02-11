package com.app.todo.dto;

// Advantage of using DTO design pattern is to decouple JPA Entity from the client
// Instead of returning the JPA Entity to the clien which may conatain sensitive information 
// We can only pass the required information to the client using DTO

public class TodoDto 
{
	private Long id;
	private String title;
	private String description;
	private boolean completed;
	
	public TodoDto() {
		// TODO Auto-generated constructor stub
	}
	
	
	public TodoDto(Long id, String title, String description, boolean completed) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.completed = completed;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isCompleted() {
		return completed;
	}


	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	@Override
	public String toString() {
		return "TodoDto [id=" + id + ", title=" + title + ", description=" + description + ", completed=" + completed
				+ "]";
	}
	
	
	
	
	
}
