package com.panel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.UpdateProject;

public interface UpdateProjectRepository extends MongoRepository<UpdateProject, String>{

}
