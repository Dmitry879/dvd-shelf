package com.gcu.dvdshelf.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object used to capture and validate input from the employee registration form.
 */
public class EmployeeRequest {
	
	@NotBlank(message = "Username is required.")
	@Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters.")
	private String username;
	
	@NotBlank(message = "Password is required.")
	@Size(min = 8, max = 255, message = "Password must be at least 8 characters long.")
	private String password;
	
	@NotBlank(message = "Please confirm your password.")
	private String confirmPassword;
	
	@NotBlank(message = "First name is required.")
	@Size(max = 45, message = "First name cannot exceed 50 characters.")
	private String firstName;
	
	@NotBlank(message = "Last name is required.")
	@Size(max = 50, message = "Last name cannot exceed 50 charactes.")
	private String lastName;
	
	@NotNull(message = "Date of birth is required.")
	@Past(message = "Date of birth must be in the past.")
	private LocalDate dob;
	
	@NotBlank(message = "Phone number is required.")
	@Pattern(regexp = "^\\+?[0-9()\\-\\s]{10,20}$",
	         message = "Please enter a valid phone number.")
	private String phone;
	
	
	@NotBlank(message = "Email address is required.")
	@Email(message = "Please, enter a valid email address.")
	@Size(max = 50, message = "Email address cannot exceed 50 characters.")
	private String email;

	@NotBlank(message = "Address is required.")
	@Size(max = 500)
	private String address;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

}
