package com.panel.dto;

import java.util.List;

public class EmployeeDetailsDto {

	private String id;
	private String name;
	private String lastName;
	private String fathername;
	private String employeeCode;
	private String emailId;
	private String personalEmailId;
	private String dateOfJoining;
	private String dateOfBirth;
	private String mobile;
	private String phone;
	private String bloodGroup;
	private String dateOfLeave;
	private String emergencyContactNumber;
	private String emergencyContactRelation;
	private String employeeLocation;
	private String gender;
	private String martialStatus;
	private String presentAddressLine1;
	private String presentAddressLine2;
	private String presentAddressCity;
	private String presentAddressPinCode;
	private String permanentAddressLine1;
	private String permanentAddressLine2;
	private String permanentAddressCity;
	private String permanentAddressPinCode;

	private String employeeStatus;
	private String employeeCategory ;
	private String employmentDesignation;
	
	/*---School (X std)---*/
	private String xthInstitutionName;
	private String xthPassingYear;
	private String xthboard;  
	private String xthPassPercentage;
	
	/*---School (XII std)---*/
	private String institutionName;
	private String passingYear;
	private String coreSubject;
	private String passPercentage;
	private String board; 
	
	
	/*---College---*/
	/*
	private String instituteName;
	private String university;
	private String degree;
	private String coreSubjects;
	private String yearOfPassing;
	private String gpa;		//GPA/Marks
	*/
	
	private List<InstitutionDto> institutions;
	
	/*---Professional Certification---*/
	/*
	private String certificateName;
	private String descriptions;
	private String issuedBy;
	private String validity;
	*/
	
	private List<CertificateDto> certificates;
	
	/*---leave info---*/
	/*
	private String financialYear;
	private int addLeave;
	private String leaveType;
	private String remarks;
	*/
	
	/*--- work info ---*/
	/*
	private String department;
	private String employmentType;
	private String employeeStatus;
	private String employmentDesignation;
	private String currentExperience;
	*/

	/*---project info---*/
	/*
	private String projectName;
	private String duration;
	private String reportingPersonName;
	private String reportingPresonEmail;
	private String responsibilities;
	private String description;
	*/

	/*---prev.Employment info---*/
	/*
	private String companyName;
	private String workedFrom;
	private String workedTill;
	private String HrEmail;
	private String reportPersonName;
	private String reportPresonEmail;
	private String companyUrl;
	*/

	/*Personal Identification Details */
	private String passport;
	private String passportValidity;
	private String pan;
	private String aadhaar;

	/*Employment Details */
	/*
	private String organization;
	private String designation;
	private String startyear;		//Start YYMM 
	private String endyear;			//End YYMM 
	
	*/
	
	List<EmploymentDetailsDto> employmentDetails;
	
		/*Skill Details  */
	/*
	private String technicalSkills;
	private String proficiency;
	private int yearsOfExperience;
	*/
	
	List<SkillsDetailsDto> skillsDetails;
	
	/*Statutory Details*/
	private String pfNo;
	private String esiNo;
	private String groupInsurance; 		
	private String insuranceValidity;
	private List<InsuranceCoverageDto> insuranceCoverages;
	
	/*Employee Details */
	private String businessUnit;		//(Should be from a drop down)
	private String masterDepartment;		//(Should be from a drop down)
	private List<String> reportingManager;
	
	private long totalLeave;
	private long balance;
	
	private boolean statusFlag = true ;
	
	private String password;
	
	private int roleId;
	private String role;
	
	
	public String getXthInstitutionName() {
		return xthInstitutionName;
	}
	public void setXthInstitutionName(String xthInstitutionName) {
		this.xthInstitutionName = xthInstitutionName;
	}
	public String getXthPassingYear() {
		return xthPassingYear;
	}
	public void setXthPassingYear(String xthPassingYear) {
		this.xthPassingYear = xthPassingYear;
	}
	
