package com.gcu.dvdshelf.services;

import java.util.List;
import java.util.Optional;

import com.gcu.dvdshelf.dtos.EmployeeRequest;
import com.gcu.dvdshelf.entities.Employee;

/**
 * Service interface defining business operations for managing employee records.
 */
public interface EmployeeService {
	
	public boolean registerEmployee(EmployeeRequest request);
	
	public List<Employee> getAllEmployees();
	
	public Optional<Employee> getEmployeeById(Long id);
	
	public boolean updateEmployee(Long id, EmployeeRequest request);
	
	public boolean deleteEmployee(Long id);

}
