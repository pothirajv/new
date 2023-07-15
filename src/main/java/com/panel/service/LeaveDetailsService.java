package com.panel.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import com.panel.dto.LeaveDetailsDto;
import com.panel.dto.LeaveDetailsFilterdto;
import com.panel.dto.LeaveDetailsListDto;

public interface LeaveDetailsService {

	public LinkedHashMap<String, Object> saveAndUpdateLeaveDetails(LeaveDetailsDto leaveDetailsDto);
	
//	public LinkedHashMap<String, Object> getEmployeesByEmployeeName(String employeeName);
	
//	public LinkedHashMap<String, Object> getEmployeesByDepartment(String department);
	
//	public LinkedHashMap<String, Object> getEmployeesByRemainingLeave(int remainingLeave);
	
	public LinkedHashMap<String, Object> getLeaveApplicationById(String id);
	
	public LinkedHashMap<String, Object> getEmployeeLeaves(String id);
	
	public LinkedHashMap<String, Object> getAllEmployeeLeaves(String id);
	
	public LinkedHashMap<String, Object> getAllEmployeeLeavesList(String id);
	
	public LinkedHashMap<String, Object> getEmployeeById(String id);
	
	public LinkedHashMap<String, Object> getLeaveTypesList();
	
	public LinkedHashMap<String, Object> getLeaveStatusList();
	
//	public LinkedHashMap<String, Object> getAllLeaveDetails(LeaveDetailsFilterdto leaveDetailsFilterdto);
	
	public LinkedHashMap<String, Object> getLeaveDetailsList();
	
	public LinkedHashMap<String, Object> getLeaveStatus();
	
	public LinkedHashMap<String, Object> getLeaveDetailsBetweenDates(LocalDate fromDate, LocalDate toDate, String id);
	
	public LinkedHashMap<String, Object> getEmployeeLeavesByManager(String id);
	
	public LinkedHashMap<String, Object> getLopLeaveDetailsForEmployee(String id);
	
	public LinkedHashMap<String, Object> getLopDetailsForAllEmployees(LocalDate fromDate, LocalDate todate);
	
	public LinkedHashMap<String, Object> getLeaveDetailsForBusinessUnit(String id);
	
	public LinkedHashMap<String, Object> getLeaveDetailsForDepartmentUnit(String id);
}
