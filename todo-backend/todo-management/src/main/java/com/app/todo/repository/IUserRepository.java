package com.app.todo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.todo.entity.User;


public interface IUserRepository extends JpaRepository<User, Long> {
	
	
	Optional<User> findByUserName(String username);
	// findBy finds the entity using field name here field name is -> UserName
	
	Boolean existsByEmail(String email);
	// standard keyword provided by spring data jpa --> existsBy   Email is our field name with first letter capital
	
	Optional<User> findByUserNameOrEmail(String userName, String email);
	// Custom query method retrive User by userName of email
	
	Boolean existsByUserName(String username);
	// Custom query method to check user exists with the given userName or not
	

}
