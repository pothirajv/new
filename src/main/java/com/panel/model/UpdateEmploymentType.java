package com.panel.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Update_employmentType")
public class UpdateEmploymentType {

	@Id
	@Indexed
	private String id;
	private String employmentTypesName;
	private String employmentTypesDescription;
	private LocalDate updateddate;
	private String updatedBy;
	private EmploymentTypes employmentTypes;
	
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
	
	public LocalDate getUpdateddate() {
		return updateddate;
	}
	public void setUpdateddate(LocalDate updateddate) {
		this.updateddate = updateddate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public EmploymentTypes getEmploymentTypes() {
		return employmentTypes;
	}
	public void setEmploymentTypes(EmploymentTypes employmentTypes) {
		this.employmentTypes = employmentTypes;
	}
	
	
	
}
