package com.gcu.dvdshelf.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object to capture and validate input from DVD registration form.
 */
public class DvdRequest {
	
	@NotBlank(message = "Title is required.")
	@Size(max = 255)
	private String title;
	
	@NotEmpty(message = "At least one genre must be selected.")
	private Set<Integer> genreIds = new HashSet<>();
	
	@NotBlank(message = "Description is required.")
	@Size(max = 2000)
	private String description;
	
	@Size(max = 3000)
	private String bonusMaterials;
	
	@Min(value = 1, message = "Number of discs must be at least 1.")
	private int numDiscs;
	
	@NotBlank(message = "Runtime is required.")
	@Size(max = 50)
	private String runtime;
	
	@NotBlank(message = "Specifications field is required.")
	@Size(max = 5000)
	private String specifications;
	
	@NotNull(message = "Release Date is required.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate releaseDate;
	
	@NotNull(message = "Arrival Date is required.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate arrivalDate;
	
	@NotNull(message = "Price is required.")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
	private BigDecimal price;
	
	@Min(value = 1, message = "Quantity must be at least 1.")
	private int qty;
	
	private MultipartFile image;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Integer> getGenreIds() {
		return genreIds;
	}

	public void setGenreIds(Set<Integer> genreIds) {
		this.genreIds = genreIds;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBonusMaterials() {
		return bonusMaterials;
	}

	public void setBonusMaterials(String bonusMaterials) {
		this.bonusMaterials = bonusMaterials;
	}

	public int getNumDiscs() {
		return numDiscs;
	}

	public void setNumDiscs(int numDiscs) {
		this.numDiscs = numDiscs;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
}
