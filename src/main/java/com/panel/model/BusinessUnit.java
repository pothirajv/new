package com.panel.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "business_unit")
public class BusinessUnit {
	@Id
	@Indexed
	private String id; 
	private String businessUnitName;
	private String businessUnitCode;
	private String businessUnitHead;
	private String businessUnitAlternate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusinessUnitName() {
		return businessUnitName;
	}
	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}
	public String getBusinessUnitCode() {
		return businessUnitCode;
	}
	public void setBusinessUnitCode(String businessUnitCode) {
		this.businessUnitCode = businessUnitCode;
	}
	public String getBusinessUnitHead() {
		return businessUnitHead;
	}
	public void setBusinessUnitHead(String businessUnitHead) {
		this.businessUnitHead = businessUnitHead;
	}
	public String getBusinessUnitAlternate() {
		return businessUnitAlternate;
	}
	public void setBusinessUnitAlternate(String businessUnitAlternate) {
		this.businessUnitAlternate = businessUnitAlternate;
	}
	
	

}
