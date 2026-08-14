package com.gcu.dvdshelf.services;

import java.util.List;
import java.util.Optional;

import com.gcu.dvdshelf.dtos.CustomerRequest;
import com.gcu.dvdshelf.entities.Customer;

/**
 * Service interface defining business operations for customer records
 */
public interface CustomerService {
	
	public boolean registerCustomer(CustomerRequest request);
	
	public List<Customer> getAllCustomers();
	
	public Optional<Customer> getCustomerById(Long id);
	
	public boolean updateCustomer(Long id, CustomerRequest request);
	
	public boolean deleteCustomer(Long id);

}
