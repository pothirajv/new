package com.panel.dto;

import java.time.LocalDate;

import com.panel.model.ProjectInformation;

public class TimeSheetLogDto {
	
	private LocalDate date;

	private ProjectInformation project;

	private String projectId;

	private String activity;

	private String activityId;

	private String Workdescription;

	private String effort;

	private String hours;

	private String minutes;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public ProjectInformation getProject() {
		return project;
	}

	public void setProject(ProjectInformation project) {
		this.project = project;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getWorkdescription() {
		return Workdescription;
	}

	public void setWorkdescription(String workdescription) {
		Workdescription = workdescription;
	}

	public String getEffort() {
		return effort;
	}

	public void setEffort(String effort) {
		this.effort = effort;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
}
