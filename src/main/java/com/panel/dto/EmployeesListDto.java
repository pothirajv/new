package com.panel.dto;

public class EmployeesListDto {

	private String name;
//	private String role;
	private String emailId;
	private boolean employeeStatusFlag;
	
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEmployeeStatusFlag() {
		return employeeStatusFlag;
	}
	public void setEmployeeStatusFlag(boolean employeeStatusFlag) {
		this.employeeStatusFlag = employeeStatusFlag;
	}
	
	
	
	
}
