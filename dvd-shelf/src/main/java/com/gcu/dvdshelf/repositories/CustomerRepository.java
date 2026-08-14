package com.gcu.dvdshelf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.dvdshelf.entities.Account;
import com.gcu.dvdshelf.entities.Customer;

/**
 * Repository interface for performing CRUD operations on Employee entities
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	/**
	 * Look up customer record linked to a given Account
	 * @param account
	 * @return
	 */
	Optional<Customer> findByAccount(Account account);

}
