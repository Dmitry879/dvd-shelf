package com.gcu.dvdshelf.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gcu.dvdshelf.entities.Account;
import com.gcu.dvdshelf.repositories.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private final static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	private final AccountRepository repository;

	public CustomUserDetailsService(AccountRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 
		Account account = repository.findByUsername(username)
				.orElseThrow(() -> 
						new UsernameNotFoundException("User not found."));
		logger.info("User '{}' found with role {} - proceeding to authentication",
					username,
					account.getRole().getName());
		
		return User.builder()
				.username(account.getUsername())
				.password(account.getPassword())
				.roles(account.getRole().getName())
				.build();
	}
}
