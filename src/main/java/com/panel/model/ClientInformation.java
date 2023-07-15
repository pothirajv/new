package com.panel.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "clientinformation")

public class ClientInformation {

	@Id
	@Indexed
	private String id;
	
	
	@Field("Client_Name")
	private String clientName;

	//@Field("Customer_Name")
	//private String customerName;
	
	@Field("Contact_No")
	private String contactNo;
	
	@Field("Contact_Email")
	private String contactEmail;
	
	@Field("Contact_Name")
	private String contactName;
	
	@Field("GST")
	private String gst;
	
	
	@Field("Company_Address")
	private String company_Address;
	
	
	//@Field("Address")
	//private String address;

	//@Field("GST_No")
	//private String gstNo;

	//@Field("PAN_No")
	//private String panNo;
	
	@Field("Created_By")
	private String createdBy;
	
	
	@Field("Created_Date")
	private LocalDate createdDate;
	
	@Field("Updated_By")
	private String updatedBy;
	
	@Field("Updated_Date")
	private LocalDate updatedDate;

	private int noOfResources;
	
	private int noOfProjects;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}

	public String getCompany_Address() {
		return company_Address;
	}

	public void setCompany_Address(String company_Address) {
		this.company_Address = company_Address;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public int getNoOfResources() {
		return noOfResources;
	}

	public void setNoOfResources(int noOfResources) {
		this.noOfResources = noOfResources;
	}

	public int getNoOfProjects() {
		return noOfProjects;
	}

	public void setNoOfProjects(int noOfProjects) {
		this.noOfProjects = noOfProjects;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	
}
