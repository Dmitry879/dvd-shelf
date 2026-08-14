package com.gcu.dvdshelf.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gcu.dvdshelf.dtos.DvdRequest;
import com.gcu.dvdshelf.entities.Dvd;
import com.gcu.dvdshelf.entities.Genre;
import com.gcu.dvdshelf.entities.Inventory;
import com.gcu.dvdshelf.repositories.DvdRepository;
import com.gcu.dvdshelf.repositories.GenreRepository;
import com.gcu.dvdshelf.repositories.InventoryRepository;

/**
 * Implementation of the DvdService interface, providing the concrete
 * business logic for registering, retrieving, updating, and deleting
 * DVD records
 */
@Service
public class DvdServiceImpl implements DvdService {
	
	private final static Logger logger = LoggerFactory.getLogger(DvdService.class);
	
	private final DvdRepository dvdRepo;
	private final GenreRepository genreRepo;
	private final InventoryRepository inventoryRepo;
	
	public DvdServiceImpl(DvdRepository dvdRepo,
						  GenreRepository genreRepo,
						  InventoryRepository inventoryRepo) {
		super();
		this.dvdRepo = dvdRepo;
		this.genreRepo = genreRepo;
		this.inventoryRepo = inventoryRepo;
	}

	@Override
	public boolean registerDvd(DvdRequest request) {
		
		logger.info("Attempting to register DVD with title: {}", request.getTitle());
		
		var dvd = new Dvd();
		setDvdDetails(request, dvd);
		dvd.setGenres(resolveGenres(request.getGenreIds()));
		
		// Handle optional image upload
		if (request.getImage() != null && !request.getImage().isEmpty()) {
			try {
				String filename = UUID.randomUUID() + "_" + request.getImage().getOriginalFilename();
				Path uploadPath = Paths.get("src/main/resources/static/uploads/dvd");
				
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				
				Files.copy(request.getImage().getInputStream(),
						   uploadPath.resolve(filename),
						   StandardCopyOption.REPLACE_EXISTING);
				
				dvd.setImagePath("/uploads/dvd/" + filename);
				logger.debug("Image uploaded for new DVD: {}", filename);
				
				} catch (IOException e) {
				logger.error("No image uploaded for DVD: {}", request.getTitle());
			}
		} else {
			logger.debug("No image uploaded for DVD: {}", request.getTitle());
		}
		
		dvdRepo.save(dvd);
		logger.debug("DVD created with id: {}", dvd.getId());
		
		var inventory = new Inventory();
		inventory.setDvd(dvd);
		inventory.setQuantity(request.getQty());
		
		inventoryRepo.save(inventory);
		logger.info("DVD registered successfully: {}", request.getTitle());
		
		return true;
	}

	@Override
	public List<Dvd> getAllDvds() {
		
		logger.info("Retrieving all DVDs");
		
		List<Dvd> dvds = dvdRepo.findAll();
		logger.debug("Retrieved {} DVDs", dvds.size());
		
		return dvds;
	}

	@Override
	public Optional<Dvd> getDvdById(Long id) {
		
		logger.info("Retrieving DVD with id: {}", id);
		
		Optional<Dvd> dvd = dvdRepo.findById(id);
		
		if (dvd.isEmpty()) {
			logger.warn("DVD not found with id: {}", id);
		}
		
		return dvd;
	}
	
	@Override
	public Optional<DvdRequest> getDvdRequestById(Long id) {
		
		logger.info("Retrieving DVD as editable request with id: {}", id);
		
		return dvdRepo.findById(id).map(dvd -> {
			DvdRequest dvdRequest = new DvdRequest();
			dvdRequest.setTitle(dvd.getTitle());
			dvdRequest.setDescription(dvd.getDescription());
			dvdRequest.setBonusMaterials(dvd.getBonusMaterials());
			dvdRequest.setNumDiscs(dvd.getNumDiscs());
			dvdRequest.setRuntime(dvd.getRuntime());
			dvdRequest.setSpecifications(dvd.getSpecifications());
			dvdRequest.setReleaseDate(dvd.getReleaseDate());
			dvdRequest.setArrivalDate(dvd.getArrivalDate());
			dvdRequest.setPrice(dvd.getPrice());
			dvdRequest.setGenreIds(dvd.getGenres().stream()
					.map(Genre::getId)
					.collect(Collectors.toSet()));
			if (dvd.getInventory() != null) {
				dvdRequest.setQty(dvd.getInventory().getQuantity());
			}
			
			return dvdRequest;
		});
	}

