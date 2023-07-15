package com.panel.dto;

import java.util.List;

public class TimeLogEntryDto {
	
	
	private String id;
	
	private	String date;
	
	private String status;

	private String employee;
	
	private String employeeId;
	
	private List<TimeLogListDto> timeLogList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public List<TimeLogListDto> getTimeLogList() {
		return timeLogList;
	}

	public void setTimeLogList(List<TimeLogListDto> timeLogList) {
		this.timeLogList = timeLogList;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
			

}
