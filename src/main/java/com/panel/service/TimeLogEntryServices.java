package com.panel.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import com.panel.dto.TimeLogEntryDto;

public interface TimeLogEntryServices {
	
	public LinkedHashMap<String, Object> SaveAndUpdate(TimeLogEntryDto timeLogEntryDto);
	
	public LinkedHashMap<String, Object> getAllTimeLogEntries();

	public LinkedHashMap<String, Object> getTimeLogEntryById(String id);
	
	public LinkedHashMap<String, Object> getTimeLogEntryByEmployee(LocalDate fromDate, LocalDate toDate, String empCode);

	public LinkedHashMap<String, Object> deleteTimeLogEntryById(String id);
	
	public LinkedHashMap<String, Object> getAllEmployeeTimelog(String id);
	
	public LinkedHashMap<String, Object> getMonthlyTimeLogEntries(LocalDate fromDate, LocalDate toDate);
	
	public LinkedHashMap<String, Object> getAllTimeLogStatus();
	
	public byte[] getTimeLogEntriesByProjectAndMonth(LocalDate fromDate, LocalDate toDate, String projectId);
}
