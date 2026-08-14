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

import com.gcu.dvdshelf.dtos.EmployeeRequest;
import com.gcu.dvdshelf.entities.Employee;
import com.gcu.dvdshelf.services.EmployeeService;

import jakarta.validation.Valid;

/**
 * Controller responsible for handling requests related to managing
 * Employee records.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	private final EmployeeService emplService;

	public EmployeeController(EmployeeService emplService) {
		super();
		this.emplService = emplService;
	}
	
	@GetMapping
	public String listEmployees(Model model) {
		
		logger.info("Listing all employees");
		model.addAttribute("employees", emplService.getAllEmployees());
		
		return "employee/list";
	}
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		
		logger.info("Displaying employee registration form");
		model.addAttribute("employeeRequest", new EmployeeRequest());
		
		return "employee/register";
	}
	
	@PostMapping("/register")
	public String registerEmployee(@Valid @ModelAttribute("employeeRequest") EmployeeRequest request,
								   BindingResult result,
								   Model model) {
		
		logger.info("Received registration submission for username: {}", request.getUsername());
		
		if (result.hasErrors()) {
			logger.warn("Registration form validation failed for username: {}", request.getUsername());
			return "employee/register";
		}
		
		boolean success = emplService.registerEmployee(request);
		if (!success) {
			logger.warn("Registration rejected - username already exists: {}", request.getUsername());
			model.addAttribute("error", "Username already exists.");
			return "employee/register";
		}
		
		logger.info("Employee registered successfully via controller: {}", request.getUsername());
		return "redirect:/employee";
	}
	
	@GetMapping("/{id}")
	public String viewEmployee(@PathVariable Long id, Model model) {
		
		logger.info("Viewing employee with id: {}", id);
		
		Employee employee = emplService.getEmployeeById(id)
				.orElseThrow(() -> {
					logger.error("Employee not found with id: {}", id);
					return new RuntimeException("Employee not found with id: " + id);
				});
		
		model.addAttribute("employee", employee);
		
		return "employee/view";
	}
	
	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable Long id, Model model) {
		
		logger.info("Displaying edit form for employee id: {}", id);
		
		Employee employee = emplService.getEmployeeById(id)
				.orElseThrow(() -> {
					logger.error("Employee not found with id: {}", id);
					return new RuntimeException("Employee not found with id: " + id);
				});
		
		model.addAttribute("employeeRequest", employee);
		model.addAttribute("employeeId", id);
		
		return "employee/edit";
	}
	
	@PostMapping("/{id}/edit")
	public String updateEmployee(@PathVariable Long id,
								 @Valid @ModelAttribute("employeeRequest") EmployeeRequest request,
								 BindingResult result) {
		
		logger.info("Received update submission for employee id: {}", id);
		
		if (result.hasErrors()) {
			logger.warn("Update form validation failed for employee id: {}", id);
			return "employee/edit";
		}
		
		boolean success = emplService.updateEmployee(id, request);
		
		if (!success) {
			logger.warn("Update failed - employee not found with id: {}", id);
		} else {
			logger.info("Employee updated successfully via controler: {}", id);
		}
		
		return "redirect:/employee/" + id;
		
	}
	
	@PostMapping("/{id}/delete")
	public String deleteEmployee(@PathVariable Long id) {
		
		logger.info("Received delete request for employee id: {}", id);
		
		boolean success = emplService.deleteEmployee(id);
		
		if (!success) {
			logger.warn("Delete failed - employee not found with id: {}", id);
		} else {
			logger.info("Employee deleted successfully via controler: {}", id);
		}
		
		return "redirect:/employee";
	}

}
