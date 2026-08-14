package com.gcu.dvdshelf.services;

import java.util.List;
import java.util.Optional;

import com.gcu.dvdshelf.dtos.DvdRequest;
import com.gcu.dvdshelf.entities.Dvd;

/**
 * Service interface defining business operations for DVD records
 */
public interface DvdService {
	
	public boolean registerDvd(DvdRequest request);
	
	public List<Dvd> getAllDvds();
	
	public Optional<Dvd> getDvdById(Long id);
	
	public Optional<DvdRequest> getDvdRequestById(Long id);
	
	public boolean updateDvd(Long id, DvdRequest request);
	
	public boolean deleteDvd(Long id);

}
