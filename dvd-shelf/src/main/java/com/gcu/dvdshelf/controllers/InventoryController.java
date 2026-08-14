package com.gcu.dvdshelf.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.dvdshelf.entities.Inventory;
import com.gcu.dvdshelf.repositories.InventoryRepository;

/**
 * Controller responsible for handling requests related to viewing DVD inventory
 */
@Controller
@RequestMapping("/inventory")
public class InventoryController {
	
	private final Logger logger = LoggerFactory.getLogger(InventoryController.class);
	
	private final InventoryRepository invRepo;
	
	public InventoryController(InventoryRepository invRepo) {
		super();
		this.invRepo = invRepo;
	}



	@GetMapping
	public String showInventory(Model model) {
		
		logger.info("Listing all inventory records");
		
		List<Inventory> invList = invRepo.findAll();
		logger.debug("Retrived {} inventory records", invList.size() );
		
		model.addAttribute("invList", invList);
		
		return "inventory/list";
	}

}
