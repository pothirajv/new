package com.panel.dto;

import java.time.LocalDate;
import java.util.List;

public class LeaveDetailsDto {

	private String id;
	private float noOfDays;
	private String fromDate;
	private String toDate;
	private String leaveType;
	private String reason;
	private String leaveStatus;
	private String empCode;
	private LocalDate requestedDate;
	private String empName;
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

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public LocalDate getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(LocalDate requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public List<String> getReportingPerson() {
		return reportingPerson;
	}

	public void setReportingPerson(List<String> reportingPerson) {
		this.reportingPerson = reportingPerson;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
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
