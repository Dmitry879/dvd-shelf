package com.gcu.dvdshelf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.dvdshelf.entities.Account;
import com.gcu.dvdshelf.entities.Employee;

/**
 * Repository interface for performing CRUD operations on Employee entities 
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	/**
	 * Look up an employee record linked to a given Account
	 * @param account
	 * @return
	 */
	Optional<Employee> findByAccount(Account account);

}
