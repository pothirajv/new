package com.panel.service;


import java.util.LinkedHashMap;
import java.util.List;

import com.panel.dto.ProjectAssignDto;
import com.panel.dto.ProjectEmployeeDto;
import com.panel.dto.ProjectInformationDto;
import com.panel.dto.ProjectInformationFilterDto;
import com.panel.dto.ProjectInformationResponseDto;
import com.panel.model.ProjectEmployee;
import com.panel.model.ProjectInformation;

public interface ProjectInformationService {
	
	public LinkedHashMap<String, Object> SaveAndUpdateProjectinfo(ProjectInformationDto projectinformationDto);

	//public List<ProjectInformation> findByProjectName(String projectName);
	
	public List<ProjectInformation> getAllProjectInformationDetails(ProjectInformationFilterDto projectInformationFilterDto);

	public LinkedHashMap<String, Object> getallProjectDetails(String id);
	
	public LinkedHashMap<String, Object> getProjectById(String id);
	
	public LinkedHashMap<String, Object> assignEmployeesToProject(ProjectAssignDto projectAssignDto);
	
	public LinkedHashMap<String, Object> getProjectEmployeeUpdateList(String id);
	
	public LinkedHashMap<String, Object> getProjectByEmployee(String id);

	//public String[] getNullPropertyNames(Object source);
		
	public LinkedHashMap<String, Object> getProjectUpdateDetails(String id);
	
	public LinkedHashMap<String, Object> getAllProjectNames();
	
	public LinkedHashMap<String, Object> getUnAssignedEmployeesForProject(String empId, String id);
	
	public List<ProjectInformation> getProjectByBusinessUnit(String id);
	
	public List<ProjectInformation> getProjectByDepartment(String id);
	
	public List<ProjectInformation> getProjectByProjectManager(String id);
	
	public LinkedHashMap<String, Object> removeResourceFromProject(String empId, String projectId);
	
	}


	
