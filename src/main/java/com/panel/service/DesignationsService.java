package com.panel.service;

import java.util.LinkedHashMap;

import com.panel.dto.AssignedResourcesDesigDto;
import com.panel.dto.Designationsdto;


public interface DesignationsService {

	public LinkedHashMap<String, Object> saveOrUpdateDesignations(Designationsdto designationsdto);	
	public LinkedHashMap<String, Object> getDesignationsList();
	public LinkedHashMap<String, Object> getUpdatedDesignationList();
	public LinkedHashMap<String, Object> getDesignationById(String id);
	public LinkedHashMap<String, Object> getAllDesignationsList() ;
	public LinkedHashMap<String, Object> getDesignationsFilter(Designationsdto designationsdto);
	public LinkedHashMap<String, Object> saveOrUpdateAssignedResources(AssignedResourcesDesigDto assignedResourcesDesigDto);
	public LinkedHashMap<String, Object> getAllAssignedResourcesList();
	public LinkedHashMap<String, Object> getAllUpdateDesignationsList(String id);
	public LinkedHashMap<String, Object> getUpdateDesignationsById(String id);
	public LinkedHashMap<String, Object> getAssignedResourcesList(String id);
}