	public String getXthboard() {
		return xthboard;
	}
	public void setXthboard(String xthboard) {
		this.xthboard = xthboard;
	}
	public String getBoard() {
		return board;
	}
	public void setBoard(String board) {
		this.board = board;
	}
	public String getXthPassPercentage() {
		return xthPassPercentage;
	}
	public void setXthPassPercentage(String xthPassPercentage) {
		this.xthPassPercentage = xthPassPercentage;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPersonalEmailId() {
		return personalEmailId;
	}
	public void setPersonalEmailId(String personalEmailId) {
		this.personalEmailId = personalEmailId;
	}
	public String getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMartialStatus() {
		return martialStatus;
	}
	public void setMartialStatus(String martialStatus) {
		this.martialStatus = martialStatus;
	}
	
	public String getPresentAddressLine1() {
		return presentAddressLine1;
	}
	public void setPresentAddressLine1(String presentAddressLine1) {
		this.presentAddressLine1 = presentAddressLine1;
	}
	public String getPresentAddressLine2() {
		return presentAddressLine2;
	}
	public void setPresentAddressLine2(String presentAddressLine2) {
		this.presentAddressLine2 = presentAddressLine2;
	}
	public String getPresentAddressCity() {
		return presentAddressCity;
	}
	public void setPresentAddressCity(String presentAddressCity) {
		this.presentAddressCity = presentAddressCity;
	}
	public String getPresentAddressPinCode() {
		return presentAddressPinCode;
	}
	public void setPresentAddressPinCode(String presentAddressPinCode) {
		this.presentAddressPinCode = presentAddressPinCode;
	}
	public String getPermanentAddressLine1() {
		return permanentAddressLine1;
	}
	public void setPermanentAddressLine1(String permanentAddressLine1) {
		this.permanentAddressLine1 = permanentAddressLine1;
	}
	public String getPermanentAddressLine2() {
		return permanentAddressLine2;
	}
	public void setPermanentAddressLine2(String permanentAddressLine2) {
		this.permanentAddressLine2 = permanentAddressLine2;
	}
	public String getPermanentAddressCity() {
		return permanentAddressCity;
	}
	public void setPermanentAddressCity(String permanentAddressCity) {
		this.permanentAddressCity = permanentAddressCity;
	}
	public String getPermanentAddressPinCode() {
		return permanentAddressPinCode;
	}
	public void setPermanentAddressPinCode(String permanentAddressPinCode) {
		this.permanentAddressPinCode = permanentAddressPinCode;
	}
	public String getPan() {
		return pan;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getAadhaar() {
		return aadhaar;
	}
	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFathername() {
		return fathername;
	}
	public void setFathername(String fathername) {
		this.fathername = fathername;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDateOfLeave() {
		return dateOfLeave;
	}
	public void setDateOfLeave(String dateOfLeave) {
		this.dateOfLeave = dateOfLeave;
	}
	public String getEmergencyContactNumber() {
		return emergencyContactNumber;
	}
	public void setEmergencyContactNumber(String emergencyContactNumber) {
		this.emergencyContactNumber = emergencyContactNumber;
	}
	public String getEmergencyContactRelation() {
		return emergencyContactRelation;
	}
	public void setEmergencyContactRelation(String emergencyContactRelation) {
		this.emergencyContactRelation = emergencyContactRelation;
	}
	public String getEmployeeLocation() {
		return employeeLocation;
	}
	public void setEmployeeLocation(String employeeLocation) {
		this.employeeLocation = employeeLocation;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public String getPassingYear() {
		return passingYear;
	}
	public void setPassingYear(String passingYear) {
		this.passingYear = passingYear;
	}
	public String getCoreSubject() {
		return coreSubject;
	}
	public void setCoreSubject(String coreSubject) {
		this.coreSubject = coreSubject;
	}
	public String getPassPercentage() {
		return passPercentage;
	}
	public void setPassPercentage(String passPercentage) {
		this.passPercentage = passPercentage;
	}
	
	
	public List<CertificateDto> getCertificates() {
		return certificates;
	}
	public void setCertificates(List<CertificateDto> certificates) {
		this.certificates = certificates;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public String getPassportValidity() {
		return passportValidity;
	}
	public void setPassportValidity(String passportValidity) {
		this.passportValidity = passportValidity;
	}
	public String getPfNo() {
		return pfNo;
	}
	public void setPfNo(String pfNo) {
		this.pfNo = pfNo;
	}
	public String getEsiNo() {
		return esiNo;
	}
	public void setEsiNo(String esiNo) {
		this.esiNo = esiNo;
	}
	public String getGroupInsurance() {
		return groupInsurance;
	}
	public void setGroupInsurance(String groupInsurance) {
		this.groupInsurance = groupInsurance;
	}
	public String getInsuranceValidity() {
		return insuranceValidity;
	}
	public void setInsuranceValidity(String insuranceValidity) {
		this.insuranceValidity = insuranceValidity;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public boolean isStatusFlag() {
		return statusFlag;
	}
	public void setStatusFlag(boolean statusFlag) {
		this.statusFlag = statusFlag;
	}
	public String getEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}
	public String getEmployeeCategory() {
		return employeeCategory;
	}
	public void setEmployeeCategory(String employeeCategory) {
		this.employeeCategory = employeeCategory;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	
	public String getMasterDepartment() {
		return masterDepartment;
	}
	public void setMasterDepartment(String masterDepartment) {
		this.masterDepartment = masterDepartment;
	}
	public List<String> getReportingManager() {
		return reportingManager;
	}
	public void setReportingManager(List<String> reportingManager) {
		this.reportingManager = reportingManager;
	}
	public long getTotalLeave() {
		return totalLeave;
	}
	public void setTotalLeave(long totalLeave) {
		this.totalLeave = totalLeave;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<InstitutionDto> getInstitutions() {
		return institutions;
	}
	public void setInstitutions(List<InstitutionDto> institutions) {
		this.institutions = institutions;
	}
	public List<EmploymentDetailsDto> getEmploymentDetails() {
		return employmentDetails;
	}
	public void setEmploymentDetails(List<EmploymentDetailsDto> employmentDetails) {
		this.employmentDetails = employmentDetails;
	}
	public List<SkillsDetailsDto> getSkillsDetails() {
		return skillsDetails;
	}
	public void setSkillsDetails(List<SkillsDetailsDto> skillsDetails) {
		this.skillsDetails = skillsDetails;
	}
	public String getEmploymentDesignation() {
		return employmentDesignation;
	}
	public void setEmploymentDesignation(String employmentDesignation) {
		this.employmentDesignation = employmentDesignation;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public List<InsuranceCoverageDto> getInsuranceCoverages() {
		return insuranceCoverages;
	}
	public void setInsuranceCoverages(List<InsuranceCoverageDto> insuranceCoverages) {
		this.insuranceCoverages = insuranceCoverages;
	}
	
}
