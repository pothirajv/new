package com.panel.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reporting_person")
public class ReportingPerson {

	@Id
	@Indexed
	private String id;
	private String reportingPerson;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReportingPerson() {
		return reportingPerson;
	}
	public void setReportingPerson(String reportingPerson) {
		this.reportingPerson = reportingPerson;
	}
	
	
	
}
