package com.app.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.todo.entity.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> 
{
	Role findByName(String name);
}
