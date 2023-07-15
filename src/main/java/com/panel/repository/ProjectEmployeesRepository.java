package com.panel.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.ProjectEmployee;

public interface ProjectEmployeesRepository extends MongoRepository<ProjectEmployee, String> {

	List<ProjectEmployee> findByProjectId(String projectId);

	List<ProjectEmployee> findByProjectIdAndEmployeeId(String projectId, String employeeId);
}
