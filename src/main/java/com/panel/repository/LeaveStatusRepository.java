package com.panel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.LeaveStatus;

public interface LeaveStatusRepository extends MongoRepository<LeaveStatus, String>{

}
