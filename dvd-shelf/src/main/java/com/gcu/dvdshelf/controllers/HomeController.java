package com.gcu.dvdshelf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
/**
 * Controller for handling requests related to home page.
 */
public class HomeController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

}
