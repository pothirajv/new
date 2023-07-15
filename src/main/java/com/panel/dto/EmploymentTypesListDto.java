package com.panel.dto;

import java.time.LocalDate;

public class EmploymentTypesListDto {

	private String employmentTypesName;
	private LocalDate createdDate;
	private String creatdBy;
	
	
	public String getEmploymentTypesName() {
		return employmentTypesName;
	}
	public void setEmploymentTypesName(String employmentTypesName) {
		this.employmentTypesName = employmentTypesName;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatdBy() {
		return creatdBy;
	}
	public void setCreatdBy(String creatdBy) {
		this.creatdBy = creatdBy;
	}
	
	
	
	
	
	
}
