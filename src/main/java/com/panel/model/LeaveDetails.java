package com.panel.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "leave_details")
public class LeaveDetails {

	@Id
	@Indexed
	private String id;
	private float noOfDays;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LeaveType leaveType;
	private String reason;
	private LocalDate requestedDate;
	private LeaveStatus leaveStatus;
	private String empCode;
	private String empName;
	private float totalLeave;
	private float balance;
	private List<String> reportingPerson;
	private String remarks;
	private boolean status;
	private float lopDays;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(float noOfDays) {
		this.noOfDays = noOfDays;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public LocalDate getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(LocalDate requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public float getTotalLeave() {
		return totalLeave;
	}

	public void setTotalLeave(float totalLeave) {
		this.totalLeave = totalLeave;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public List<String> getReportingPerson() {
		return reportingPerson;
	}

	public void setReportingPerson(List<String> reportingPerson) {
		this.reportingPerson = reportingPerson;
	}

	public LeaveStatus getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(LeaveStatus leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public float getLopDays() {
		return lopDays;
	}

	public void setLopDays(float lopDays) {
		this.lopDays = lopDays;
	}
}
