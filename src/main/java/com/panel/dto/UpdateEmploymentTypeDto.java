package com.panel.dto;

import java.time.LocalDate;

public class UpdateEmploymentTypeDto {

	private String id;
	private String employmentTypesName;
	private String employmentTypesDescription;
	private LocalDate updateddate;
	private String updatedBy;
	
	
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
	
	
	
}
