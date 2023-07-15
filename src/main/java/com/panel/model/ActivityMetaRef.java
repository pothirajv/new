package com.panel.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "activity_meta_ref")
public class ActivityMetaRef {

	@Id
	@Indexed
	private String id;
	private String activityMeta;
	private String activityMetaDescription;
	private String parentActivityId;
	private ParentActivity parentActivity;
	private LocalDate createdDate;
	private String createdBy;

	public String getActivityMeta() {
		return activityMeta;
	}

	public void setActivityMeta(String activityMeta) {
		this.activityMeta = activityMeta;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityMetaDescription() {
		return activityMetaDescription;
	}

	public void setActivityMetaDescription(String activityMetaDescription) {
		this.activityMetaDescription = activityMetaDescription;
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

	public String getParentActivityId() {
		return parentActivityId;
	}

	public void setParentActivityId(String parentActivityId) {
		this.parentActivityId = parentActivityId;
	}

	public ParentActivity getParentActivity() {
		return parentActivity;
	}

	public void setParentActivity(ParentActivity parentActivity) {
		this.parentActivity = parentActivity;
	}

}
