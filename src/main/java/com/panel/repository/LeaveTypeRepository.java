package com.panel.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.LeaveType;


public interface LeaveTypeRepository extends MongoRepository<LeaveType, String>{

	

}
