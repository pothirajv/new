package com.panel.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.ParentActivity;

public interface ParentActivityRepository extends MongoRepository<ParentActivity, String>{

	List<ParentActivity> findByParentActivityIgnoreCase(String parentActivity);

}
