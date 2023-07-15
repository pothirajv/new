package com.panel.repository;



import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.EmploymentTypes;


public interface EmploymentTypeRepository extends MongoRepository<EmploymentTypes, String>{

	Optional<EmploymentTypes> findOneById(String id);


	

}
