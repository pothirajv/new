package com.panel.dto;

public class InstitutionDto {

	/*---College---*/
	private String instituteName;
	private String university;
	private String degree;
	private String coreSubjects;
	private String yearOfPassing;
	private String gpa;		//GPA/Marks
	
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getCoreSubjects() {
		return coreSubjects;
	}
	public void setCoreSubjects(String coreSubjects) {
		this.coreSubjects = coreSubjects;
	}
	public String getYearOfPassing() {
		return yearOfPassing;
	}
	public void setYearOfPassing(String yearOfPassing) {
		this.yearOfPassing = yearOfPassing;
	}
	public String getGpa() {
		return gpa;
	}
	public void setGpa(String gpa) {
		this.gpa = gpa;
	}
	
	

}
