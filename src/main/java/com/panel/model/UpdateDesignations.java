package com.panel.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "Update_designations")
public class UpdateDesignations {

	@Id
	@Indexed
	private String id;
	private String designationName;
	private String designationDescription;
	private LocalDate updateddate;
	private String updatedBy;
	private Designations designations;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getDesignationDescription() {
		return designationDescription;
	}
	public void setDesignationDescription(String designationDescription) {
		this.designationDescription = designationDescription;
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
	public Designations getDesignations() {
		return designations;
	}
	public void setDesignations(Designations designations) {
		this.designations = designations;
	}
	
	
	
	
	
	
}
