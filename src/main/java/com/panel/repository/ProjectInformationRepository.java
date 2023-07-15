package com.panel.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.ProjectInformation;


public interface ProjectInformationRepository extends MongoRepository<ProjectInformation, String>{

	List<ProjectInformation> findByProjectName(String projectName);

	List<ProjectInformation> findByBusinessUnitId(String id);
	
	List<ProjectInformation> findByMasterDepartmentId(String id);
	
	List<ProjectInformation> findByProjectManager(String id);
	
}
