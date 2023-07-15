package com.panel.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

import com.panel.dto.ChangePassword;
import com.panel.dto.EmployeeDetailsDto;
import com.panel.dto.EmployeeNameDto;
import com.panel.model.EmployeeDetails;
import com.panel.model.Role;

public interface EmployeeDetailsService {

	public LinkedHashMap<String, Object> saveAndUpdateEmployeeDetails(EmployeeDetailsDto employeeDetailsDto);

	public LinkedHashMap<String, Object> getEmployeeById(String id);

	public LinkedHashMap<String, Object> login(String employeeName, String password);
//	public LinkedHashMap<String, Object> getEmployeeList();
//	public LinkedHashMap<String, Object> getEmployeesByDepartment(String department);
//	public LinkedHashMap<String, Object> getEmployeesByEmployeeStatus(String employeeStatus);
	// public LinkedHashMap<String, Object> getEmployeesByEmployeeRole(String
	// employeeRole);
	// public LinkedHashMap<String, Object>
	// getAllEmployeeDetails(EmployeeDetailsFilterDto employeeDetailsFilterDto);

	public LinkedHashMap<String, Object> getAllEmployDetailsList();

	public LinkedHashMap<String, Object> getRoleList();

	public LinkedHashMap<String, Object> getAllGenderList();

	public LinkedHashMap<String, Object> getAllMartialStatusList();

	public LinkedHashMap<String, Object> getAllReportingPersonList(String id);

	public LinkedHashMap<String, Object> saveOrUpdateRole(Role role);

	public LinkedHashMap<String, Object> getAllEmployeeStatusList();

	public LinkedHashMap<String, Object> getAllEmployeeCategoryList();

	public LinkedHashMap<String, Object> getAllEmployeeNameList();

	public LinkedHashMap<String, Object> getAllManagersList();

	public LinkedHashMap<String, Object> getAllBloodGroup();

	public LinkedHashMap<String, Object> getAllProficiency();

	public LinkedHashMap<String, Object> getAllBoards();

	public LinkedHashMap<String, Object> changePassword(ChangePassword changePassword);

	public LinkedHashMap<String, Object> getEmployeesByManager(String id, String businessUnit, String department);

	public LinkedHashMap<String, Object> getMyAccount();
	
	public LinkedHashMap<String, Object> excelUpload(MultipartFile file) throws IOException;

	public UsernamePasswordAuthenticationToken validateLogin(String emailId, String password);
	
	public List<EmployeeDetails> getAllEmployeesByBusinessUnit(String id);
	
	public List<EmployeeDetails> getAllEmployeesByMasterDepartment(String id);
	
	public List<EmployeeDetails> getAllEmployeesByReportingPerson(String id);
	
	public List<EmployeeDetails> getEmployeesByBusinessUnitAndDepartment(String businessUnit, String department);
	
	public List<EmployeeNameDto> getEmployeesNameByBusinessUnit(String id, String projectId);

}
