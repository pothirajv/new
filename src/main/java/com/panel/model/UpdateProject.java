package com.panel.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "update_project")
public class UpdateProject {

	@Id
	@Indexed
	private String id;
	private LocalDate updatedDate;
	private String updatedBy;
	private ProjectInformation project;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public ProjectInformation getProject() {
		return project;
	}

	public void setProject(ProjectInformation project) {
		this.project = project;
	}

}
