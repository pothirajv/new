package com.panel.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "master_department")
public class MasterDepartment {
	@Id
	@Indexed
	private String id; 
	private String departmentName;
	private String departmentCode;
	private String departmentHead;
	private String departmentAlternate;
	private String businessUnitId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getDepartmentHead() {
		return departmentHead;
	}
	public void setDepartmentHead(String departmentHead) {
		this.departmentHead = departmentHead;
	}
	public String getDepartmentAlternate() {
		return departmentAlternate;
	}
	public void setDepartmentAlternate(String departmentAlternate) {
		this.departmentAlternate = departmentAlternate;
	}
	public String getBusinessUnitId() {
		return businessUnitId;
	}
	public void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}
	
	
	
	

}
