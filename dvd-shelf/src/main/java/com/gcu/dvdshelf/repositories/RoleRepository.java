package com.gcu.dvdshelf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.dvdshelf.entities.Role;

/**
 * Interface repository for performing CRUD operations on Role entity
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	public Optional<Role> findByName(String name);

}
