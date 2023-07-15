package com.panel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.ActivityCode;

public interface ActivityCodeRepository extends MongoRepository<ActivityCode, String> {
	
	Optional<ActivityCode> getActivityCodeById(String id);

	List<ActivityCode> findByProjectId(String id);

}
