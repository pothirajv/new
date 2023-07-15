package com.panel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.AssignActivityCodeToProject;

public interface AssignActivityCodeToProjectRepository extends MongoRepository<AssignActivityCodeToProject, String> {

}
