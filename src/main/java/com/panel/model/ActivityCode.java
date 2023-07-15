package com.panel.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "activitycode")
public class ActivityCode {

	@Id
	@Indexed

	private String id;

	private String authority;

	@Field("Activity_Code")
	private String activityCode;

	@Field("Activity_Description")
	private String activityDescription;

	private String activityMeta;

	private String parentActivityId;

	private String projectId;

//	private String projectName;

	@Field("Created_By")
	private String createdBy;

	@Field("Created_On")
	private LocalDate createdOn;

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	public String getActivityMeta() {
		return activityMeta;
	}

	public void setActivityMeta(String activityMeta) {
		this.activityMeta = activityMeta;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

//	public String getProjectName() {
//		return projectName;
//	}
//
//	public void setProjectName(String projectName) {
//		this.projectName = projectName;
//	}

	public String getParentActivityId() {
		return parentActivityId;
	}

	public void setParentActivityId(String parentActivityId) {
		this.parentActivityId = parentActivityId;
	}

}
