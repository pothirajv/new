package com.panel.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import com.panel.dto.TimeSheetDto;

public interface TimeSheetService {

	public LinkedHashMap<String, Object> SaveAndUpdateTimesheet(TimeSheetDto timeSheetDto);

	public LinkedHashMap<String, Object> getAllTimesheet();

	public LinkedHashMap<String, Object> getTimeSheetById(String id);

	public LinkedHashMap<String, Object> getAllEmployeeTimesheet(String id);

	public LinkedHashMap<String, Object> getClientByProject(String id);

	public LinkedHashMap<String, Object> getAllTimeSheetStatus();

	public LinkedHashMap<String, Object> getTimeSheetApproval(String id);

	public LinkedHashMap<String, Object> getTimeSheetStatus();

	public LinkedHashMap<String, Object> reSubmitTimeSheet(String id);

	public LinkedHashMap<String, Object> getTimeSheetBetweenDatesByEmployee(String id, String month);

	public LinkedHashMap<String, Object> getEmployeeMissingTimeSheetWeek(String id, LocalDate fromDate, LocalDate toDate);

	public LinkedHashMap<String, Object> getTimeSheetPendingApproval(String id);
	
	public List<String> getWeeksForMonths(LocalDate fromDate, LocalDate toDate);
}
