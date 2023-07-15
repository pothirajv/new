package com.panel.dto;

import java.time.LocalDate;
import java.util.LinkedHashMap;

public class TimeLogReportDto {

	private String empCode;
	private String empName;
	private String emailId;
	private String client;
	private String project;
	private String jobName;
	private LinkedHashMap<LocalDate, String> data;
	private String total;
	private String totalEmp;

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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public LinkedHashMap<LocalDate, String> getData() {
		return data;
	}

	public void setData(LinkedHashMap<LocalDate, String> data) {
		this.data = data;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTotalEmp() {
		return totalEmp;
	}

	public void setTotalEmp(String totalEmp) {
		this.totalEmp = totalEmp;
	}

}
