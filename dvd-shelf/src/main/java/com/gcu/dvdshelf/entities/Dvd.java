package com.gcu.dvdshelf.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dvds")
public class Dvd {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "bonus_materials")
	private String bonusMaterials;
	
	@Column(name = "num_discs")
	private int numDiscs;
	
	@Column(name = "runtime")
	private String runtime;
	
	@Column(name = "specifications")
	private String specifications;
	
	@Column(name = "release_date")
	private LocalDate releaseDate;
	
	@Column(name = "arrival_date")
	private LocalDate arrivalDate;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@OneToOne(mappedBy = "dvd", cascade = CascadeType.REMOVE)
	private Inventory inventory;
	
	@ManyToMany
	@JoinTable(name= "dvds_genres",
			   joinColumns = @JoinColumn(name = "dvd_id"),
			   inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private Set<Genre> genres = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}
}
