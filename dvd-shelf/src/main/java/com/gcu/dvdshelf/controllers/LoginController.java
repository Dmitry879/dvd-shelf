package com.gcu.dvdshelf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for handling requests related to login page.
 */
@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
