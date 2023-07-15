package com.panel.service;

import java.util.LinkedHashMap;

import com.panel.dto.EmployeeDto;
import com.panel.model.Role;


public interface EmployeeService {

	public LinkedHashMap<String, Object> signUp(EmployeeDto employeeDto);
	public LinkedHashMap<String, Object>  login(String employeeName, String password) ;
	public LinkedHashMap<String, Object> getRoleList();
	public LinkedHashMap<String, Object> getEmployeeList();
	public LinkedHashMap<String, Object> saveOrUpdateRole(Role role);
	
	
}
