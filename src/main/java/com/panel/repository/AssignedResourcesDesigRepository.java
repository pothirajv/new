package com.panel.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.AssignedResourceDesig;


public interface AssignedResourcesDesigRepository extends MongoRepository<AssignedResourceDesig, String>{
	

}