	@Override
	public boolean updateDvd(Long id, DvdRequest request) {
		
		logger.info("Attempting to update DVD with id: {}", id);
		Optional<Dvd> existingDvd = dvdRepo.findById(id);
		if (existingDvd.isEmpty()) {
			logger.warn("Update failed - DVD not found with id: {}", id);
			return false;
		}
		
		Dvd dvd = existingDvd.get();
		setDvdDetails(request, dvd);
		dvd.setGenres(resolveGenres(request.getGenreIds()));
		
		if (request.getImage() != null && !request.getImage().isEmpty()) {
			try {
				String filename = UUID.randomUUID() + "_" + request.getImage().getOriginalFilename();
				Path uploadPath = Paths.get("src/main/resources/static/uploads/dvd");
				
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				
				Files.copy(request.getImage().getInputStream(),
						   uploadPath.resolve(filename),
						   StandardCopyOption.REPLACE_EXISTING);
				
				if (dvd.getImagePath() != null && !dvd.getImagePath().isBlank()) {
					Path oldImagePath = Paths.get("src/main/resources/static" + dvd.getImagePath());
					Files.deleteIfExists(oldImagePath);
					logger.debug("Deleted old image file for DVD id: {}", id);
				}
				
				dvd.setImagePath("/uploads/dvd/" + filename);
				logger.debug("Image replaced for DVD id: {}", id);
			} catch (IOException e) {
				logger.error("Failed to save new image for DVD id: {}", id, e);
			}
		}
		
		dvdRepo.save(dvd);
		
		Inventory inventory = dvd.getInventory();
		if (inventory != null) {
			inventory.setQuantity(request.getQty());
			inventoryRepo.save(inventory);
			logger.debug("Inventory updated for DVD id: {}", id);
		}
		
		logger.info("DVD updated successfully with id: {}", id);
		
		return true;
	}

	@Override
	public boolean deleteDvd(Long id) {
		
		logger.info("Attempting to delete DVD with id: {}", id);
		
		Optional<Dvd> existingDvd = dvdRepo.findById(id);
		if (existingDvd.isEmpty()) {
			logger.warn("Delete failed - DVD not found with id: {}", id);
			return false;
		}
		
		Dvd dvd = existingDvd.get();
		
		if (dvd.getImagePath() != null && !dvd.getImagePath().isBlank()) {
			try {
				Path imagePath = Paths.get("src/main/resources/static" + dvd.getImagePath());
				logger.debug("Attempting to delete image at absolute path: {}", imagePath.toAbsolutePath());
				
				boolean deleted = Files.deleteIfExists(imagePath);
				
				if (deleted) {
					logger.debug("Deleted image file for DVD id: {}", id);
				} else {
					logger.warn("Image file not found at path for DVD id: {} - path: {}", id, imagePath.toAbsolutePath());
				}
				
			} catch (IOException e) {
				logger.error("Failed to delete image file for DVD id: {}", id, e);
			}
		}
		
		dvdRepo.delete(dvd);
		logger.info("DVD and associated inventory deleted successfully with id: {}", id);
		
		return true;
	}
	
	/**
	 * Helper method to populate a Dvd object with new values from the request.
	 * @param request
	 * @param dvd
	 */
	private void setDvdDetails(DvdRequest request, Dvd dvd) {
		
		dvd.setTitle(request.getTitle());
		dvd.setDescription(request.getDescription());
		dvd.setBonusMaterials(request.getBonusMaterials());
		dvd.setNumDiscs(request.getNumDiscs());
		dvd.setRuntime(request.getRuntime());
		dvd.setSpecifications(request.getSpecifications());
		dvd.setReleaseDate(request.getReleaseDate());
		dvd.setArrivalDate(request.getArrivalDate());
		dvd.setPrice(request.getPrice());	
	}
	
	/**
	 * Helper method to resolve a set of Genre IDs into Genre entities
	 * @param genreIds
	 * @return
	 */
	private Set<Genre> resolveGenres(Set<Integer> genreIds) {
		
		return genreIds.stream()
				.map(genreId -> genreRepo.findById(genreId)
						.orElseThrow(() -> new RuntimeException("Genre not found with id: " + genreId)))
				.collect(Collectors.toSet());
	}

}
