package com.gcu.dvdshelf.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.dvdshelf.dtos.DvdRequest;
import com.gcu.dvdshelf.services.DvdService;
import com.gcu.dvdshelf.services.GenreService;

import com.gcu.dvdshelf.entities.Dvd;

import jakarta.validation.Valid;

/**
 * Controller responsible for handling requests related to managing DVD records.
 */
@Controller
@RequestMapping("/dvd")
public class DvdController {
	
	private static final Logger logger = LoggerFactory.getLogger(DvdController.class);
	
	private final DvdService dvdService;
	private final GenreService genreService;
	
	public DvdController(DvdService dvdService, GenreService genreService) {
		super();
		this.dvdService = dvdService;
		this.genreService = genreService;
	}
	
	@GetMapping
	public String listDvds(Model model) {
		
		logger.info("Listing all DVDs");
		model.addAttribute("dvds", dvdService.getAllDvds());
		return "dvd/list";
	}
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		
		logger.info("Displaying DVD registration form");
		model.addAttribute("dvdRequest", new DvdRequest());
		model.addAttribute("allGenres", genreService.getAllGenres());
		return "dvd/register";
	}
	
	@PostMapping("/register")
	public String registerDvd(@Valid @ModelAttribute("dvdRequest") DvdRequest request,
							  BindingResult result,
							  Model model) {
		
		logger.info("Received registraton submission for DVD title: {}", request.getTitle());
		
		if (result.hasErrors()) {
			logger.warn("Registration form validation failed for title: {}", request.getTitle());
			model.addAttribute("allGenres", genreService.getAllGenres());
			return "dvd/register";
		}
		
		boolean success = dvdService.registerDvd(request);
		
		if (!success) {
			logger.warn("Registration failed for title: {}", request.getTitle());
			model.addAttribute("error", "Unable to register DVD.");
			return "dvd/register";
		}
		
		logger.info("DVD registered successfully via controller: {}", request.getTitle());
		
		return "redirect:/dvd";
	}
	
	@GetMapping("/{id}")
	public String viewDvd(@PathVariable Long id, Model model) {
		
		logger.info("Viewing DVD with id: {}", id);
		
		Dvd dvd = dvdService.getDvdById(id)
				.orElseThrow(() -> {
					logger.error("DVD not found with id: {}", id);
					return new RuntimeException("DVD not found with id: " + id);
				});
		
		model.addAttribute("dvd", dvd);
		return "dvd/view";
	}
	
	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable Long id, Model model) {
		
		logger.info("Displaying edit form for DVD id: {}", id);
		
		DvdRequest dvdRequest = dvdService.getDvdRequestById(id)
				.orElseThrow(() -> {
					logger.error("DVD not found with id: {}", id);
					return new RuntimeException("DVD not found with id: " + id);
				});
		
		model.addAttribute("dvdRequest", dvdRequest);
		model.addAttribute("dvdId", id);
		model.addAttribute("allGenres", genreService.getAllGenres());
		
		logger.debug("DvdRequest releaseDate: {}, arrivaleDate: {}", dvdRequest.getReleaseDate(), dvdRequest.getArrivalDate());
		
		return "dvd/edit";
	}
	
	@PostMapping("/{id}/edit")
	public String updateDvd(@PathVariable Long id,
							@Valid @ModelAttribute("dvdRequest") DvdRequest request,
							BindingResult result,
							Model model) {
		
		logger.info("Received update submission for DVD id: {}", id);
		
		if (result.hasErrors()) {
			logger.warn("Update form validation failed for DVD id: {}", id);
			model.addAttribute("allGenres", genreService.getAllGenres());
			return "dvd/edit";
		}
		
		boolean success = dvdService.updateDvd(id, request);
		
		if (!success) {
			logger.warn("Update failed - DVD not found with id: {}", id);
		} else {
			logger.info("DVD updated successfully via controller: {}", id);
		}
		
		return "redirect:/dvd/" + id;
	}
	
	@PostMapping("/{id}/delete")
	public String deleteDvd(@PathVariable Long id) {
		
		logger.info("Received delete request for DVD id: {}", id);
		
		boolean success = dvdService.deleteDvd(id);
		
		if (!success) {
			logger.warn("Delete failed - DVD not found with id: {}", id);
		} else {
			logger.info("DVD deleted successfully via controller: {}", id);
		}
		
		return "redirect:/dvd";
	}
}
