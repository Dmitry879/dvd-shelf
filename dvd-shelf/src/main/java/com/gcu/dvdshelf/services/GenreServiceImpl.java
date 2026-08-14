package com.gcu.dvdshelf.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gcu.dvdshelf.entities.Genre;
import com.gcu.dvdshelf.repositories.GenreRepository;

/**
 * Implementation of the Genre interface, providing the concrete
 * business logic for creating, retrieving, updating, and
 * deleting Genre records.
 */
@Service
public class GenreServiceImpl implements GenreService {
	
	private final static Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);
	
	private final GenreRepository genreRepo;

	public GenreServiceImpl(GenreRepository genreRepo) {
		super();
		this.genreRepo = genreRepo;
	}

	@Override
	public boolean registerGenre(String name) {
		
		logger.info("Attempting to register genre: {}", name);
		
		var genre = new Genre();
		genre.setName(name);
		
		genreRepo.save(genre);
		logger.info("Genre registered successfully: {}", name);
		
		return true;
	}

	@Override
	public List<Genre> getAllGenres() {
		
		logger.info("Retrieving all genres");
		
		List<Genre> genres = genreRepo.findAllByOrderByNameAsc();
		logger.debug("Retrieved {} genres", genres.size());
		
		return genres;
	}

	@Override
	public Optional<Genre> getGenreById(Integer id) {
		
		logger.info("Retrieving genre with id: {}", id);
		
		Optional<Genre> genre = genreRepo.findById(id);
		if (genre.isEmpty()) {
			logger.warn("Genre not found with id: {}", id);
		}
		
		return genre;
	}

	@Override
	public boolean updateGenre(Integer id, String name) {
		
		logger.info("Attmepting to update genre with id: {}", id);
		
		Optional<Genre> existingGenre = genreRepo.findById(id);
		if (existingGenre.isEmpty()) {
			logger.warn("Update failed - genre not found with id: {}", id);
			return false;
		}
		
		Genre genre = existingGenre.get();
		genre.setName(name);
		
		genreRepo.save(genre);
		logger.info("Genre updated successfully with id: {}", id);
		
		return true;
	}

	@Override
	public boolean deleteGenre(Integer id) {
		
		logger.info("Attmepting to delete genre with id: {}", id);
		
		Optional<Genre> existingGenre = genreRepo.findById(id);
		if (existingGenre.isEmpty()) {
			logger.warn("Delete failed - genre not found with id: {}", id);
			return false;
		}
		
		genreRepo.delete(existingGenre.get());
		logger.info("Genre deleted successfully with id: {}", id);
		
		return true;
	}

}
