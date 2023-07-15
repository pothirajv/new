package com.panel.dto;

import java.time.LocalDate;

public class DepartmenListDto {

	private String departmentName;
	private LocalDate createdDate;
	private String creatdBy;
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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
