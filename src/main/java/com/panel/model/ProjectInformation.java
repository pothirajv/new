package com.panel.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "projectinformation")
public class ProjectInformation {

	@Id
	@Indexed

	private String id;

	@Field("Project_Name")
	private String projectName;

	// @Field("Project_Id")
	// private String projectId;

	// @Field("Project_Manager")
	// private String projectManager;

	// @Field("Planned_Start_Date")
	// private LocalDate plannedStartDate;

	// @Field("Planned_End_Date")
	// private LocalDate plannedEndDate;

	// @Field("Actual_Start_Date")
	// private LocalDate actualStartDate;

	// @Field("Actual_End_Date")
	// private LocalDate actualEndDate;

	@Field("From_Date")
	private LocalDate fromDate;

	@Field("To_Date")
	private LocalDate toDate;

	@Field("Project_Description")
	private String project_Description;

	@Field("Created_Date")
	private LocalDate createdDate;

	@Field("Created_By")
	private String createdBy;

	@Field("updated_Date")
	private LocalDate updatedDate;

	@Field("updated_By")
	private String updatedBy;

	private List<ActivityCode> activityCodes;

	private ClientInformation client;

	private long noOfResources;

	private List<String> assignedResources;

	private String businessUnitId;
	
	private BusinessUnit businessUnit;

	private String masterDepartmentId;
	
	private MasterDepartment masterDepartment;

	private String projectManager;

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

	public String getProject_Description() {
		return project_Description;
	}

	public void setProject_Description(String project_Description) {
		this.project_Description = project_Description;
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

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public ClientInformation getClient() {
		return client;
	}

	public void setClient(ClientInformation client) {
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

	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getMasterDepartmentId() {
		return masterDepartmentId;
	}

	public void setMasterDepartmentId(String masterDepartmentId) {
		this.masterDepartmentId = masterDepartmentId;
	}

	public MasterDepartment getMasterDepartment() {
		return masterDepartment;
	}

	public void setMasterDepartment(MasterDepartment masterDepartment) {
		this.masterDepartment = masterDepartment;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

}
