package com.panel.dto;

import java.util.List;

import com.panel.model.BusinessUnit;
import com.panel.model.MasterDepartment;

public class TimeSheetMissingReportDto {

	private String employeeId;
	private String employeeName;
	private BusinessUnit businessUnit;
	private MasterDepartment masterDepartment;
	private long week;
	private List<String> timeSheets;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	public MasterDepartment getMasterDepartment() {
		return masterDepartment;
	}

	public void setMasterDepartment(MasterDepartment masterDepartment) {
		this.masterDepartment = masterDepartment;
	}

	public long getWeek() {
		return week;
	}

	public void setWeek(long week) {
		this.week = week;
	}

	public List<String> getTimeSheets() {
		return timeSheets;
	}

	public void setTimeSheets(List<String> timeSheets) {
		this.timeSheets = timeSheets;
	}

}
