package com.panel.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.panel.dto.UpdateEmploymentTypeDto;

@Document(collection = "employment_types")
public class EmploymentTypes {

	@Id
	@Indexed
	private String id;
	private String employmentTypesName;
	private String employmentTypesDescription;
	private LocalDate createdDate;
	private LocalDate updateddate;
	private String createdBy;
	private String updatedBy;
	private int noOfResources;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmploymentTypesName() {
		return employmentTypesName;
	}
	public void setEmploymentTypesName(String employmentTypesName) {
		this.employmentTypesName = employmentTypesName;
	}
	public String getEmploymentTypesDescription() {
		return employmentTypesDescription;
	}
	public void setEmploymentTypesDescription(String employmentTypesDescription) {
		this.employmentTypesDescription = employmentTypesDescription;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDate getUpdateddate() {
		return updateddate;
	}
	public void setUpdateddate(LocalDate updateddate) {
		this.updateddate = updateddate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public int getNoOfResources() {
		return noOfResources;
	}
	public void setNoOfResources(int noOfResources) {
		this.noOfResources = noOfResources;
	}
	
	
	
	
	
}
