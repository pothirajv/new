package com.panel.service;

import java.util.LinkedHashMap;

import com.panel.dto.AssignedResourcesEmpTypeDto;
import com.panel.dto.EmploymentTypesDto;


public interface EmploymentTypeService {


	public LinkedHashMap<String, Object> saveOrUpdateEmploymentTypes(EmploymentTypesDto employmentTypesDto);
	public LinkedHashMap<String, Object> getEmploymenttypesList();
	public LinkedHashMap<String, Object> getUpdateEmploymenttypesList();
	public LinkedHashMap<String, Object> getEmploymentTypesById(String id);
	public LinkedHashMap<String, Object> getAllEmploymentTypesList();
	public LinkedHashMap<String, Object> getEmploymentTypesFilter(EmploymentTypesDto employmentTypesDto);
	public LinkedHashMap<String, Object> saveOrUpdateAssignedResources(AssignedResourcesEmpTypeDto assignedResourcesDto);
	public LinkedHashMap<String, Object> getAllAssignedResourcesList();
	public LinkedHashMap<String, Object> getAllUpdateEmploymentTypesList(String id);
	public LinkedHashMap<String, Object> getUpdateEmploymentTypesById(String id);
	public LinkedHashMap<String, Object> getAssignedResourcesList(String id);
}
