package com.panel.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.LOPLeaveDetails;

public interface LOPLeaveDetailsRepository extends MongoRepository<LOPLeaveDetails, String>{

	LOPLeaveDetails findByLeaveDetailsId(String id);

	List<LOPLeaveDetails> findByEmployeeAndStatus(String id, boolean status);

}
