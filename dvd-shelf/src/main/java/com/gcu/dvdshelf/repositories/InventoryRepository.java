package com.gcu.dvdshelf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcu.dvdshelf.entities.Inventory;

/**
 * Repository interface for performing CRUD operations on Genre entities
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
