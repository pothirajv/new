package com.panel.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "leave_transaction_history")
public class LeaveTransactionHistory {

	@Id
	@Indexed
	private String id;
	private String employeeId;
	private EmployeeDetails employee;
	private LeaveDetails leaveDetails;
	private String leaveDetailsId;
	private String transactionType;
	private float noOfDays;
	private LocalDate createdDate;
	private String createdBy;
	private String leaveStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public float getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(float noOfDays) {
		this.noOfDays = noOfDays;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public EmployeeDetails getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDetails employee) {
		this.employee = employee;
	}

	public LeaveDetails getLeaveDetails() {
		return leaveDetails;
	}

	public void setLeaveDetails(LeaveDetails leaveDetails) {
		this.leaveDetails = leaveDetails;
	}

	public String getLeaveDetailsId() {
		return leaveDetailsId;
	}

	public void setLeaveDetailsId(String leaveDetailsId) {
		this.leaveDetailsId = leaveDetailsId;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
}
