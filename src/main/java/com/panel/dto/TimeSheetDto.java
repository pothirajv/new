package com.panel.dto;

import java.util.List;

public class TimeSheetDto {

	private String id;

	private String description;

	private String empId;

	private String empName;

	private String totalhours;

	private String status;

	private String respondedBy;

	private String feedback;

	private List<String> reportingPersons;

	private List<String> timeLogs;
	
	private List<TimeSheetLogDto> timeSheetLog;

	private String month;

	private String week;
	
	private String fromDate;
	
	private String toDate;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public List<String> getReportingPersons() {
		return reportingPersons;
	}

	public void setReportingPersons(List<String> reportingPersons) {
		this.reportingPersons = reportingPersons;
	}

	public List<String> getTimeLogs() {
		return timeLogs;
	}

	public void setTimeLogs(List<String> timeLogs) {
		this.timeLogs = timeLogs;
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

	public List<TimeSheetLogDto> getTimeSheetLog() {
		return timeSheetLog;
	}

	public void setTimeSheetLog(List<TimeSheetLogDto> timeSheetLog) {
		this.timeSheetLog = timeSheetLog;
	}
}
