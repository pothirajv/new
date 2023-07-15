package com.panel.dto;

import java.util.List;

import com.panel.model.ActivityCode;

public class ProjectInformationDto {

	private String id;

	private String projectName;

	private String fromDate;

	private String todate;

	private String projectDescription;

	private String client;

	private String createdBy;

	private String updatedBy;

	private long noOfResources;

	private List<String> assignedResources;

	private List<ActivityCode> activityCodes;
	
	private String businessUnitId;
	
	private String masterDepartmentId;
	
	private String projectManager;

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public long getNoOfResources() {
		return noOfResources;
	}

	public void setNoOfResources(long noOfResources) {
		this.noOfResources = noOfResources;
	}

	public List<String> getAssignedResources() {
		return assignedResources;
	}

	public void setAssignedResources(List<String> assignedResources) {
		this.assignedResources = assignedResources;
	}

	public List<ActivityCode> getActivityCodes() {
		return activityCodes;
	}

	public void setActivityCodes(List<ActivityCode> activityCodes) {
		this.activityCodes = activityCodes;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	public String getMasterDepartmentId() {
		return masterDepartmentId;
	}

	public void setMasterDepartmentId(String masterDepartmentId) {
		this.masterDepartmentId = masterDepartmentId;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

}
