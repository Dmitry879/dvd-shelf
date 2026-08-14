package com.gcu.dvdshelf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.dvdshelf.entities.Genre;

/**
 * Repository interface for performing CRUD operations on Genre entities
 */
public interface GenreRepository extends JpaRepository<Genre, Integer> {
	
	List<Genre> findAllByOrderByNameAsc();

}
