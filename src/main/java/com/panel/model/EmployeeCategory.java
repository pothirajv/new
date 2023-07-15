package com.panel.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "employee_category")
public class EmployeeCategory {

	@Id
	@Indexed
	private String id;
	@Field("category")
	private String employeeCategory ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmployeeCategory() {
		return employeeCategory;
	}
	public void setEmployeeCategory(String employeeCategory) {
		this.employeeCategory = employeeCategory;
	}
	
	
	
	
	
	
}
