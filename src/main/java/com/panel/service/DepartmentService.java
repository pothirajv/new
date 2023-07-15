package com.panel.service;

import java.util.LinkedHashMap;

import com.panel.dto.DepartmentDto;


public interface DepartmentService {

	public LinkedHashMap<String, Object> saveOrUpdateDepartment(DepartmentDto departmentDto);
	public LinkedHashMap<String, Object> getDepartmentList();
	public LinkedHashMap<String, Object> getUpdatedDepartmentList();
	public LinkedHashMap<String, Object> getDepartmentById(String id);
	public LinkedHashMap<String, Object> getAllDepartmentsList() ;
	public LinkedHashMap<String, Object> getDepartmentFilter(DepartmentDto departmentDto);
	
}
