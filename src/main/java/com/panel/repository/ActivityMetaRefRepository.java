package com.panel.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.ActivityMetaRef;

public interface ActivityMetaRefRepository extends MongoRepository<ActivityMetaRef, String>{

	List<ActivityMetaRef> findByParentActivityId(String id);

}
