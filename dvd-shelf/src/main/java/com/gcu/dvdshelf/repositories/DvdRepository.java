package com.gcu.dvdshelf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.dvdshelf.entities.Dvd;

/**
 * Repository interface for performing CRUD operations on Dvd entities.
 */
public interface DvdRepository extends JpaRepository<Dvd, Long> {
	
	/**
	 * Search DVD by title
	 * @param title
	 * @return
	 */
	List<Dvd> findByTitleContainingIgnoreCase(String title);
	
	/**
	 * Look up DVDs of a certain genre
	 * @param genre
	 * @return
	 */
	List<Dvd> findByGenres_Name(String genreName);
}
