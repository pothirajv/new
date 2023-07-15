package com.panel.dto;

import java.time.LocalDate;

public class ParentActivityDto {

	private String id;
	private String parentActivity;
	private String parentActivityDesc;
	private String createdBy;
	private LocalDate createdDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentActivity() {
		return parentActivity;
	}

	public void setParentActivity(String parentActivity) {
		this.parentActivity = parentActivity;
	}

	public String getParentActivityDesc() {
		return parentActivityDesc;
	}

	public void setParentActivityDesc(String parentActivityDesc) {
		this.parentActivityDesc = parentActivityDesc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

}
