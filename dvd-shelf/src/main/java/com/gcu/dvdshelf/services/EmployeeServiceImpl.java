package com.gcu.dvdshelf.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gcu.dvdshelf.dtos.EmployeeRequest;
import com.gcu.dvdshelf.entities.Account;
import com.gcu.dvdshelf.entities.Employee;
import com.gcu.dvdshelf.entities.Role;
import com.gcu.dvdshelf.repositories.AccountRepository;
import com.gcu.dvdshelf.repositories.EmployeeRepository;
import com.gcu.dvdshelf.repositories.RoleRepository;

/**
 * Implementation of the EmployeeService interface, providing the concrete
 * business logic for registering, retrieving, updating, and deleting
 * Employee records.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	private final EmployeeRepository emplRepo;
	private final AccountRepository accRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder encoder;
	
	

	public EmployeeServiceImpl(EmployeeRepository emplRepo,
							   AccountRepository accRepo,
							   RoleRepository roleRepo,
							   PasswordEncoder encoder) {
		super();
		this.emplRepo = emplRepo;
		this.accRepo = accRepo;
		this.roleRepo = roleRepo;
		this.encoder = encoder;
	}

	@Override
	public boolean registerEmployee(EmployeeRequest request) {
		
		logger.info("Attempting to register employee with username: {}", request.getUsername());
		
		if (accRepo.findByUsername(request.getUsername()).isPresent()) {
			logger.warn("Registration failed - username already exists: {}", request.getUsername());
			return false;
		}
		
		var account = new Account();
		account.setUsername(request.getUsername());
		account.setPassword(encoder.encode(request.getPassword()));
		account.setEmail(request.getEmail());
		
		Role role = roleRepo.findByName("EMPLOYEE")
				.orElseThrow(() -> {
					logger.error("Role 'EMPLOYEE' not found in database");
					return new RuntimeException("Role EMPLOYEE not found.");
				});
		
		account.setRole(role);
		
		accRepo.save(account);
		logger.debug("Account created with id: {}", account.getId());
		
		var employee = new Employee();
		
		employee.setAccount(account);
		
		setEmployeeDetails(request, employee);
		
		emplRepo.save(employee);
		logger.info("Employee registered successfully: {} {}",
					request.getFirstName(),
					request.getLastName());
		
		return true;
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		logger.info("Retrieving all employees");
		
		List<Employee> employees = emplRepo.findAll();
		logger.debug("Retrieved {} employees", employees.size());
		
		return employees;
	}

	@Override
	public Optional<Employee> getEmployeeById(Long id) {
		
		logger.info("Retrieving employee with id: {}", id);
		
		Optional<Employee> employee = emplRepo.findById(id);
		
		if (employee.isEmpty()) {
			logger.warn("Employee not found with id: {}", id);
		}
		
		return employee;
	}

	@Override
	public boolean updateEmployee(Long id, EmployeeRequest request) {
		
		logger.info("Attempting to update employee with id: {}", id);
		
		Optional<Employee> existingEmpl = emplRepo.findById(id);
		if (existingEmpl.isEmpty()) {
			logger.warn("Update failed - employee not found with id: {}", id);
			return false;
		}
		
		Employee employee = existingEmpl.get();
		setEmployeeDetails(request, employee);
		
		Account account = employee.getAccount();
		account.setEmail(request.getEmail());
		
		// Only update password if a new one was provided
		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			account.setPassword(encoder.encode(request.getPassword()));
			logger.debug("Password updated for account id: {}", id);
		}
		
		accRepo.save(account);
		emplRepo.save(employee);
		
		logger.info("Employee updated successfully with id: {}", id);
		
		return true;
	}

	@Override
	public boolean deleteEmployee(Long id) {
		
		logger.info("Attempting to delete employee with id: {}", id);
		Optional<Employee> existingEmpl = emplRepo.findById(id);
		
		if (existingEmpl.isEmpty()) {
			logger.warn("Delete failed - employee not found with id: {}", id);
			return false;
		}
		
		emplRepo.delete(existingEmpl.get());
		logger.info("Employee and associated account deleted successfully with id: {}", id);
		
		return true;
	}
	
	/**
	 * Helper method to populated an Employee object with new values
	 * @param request
	 * @param employee
	 */
	private void setEmployeeDetails(EmployeeRequest request, Employee employee) {
		employee.setFirstName(request.getFirstName());
		employee.setLastName(request.getLastName());
		employee.setDob(request.getDob());
		employee.setPhone(request.getPhone());
		employee.setAddress(request.getAddress());
	}
}
