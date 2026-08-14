package com.gcu.dvdshelf.services;

import java.util.List;
import java.util.Optional;

import com.gcu.dvdshelf.entities.Genre;

/**
 * Service interface defining business operations for managing Genre records.
 */
public interface GenreService {
	
	public boolean registerGenre(String name);
	
	public List<Genre> getAllGenres();
	
	public Optional<Genre> getGenreById(Integer id);
	
	public boolean updateGenre(Integer id, String name);
	
	public boolean deleteGenre(Integer id);

}
