package com.panel.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.MartialStatus;


public interface MartialStatusRepository extends MongoRepository<MartialStatus, String>{

	

}
