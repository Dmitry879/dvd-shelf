package com.gcu.dvdshelf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.dvdshelf.entities.Account;

/**
 * Repository interface for performing CRUD operations on Account entities.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	/**
	 * Look up user by username
	 * @param username
	 * @return
	 */
	Optional<Account> findByUsername(String username);
	
	/**
	 * Check if username exists during registration
	 * @param username
	 * @return
	 */
	boolean existsByUsername(String username);

}
