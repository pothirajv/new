package com.panel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.TimeLogStatus;

public interface TimeLogStatusRepository extends MongoRepository<TimeLogStatus, String>{

	TimeLogStatus findByStatus(String string);
	
}
