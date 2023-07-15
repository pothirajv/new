package com.panel.dto;

import java.time.LocalDate;
import java.util.List;

import com.panel.model.TimeLogEntry;
import com.panel.model.TimeSheetStatus;

public class TimeSheetResponseDto {
	
	private String id;

	private String description;

	private String empId;

	private String empName;

	private String totalhours;

	private TimeSheetStatus status;

	private String respondedBy;

	private String feedback;

	private LocalDate requestedDate;

	private List<String> reportingPerson;

	private List<TimeLogEntry> timeLogs;
	
	private List<TimeSheetLogDto> timeSheetLog;

	private String month;

	private String week;

	private LocalDate fromDate;

	private LocalDate toDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getTotalhours() {
		return totalhours;
	}

	public void setTotalhours(String totalhours) {
		this.totalhours = totalhours;
	}

	public TimeSheetStatus getStatus() {
		return status;
	}

	public void setStatus(TimeSheetStatus status) {
		this.status = status;
	}

	public String getRespondedBy() {
		return respondedBy;
	}

	public void setRespondedBy(String respondedBy) {
		this.respondedBy = respondedBy;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public LocalDate getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(LocalDate requestedDate) {
		this.requestedDate = requestedDate;
	}

	public List<String> getReportingPerson() {
		return reportingPerson;
	}

	public void setReportingPerson(List<String> reportingPerson) {
		this.reportingPerson = reportingPerson;
	}

	public List<TimeLogEntry> getTimeLogs() {
		return timeLogs;
	}

	public void setTimeLogs(List<TimeLogEntry> timeLogs) {
		this.timeLogs = timeLogs;
	}

	public List<TimeSheetLogDto> getTimeSheetLog() {
		return timeSheetLog;
	}

	public void setTimeSheetLog(List<TimeSheetLogDto> timeSheetLog) {
		this.timeSheetLog = timeSheetLog;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
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

}
