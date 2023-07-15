package com.panel.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.Designations;



public interface DesignationsRepository extends MongoRepository<Designations, String>{

	Optional<Designations> findOneById(String id);

	

}
