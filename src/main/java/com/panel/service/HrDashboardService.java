package com.panel.service;

import java.util.LinkedHashMap;

public interface HrDashboardService {
	
	public LinkedHashMap<String, Object> getAllEmployeeCount();
	public LinkedHashMap<String, Object> getAllClientCount();
	public LinkedHashMap<String, Object> getAllProjectCount();
	public LinkedHashMap<String, Object> getTodayLeaves();
}
