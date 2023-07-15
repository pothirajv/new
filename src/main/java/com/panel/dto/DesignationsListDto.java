package com.panel.dto;

import java.time.LocalDate;

public class DesignationsListDto {

	private String designationName;
	private LocalDate createdDate;
	private String creatdBy;
	
	
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
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
