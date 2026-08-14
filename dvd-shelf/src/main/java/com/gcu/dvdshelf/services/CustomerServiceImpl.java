package com.gcu.dvdshelf.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gcu.dvdshelf.dtos.CustomerRequest;
import com.gcu.dvdshelf.entities.Account;
import com.gcu.dvdshelf.entities.Customer;
import com.gcu.dvdshelf.entities.Role;
import com.gcu.dvdshelf.repositories.AccountRepository;
import com.gcu.dvdshelf.repositories.CustomerRepository;
import com.gcu.dvdshelf.repositories.RoleRepository;

/**
 * Implementation of the CustomerService interface, providing the concrete
 * business logic for registering, retrieving, updating, and deleting
 * Customer records
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	
private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	private final CustomerRepository customerRepo;
	private final AccountRepository accRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder encoder;
	
	

	public CustomerServiceImpl(CustomerRepository customerRepo,
							   AccountRepository accRepo,
							   RoleRepository roleRepo,
							   PasswordEncoder encoder) {
		super();
		this.customerRepo = customerRepo;
		this.accRepo = accRepo;
		this.roleRepo = roleRepo;
		this.encoder = encoder;
	}

	@Override
	public boolean registerCustomer(CustomerRequest request) {
		
		logger.info("Attempting to register customer with username: {}", request.getUsername());
		
		if (accRepo.findByUsername(request.getUsername()).isPresent()) {
			logger.warn("Registration failed - username already exists: {}", request.getUsername());
			return false;
		}
		
		var account = new Account();
		account.setUsername(request.getUsername());
		account.setPassword(encoder.encode(request.getPassword()));
		account.setEmail(request.getEmail());
		
		Role role = roleRepo.findByName("CUSTOMER")
				.orElseThrow(() -> {
					logger.error("Role 'CUSTOMER' not found in database");
					return new RuntimeException("Role CUSTOMER not found.");
				});
		
		account.setRole(role);
		
		accRepo.save(account);
		logger.debug("Account created with id: {}", account.getId());
		
		var customer = new Customer();
		
		customer.setAccount(account);
		
		setCustomerDetails(request, customer);
		
		customerRepo.save(customer);
		logger.info("Customer registered successfully: {} {}",
					request.getFirstName(),
					request.getLastName());
		
		return true;
	}

	@Override
	public List<Customer> getAllCustomers() {
		
		logger.info("Retrieving all customers");
		
		List<Customer> customers = customerRepo.findAll();
		logger.debug("Retrieved {} customers", customers.size());
		
		return customers;
	}

	@Override
	public Optional<Customer> getCustomerById(Long id) {
		
		logger.info("Retrieving customer with id: {}", id);
		
		Optional<Customer> customer = customerRepo.findById(id);
		
		if (customer.isEmpty()) {
			logger.warn("Customer not found with id: {}", id);
		}
		
		return customer;
	}

	@Override
	public boolean updateCustomer(Long id, CustomerRequest request) {
		
		logger.info("Attempting to update customer with id: {}", id);
		
		Optional<Customer> existingCustomer = customerRepo.findById(id);
		if (existingCustomer.isEmpty()) {
			logger.warn("Update failed - customer not found with id: {}", id);
			return false;
		}
		
		Customer customer = existingCustomer.get();
		setCustomerDetails(request, customer);
		
		Account account = customer.getAccount();
		account.setEmail(request.getEmail());
		
		// Only update password if a new one was provided
		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			account.setPassword(encoder.encode(request.getPassword()));
			logger.debug("Password updated for account id: {}", id);
		}
		
		accRepo.save(account);
		customerRepo.save(customer);
		
		logger.info("Customer updated successfully with id: {}", id);
		
		return true;
	}

	@Override
	public boolean deleteCustomer(Long id) {
		
		logger.info("Attempting to delete customer with id: {}", id);
		Optional<Customer> existingCustomer = customerRepo.findById(id);
		
		if (existingCustomer.isEmpty()) {
			logger.warn("Delete failed - customer not found with id: {}", id);
			return false;
		}
		
		customerRepo.delete(existingCustomer.get());
		logger.info("Customer and associated account deleted successfully with id: {}", id);
		
		return true;
	}
	
	/**
	 * Helper method to populated Customer object with new values
	 * @param request
	 * @param customer
	 */
	private void setCustomerDetails(CustomerRequest request, Customer customer) {
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setDob(request.getDob());
		customer.setPhone(request.getPhone());
		customer.setAddress(request.getAddress());
	}
}
