package com.panel.dto;

import java.time.LocalDate;

import com.panel.model.EmployeeDetails;
import com.panel.model.LeaveDetails;

public class LOPLeaveDetailsDto {

	private String id;
	private String leaveDetailsId;
	private LeaveDetails leaveDetails;
	private float noOfDays;
	private String employee;
	private EmployeeDetails employeeDetails;
	private boolean status;
	private LocalDate createdDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeaveDetailsId() {
		return leaveDetailsId;
	}

	public void setLeaveDetailsId(String leaveDetailsId) {
		this.leaveDetailsId = leaveDetailsId;
	}

	public LeaveDetails getLeaveDetails() {
		return leaveDetails;
	}

	public void setLeaveDetails(LeaveDetails leaveDetails) {
		this.leaveDetails = leaveDetails;
	}

	public float getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(float noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public EmployeeDetails getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(EmployeeDetails employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
