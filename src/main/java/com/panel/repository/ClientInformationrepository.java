package com.panel.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.ClientInformation;


public interface ClientInformationrepository extends MongoRepository<ClientInformation, String>{
	
	

//	List<ClientInformation> findByCustomerName(String customerName);
	
	Optional<ClientInformation> getClientInformationById(String id);
	
	
}
