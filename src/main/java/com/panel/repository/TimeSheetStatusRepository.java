package com.panel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.TimeSheetStatus;

public interface TimeSheetStatusRepository extends MongoRepository<TimeSheetStatus, String>{

	public TimeSheetStatus findByStatus(String status);
	
}
