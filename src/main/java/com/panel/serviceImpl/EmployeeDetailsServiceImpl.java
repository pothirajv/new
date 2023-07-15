package com.panel.serviceImpl;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.panel.dto.CertificateDto;
import com.panel.dto.ChangePassword;
import com.panel.dto.EmployeeDetailsDto;
import com.panel.dto.EmployeeNameDto;
import com.panel.dto.EmploymentDetailsDto;
import com.panel.dto.InstitutionDto;
import com.panel.dto.InsuranceCoverageDto;
import com.panel.dto.SkillsDetailsDto;
import com.panel.model.BloodGroup;
import com.panel.model.Board;
import com.panel.model.BusinessUnit;
import com.panel.model.Designations;
import com.panel.model.EmployeeCategory;
import com.panel.model.EmployeeDetails;
import com.panel.model.EmployeeStatus;
import com.panel.model.EmploymentTypes;
import com.panel.model.Gender;
import com.panel.model.MartialStatus;
import com.panel.model.MasterDepartment;
import com.panel.model.Proficiency;
import com.panel.model.ProjectEmployee;
import com.panel.model.Role;
import com.panel.repository.BloodGroupRepository;
import com.panel.repository.BoardRepository;
import com.panel.repository.BusinessUnitRepository;
import com.panel.repository.DepartmentRepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.EmployeeRepository;
import com.panel.repository.DesignationsRepository;
import com.panel.repository.EmployeeCategoryRepository;
import com.panel.repository.EmployeeStatusRepository;
import com.panel.repository.EmploymentTypeRepository;
import com.panel.repository.FinancialYearRepository;
import com.panel.repository.GenderRepository;
import com.panel.repository.LeaveTypeRepository;
import com.panel.repository.MartialStatusRepository;
import com.panel.repository.MasterDepartmentRepository;
import com.panel.repository.ProficiencyRepository;
import com.panel.repository.ProjectEmployeesRepository;
import com.panel.repository.ReportingPersonRepository;
import com.panel.repository.RoleRepository;
import com.panel.service.EmployeeDetailsService;
import com.panel.support.constant.GeneralConstants;
import com.panel.support.constant.SecurityConstants;
import com.panel.support.util.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	GenderRepository genderRepository;

	@Autowired
	DepartmentRepository depertmentRepository;

	@Autowired
	EmploymentTypeRepository employmentTypeRepository;

	@Autowired
	EmployeeStatusRepository employeeStatusRepository;

	@Autowired
	DesignationsRepository designationsRepository;

	@Autowired
	MartialStatusRepository martialStatusRepository;

	@Autowired
	FinancialYearRepository financialYearRepository;

	@Autowired
	LeaveTypeRepository leaveTypeRepository;

	@Autowired
	EmployeeCategoryRepository employeeCategoryRepository;

	@Autowired
	BusinessUnitRepository businessUnitRepository;

	@Autowired
	MasterDepartmentRepository masterDepartmentRepository;

	@Autowired
	ReportingPersonRepository reportingPersonRepository;

	@Autowired
	BloodGroupRepository bloodGroupRepository;

	@Autowired
	ProficiencyRepository proficiencyRepository;

	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	ProjectEmployeesRepository projectEmployeesRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	HttpServletRequest request;

	@Autowired
	Utility utility;

	public LinkedHashMap<String, Object> saveAndUpdateEmployeeDetails(EmployeeDetailsDto employeeDetailsDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		if (employeeDetailsDto.getId() == null) {
			Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository
					.findByEmployeeCode(employeeDetailsDto.getEmployeeCode());
			if (!employeeDetail.isPresent()) {
				/*---basic info---*/
				EmployeeDetails employeeDetails = new EmployeeDetails();

				BeanUtils.copyProperties(employeeDetailsDto, employeeDetails);

				if (employeeDetailsDto.getDateOfBirth() != null && !employeeDetailsDto.getDateOfBirth().equals("")) {
					DateTimeFormatter In_formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date2 = employeeDetailsDto.getDateOfBirth();
					LocalDate date3 = LocalDate.parse(Out_formatter1.format(In_formatter1.parse(date2)).toString(),
							Out_formatter1);
					employeeDetails.setDateOfBirth(date3);
				} else {
					employeeDetails.setDateOfBirth(null);
				}

				if (employeeDetailsDto.getDateOfJoining() != null
						&& !employeeDetailsDto.getDateOfJoining().equals("")) {
					DateTimeFormatter In_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date = employeeDetailsDto.getDateOfJoining();
					LocalDate date1 = LocalDate.parse(Out_formatter.format(In_formatter.parse(date)).toString(),
							Out_formatter);
					employeeDetails.setDateOfJoining(date1);
				} else {
					employeeDetails.setDateOfJoining(null);
				}

				if (employeeDetailsDto.getDateOfLeave() != null && !employeeDetailsDto.getDateOfLeave().equals("")) {
					DateTimeFormatter In_formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter2 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date4 = employeeDetailsDto.getDateOfLeave();
					LocalDate date5 = LocalDate.parse(Out_formatter2.format(In_formatter2.parse(date4)).toString(),
							Out_formatter2);
					employeeDetails.setDateOfLeave(date5);
				} else {
					employeeDetails.setDateOfLeave(null);
				}

				if (employeeDetailsDto.getGender() != null && !employeeDetailsDto.getGender().equals("")) {
					Optional<Gender> gender = genderRepository.findById(employeeDetailsDto.getGender());
					if (gender != null) {
						employeeDetails.setGender(gender.get());
					}
				} else {
					employeeDetails.setGender(null);
				}
				if (employeeDetailsDto.getMartialStatus() != null
						&& !employeeDetailsDto.getMartialStatus().equals("")) {
					Optional<MartialStatus> martialStatus = martialStatusRepository
							.findById(employeeDetailsDto.getMartialStatus());
					if (martialStatus != null) {
						employeeDetails.setMartialStatus(martialStatus.get());
					}
				} else {
					employeeDetails.setMartialStatus(null);
				}

				if (employeeDetailsDto.getEmployeeStatus() != null
						&& !employeeDetailsDto.getEmployeeStatus().equals("")) {
					Optional<EmployeeStatus> employeeStatus = employeeStatusRepository
							.findById(employeeDetailsDto.getEmployeeStatus());
					if (employeeStatus != null) {
						employeeDetails.setEmployeeStatus(employeeStatus.get());
					}
				} else {
					employeeDetails.setEmployeeStatus(null);
				}

				Optional<EmploymentTypes> employmentType = null;
				if (employeeDetailsDto.getEmployeeCategory() != null
						&& !employeeDetailsDto.getEmployeeCategory().equals("")) {
					employmentType = employmentTypeRepository.findById(employeeDetailsDto.getEmployeeCategory());
					if (employmentType.isPresent()) {
						employeeDetails.setEmployeeCategory(employmentType.get());
					}
				} else {
					employeeDetails.setEmployeeCategory(null);
				}

				if (employeeDetailsDto.getEmploymentDesignation() != null
						&& !employeeDetailsDto.getEmploymentDesignation().equals("")) {
					Optional<Designations> designation = designationsRepository
							.findById(employeeDetailsDto.getEmploymentDesignation());
					if (designation != null) {
						employeeDetails.setEmploymentDesignation(designation.get());
					}

				} else {
					employeeDetails.setEmploymentDesignation(null);
				}

				/*---Leave info---*/
				/*
				 * if(employeeDetailsDto.getFinancialYear()!=null) { Optional<FinancialYear>
				 * financialYear =
				 * financialYearRepository.findById(employeeDetailsDto.getFinancialYear());
				 * if(financialYear != null) {
				 * employeeDetails.setFinancialYear(financialYear.get()); } }else {
				 * employeeDetails.setFinancialYear(null); }
				 * if(employeeDetailsDto.getLeaveType()!=null) { Optional<LeaveType> leaveType =
				 * leaveTypeRepository.findById(employeeDetailsDto.getLeaveType());
				 * if(leaveType!=null) { employeeDetails.setLeaveType(leaveType.get()); } }else
				 * { employeeDetails.setLeaveType(null); }
				 */
				/*---Work Info---*/

				/*
				 * if(employeeDetailsDto.getEmployeeStatus()!=null) { Optional<EmployeeStatus>
				 * employeeStatus =
				 * employeeStatusRepository.findById(employeeDetailsDto.getEmployeeStatus());
				 * if(employeeStatus!=null) {
				 * employeeDetails.setEmployeeStatus(employeeStatus.get()); } }else {
				 * employeeDetails.setEmployeeStatus(null); }
				 * 
				 * if(employeeDetailsDto.getDepartment()!=null) { Optional<Department>
				 * department =
				 * depertmentRepository.findById(employeeDetailsDto.getDepartment());
				 * if(department!=null) { employeeDetails.setDepartment(department.get()); }
				 * }else { employeeDetails.setDepartment(null); }
				 * 
				 * LocalDate localdate = employeeDetails.getDateOfJoining().withDayOfMonth(1);
				 * LocalDate myObj = LocalDate.now().withDayOfMonth(1); Period diff =
				 * Period.between(localdate, myObj);
				 * employeeDetails.setCurrentExperience(diff.getYears()+"Year(s) "+diff.
				 * getMonths()+"Month(s)");
				 */
				/*
				 * Optional<Employee> employee =
				 * employeeRepository.findOneById(employeeDetailsDto.getAddedBy()); if
				 * (employee.isPresent()) {
				 * employeeDetails.setAddedBy(employee.get().getEmployeeName()); }
				 * employeeDetails.setAddedDate(LocalDate.now());
				 */

				/*---College---*/

				List<InstitutionDto> institutionDtos = new ArrayList<>();
				if (!CollectionUtils.isEmpty(employeeDetailsDto.getInstitutions())) {
					for (InstitutionDto inst : employeeDetailsDto.getInstitutions()) {
						InstitutionDto institution = new InstitutionDto();
						BeanUtils.copyProperties(inst, institution);
						institutionDtos.add(institution);
					}
				}
				employeeDetails.setInstitutions(institutionDtos);

				/*--- certificates---*/
				List<CertificateDto> certificateDto = new ArrayList<>();
				if (!CollectionUtils.isEmpty(employeeDetailsDto.getCertificates())) {
					for (CertificateDto certify : employeeDetailsDto.getCertificates()) {
						CertificateDto certificate = new CertificateDto();
						BeanUtils.copyProperties(certify, certificate);
						certificateDto.add(certificate);
					}
				}
				employeeDetails.setCertificates(certificateDto);

				// skill details

				List<SkillsDetailsDto> skillsDetailsDtos = new ArrayList<>();
				if (!CollectionUtils.isEmpty(employeeDetailsDto.getSkillsDetails())) {
					for (SkillsDetailsDto skillsDetails : employeeDetailsDto.getSkillsDetails()) {
						SkillsDetailsDto skillsDetailsDto = new SkillsDetailsDto();
						BeanUtils.copyProperties(skillsDetails, skillsDetailsDto);
						skillsDetailsDtos.add(skillsDetailsDto);
					}
				}
				employeeDetails.setSkillsDetails(skillsDetailsDtos);

				// Employment details

				List<EmploymentDetailsDto> employment = new ArrayList<>();
				if (!CollectionUtils.isEmpty(employeeDetailsDto.getEmploymentDetails())) {
					for (EmploymentDetailsDto emplDetails : employeeDetailsDto.getEmploymentDetails()) {
						EmploymentDetailsDto employmentDetailsDto = new EmploymentDetailsDto();
						BeanUtils.copyProperties(emplDetails, employmentDetailsDto);
						employment.add(employmentDetailsDto);
					}
				}
				employeeDetails.setEmploymentDetails(employment);

				if (employeeDetailsDto.getBusinessUnit() != null && !employeeDetailsDto.getBusinessUnit().equals("")) {
					Optional<BusinessUnit> businessUnit = businessUnitRepository
							.findById(employeeDetailsDto.getBusinessUnit());
					if (businessUnit.isPresent()) {
						employeeDetails.setBusinessUnit(businessUnit.get());
					}
				} else {
					employeeDetails.setBusinessUnit(null);
				}

				if (employeeDetailsDto.getMasterDepartment() != null
						&& !employeeDetailsDto.getMasterDepartment().equals("")) {
					Optional<MasterDepartment> masterDepartment = masterDepartmentRepository
							.findById(employeeDetailsDto.getMasterDepartment());
					if (masterDepartment.isPresent()) {
						employeeDetails.setMasterDepartment(masterDepartment.get());
					}
				} else {
					employeeDetails.setMasterDepartment(null);
				}
				/*
				 * if(employeeDetailsDto.getRole()!=null &&
				 * !employeeDetailsDto.getRole().equals("")) { Optional<Role>
				 * role=roleRepository.findById(employeeDetailsDto.getRole());
				 * if(role.isPresent()) { employeeDetails.setRole(role.get()); } }else {
				 * employeeDetails.setRole(null); }
				 */
				if (employeeDetailsDto.getRoleId() != 0) {
					if (employeeDetailsDto.getRoleId() == 1) {
						employeeDetails.setRole(GeneralConstants.ADMIN);
					} else if (employeeDetailsDto.getRoleId() == 2) {
						employeeDetails.setRole(GeneralConstants.MANAGER);
					} else if (employeeDetailsDto.getRoleId() == 3) {
						employeeDetails.setRole(GeneralConstants.TEAM_LEADER);
					} else if (employeeDetailsDto.getRoleId() == 4) {
						employeeDetails.setRole(GeneralConstants.TEAM_MEMBER);
					} else if (employeeDetailsDto.getRoleId() == 5) {
						employeeDetails.setRole(GeneralConstants.BUSINESS_UNIT_HEAD);
					} else if (employeeDetailsDto.getRoleId() == 6) {
						employeeDetails.setRole(GeneralConstants.DEPARTMENT_HEAD);
					}
				}

				/*
				 * if(employeeDetailsDto.getReportingManager()!=null &&
				 * !employeeDetailsDto.getReportingManager().equals("")) {
				 * Optional<ReportingPerson> reportingPerson=
				 * reportingPersonRepository.findById(employeeDetailsDto.getReportingManager());
				 * if(reportingPerson.isPresent()) {
				 * employeeDetails.setReportingManager(reportingPerson.get().getReportingPerson(
				 * )); } }else { employeeDetails.setReportingManager(null); }
				 */

				if (!CollectionUtils.isEmpty(employeeDetailsDto.getReportingManager())) {
//			List<String> reportingPersons=new ArrayList<>();
//			employeeDetailsDto.getReportingManager().forEach(rm -> {
//				Optional<EmployeeDetails> reportingPerson = employeeDetailsRepository.findById(rm);
//				if(reportingPerson.isPresent()) {
//					reportingPersons.add(reportingPerson.get().getEmployeeCode()+"-"+reportingPerson.get().getName());
//				}
//			});
//			
//			employeeDetails.setReportingManager(reportingPersons);
					employeeDetails.setReportingManager(employeeDetailsDto.getReportingManager());
				}

				employeeDetails.setStatusFlag(true);

				/*
				 * int count =1;
				 * 
				 * List<EmployeeDetails> employees = employeeDetailsRepository.findAll(); String
				 * str=null; if(employees.size()==0) { str= String.format("%04d", count++);
				 * }else { EmployeeDetails employee = employees.get(employees.size()-1); String
				 * s=employee.getEmployeeCode().substring(4); int num =Integer.parseInt(s);
				 * num++; str= String.format("%04d", num); }
				 * 
				 */

				List<EmployeeDetails> permanent = new ArrayList<>();

				List<EmployeeDetails> contract = new ArrayList<>();

				List<EmployeeDetails> employees = employeeDetailsRepository.findAll();

				int count = 1;

				String str = null;

				for (EmployeeDetails employee : employees) {
					if (employee.getEmployeeCategory().getEmploymentTypesName().equalsIgnoreCase("Contract")) {
						contract.add(employee);
					} else {
						permanent.add(employee);
					}
				}
				if (employmentType != null) {
					if (employmentType.get().getEmploymentTypesName().equalsIgnoreCase("Contract")) {
						if (contract.size() == 0) {
							str = String.format("%03d", count++);
							employeeDetails.setEmployeeCode("INFC-" + str);
						} else {
							contract = contract.stream().sorted(Comparator.comparing(EmployeeDetails::getEmployeeCode))
									.collect(Collectors.toList());
							EmployeeDetails con = contract.get(contract.size() - 1);
							String s1 = con.getEmployeeCode().substring(5);
							int num = Integer.parseInt(s1);
							num++;
							str = String.format("%03d", num);
							employeeDetails.setEmployeeCode("INFC-" + str);
						}
					} else {
						if (permanent.size() == 0) {
							str = String.format("%03d", count++);
							employeeDetails.setEmployeeCode("INFO-" + str);
						} else {
							permanent = permanent.stream()
									.sorted(Comparator.comparing(EmployeeDetails::getEmployeeCode))
									.collect(Collectors.toList());
							EmployeeDetails emp = permanent.get(permanent.size() - 1);
							String s = emp.getEmployeeCode().substring(5);
							int num = Integer.parseInt(s);
							num++;
							str = String.format("%03d", num);

							employeeDetails.setEmployeeCode("INFO-" + str);
						}
					}
				}

//				if (employmentType.get().getEmploymentTypesName().equalsIgnoreCase("Permanent")) {
				employeeDetails.setTotalLeave(0);
				employeeDetails.setBalance(0);

//				} else {
//					employeeDetails.setTotalLeave(0);
//					employeeDetails.setBalance(0);
//				}

				List<InsuranceCoverageDto> insuranceCoverageList = new ArrayList<>();
				if (!CollectionUtils.isEmpty(employeeDetailsDto.getInsuranceCoverages())) {
					for (InsuranceCoverageDto insuranceCoverageDto : employeeDetailsDto.getInsuranceCoverages()) {
						InsuranceCoverageDto insuranceCoverage = new InsuranceCoverageDto();
						insuranceCoverage.setCoverageName(insuranceCoverageDto.getCoverageName());
						insuranceCoverage.setRelationship(insuranceCoverageDto.getRelationship());
						insuranceCoverageList.add(insuranceCoverage);
					}
					employeeDetails.setInsuranceCoverages(insuranceCoverageList);
				}

				EmployeeDetails emailId = employeeDetailsRepository.findByEmailId(employeeDetailsDto.getEmailId());
				if (emailId == null) {

					if (employeeDetailsDto.getEmailId() != null && !employeeDetailsDto.getEmailId().equals("")) {
						employeeDetails.setEmailId(employeeDetailsDto.getEmailId());
					} else {
						employeeDetails.setEmailId(null);
					}

					employeeDetails.setPassword(employeeDetails.getEmployeeCode());
					employeeDetailsRepository.save(employeeDetails);
					entity.put("Status", "200");
					entity.put("Message", "Employee Created Successfully");
					entity.put("Data", employeeDetails);
				} else {
					entity.put("status", "405");
					entity.put("message", "Email AlreadyExist!..");
				}
			} else {
				entity.put("Status", "400");
				entity.put("Message", "Employee Code already exist..!");
			}
		} else {

			Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
					.findOneById(employeeDetailsDto.getId());
			if (employeeDetails.get().getId() != null) {

				EmployeeDetails employeeDetail = employeeDetails.get();
				float tot = employeeDetail.getTotalLeave();
				float bal = employeeDetail.getBalance();
				BeanUtils.copyProperties(employeeDetailsDto, employeeDetail, getNullPropertyNames(employeeDetailsDto));
				if (employeeDetailsDto.getDateOfJoining() != null
						&& !employeeDetailsDto.getDateOfJoining().equals("")) {
					DateTimeFormatter In_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date = employeeDetailsDto.getDateOfJoining();
					LocalDate date1 = LocalDate.parse(Out_formatter.format(In_formatter.parse(date)).toString(),
							Out_formatter);
					employeeDetail.setDateOfJoining(date1);
				} else if (employeeDetail.getDateOfJoining() != null && employeeDetailsDto.getDateOfJoining() != null
						&& !employeeDetailsDto.getDateOfJoining().equals("")) {
					DateTimeFormatter In_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date = employeeDetailsDto.getDateOfJoining();
					LocalDate date1 = LocalDate.parse(Out_formatter.format(In_formatter.parse(date)).toString(),
							Out_formatter);
					employeeDetail.setDateOfJoining(date1);
				} else {
					employeeDetail.setDateOfJoining(employeeDetail.getDateOfJoining());
				}

				if (employeeDetailsDto.getDateOfBirth() != null && !employeeDetailsDto.getDateOfBirth().equals("")) {
					DateTimeFormatter In_formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date2 = employeeDetailsDto.getDateOfBirth();
					LocalDate date3 = LocalDate.parse(Out_formatter1.format(In_formatter1.parse(date2)).toString(),
							Out_formatter1);
					employeeDetail.setDateOfBirth(date3);
				} else if (employeeDetail.getDateOfBirth() != null && employeeDetailsDto.getDateOfBirth() != null
						&& !employeeDetailsDto.getDateOfBirth().equals("")) {
					DateTimeFormatter In_formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date2 = employeeDetailsDto.getDateOfBirth();
					LocalDate date3 = LocalDate.parse(Out_formatter1.format(In_formatter1.parse(date2)).toString(),
							Out_formatter1);
					employeeDetail.setDateOfBirth(date3);
				} else {
					employeeDetail.setDateOfBirth(employeeDetail.getDateOfBirth());
				}

				if (employeeDetailsDto.getDateOfLeave() != null && !employeeDetailsDto.getDateOfLeave().equals("")) {
					DateTimeFormatter In_formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter2 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date2 = employeeDetailsDto.getDateOfLeave();
					LocalDate date3 = LocalDate.parse(Out_formatter2.format(In_formatter2.parse(date2)).toString(),
							Out_formatter2);
					employeeDetail.setDateOfLeave(date3);
				} else if (employeeDetail.getDateOfLeave() != null && employeeDetailsDto.getDateOfLeave() != null
						&& !employeeDetailsDto.getDateOfLeave().equals("")) {
					DateTimeFormatter In_formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date2 = employeeDetailsDto.getDateOfLeave();
					LocalDate date3 = LocalDate.parse(Out_formatter1.format(In_formatter1.parse(date2)).toString(),
							Out_formatter1);
					employeeDetail.setDateOfLeave(date3);
				} else {
					employeeDetail.setDateOfLeave(employeeDetail.getDateOfLeave());
				}

				if (employeeDetailsDto.getGender() != null && !employeeDetailsDto.getGender().equals("")) {
					Optional<Gender> gender = genderRepository.findById(employeeDetailsDto.getGender());
					if (gender.isPresent()) {
						employeeDetail.setGender(gender.get());
					}
				} else {
					employeeDetail.setGender(employeeDetail.getGender());
				}
				if (employeeDetailsDto.getMartialStatus() != null
						&& !employeeDetailsDto.getMartialStatus().equals("")) {
					Optional<MartialStatus> martialStatus = martialStatusRepository
							.findById(employeeDetailsDto.getMartialStatus());
					if (martialStatus.isPresent()) {
						employeeDetail.setMartialStatus(martialStatus.get());
					}
				} else {
					employeeDetail.setMartialStatus(employeeDetail.getMartialStatus());
				}
				if (employeeDetailsDto.getEmployeeStatus() != null
						&& !employeeDetailsDto.getEmployeeStatus().equals("")) {
					Optional<EmployeeStatus> employeeStatus = employeeStatusRepository
							.findById(employeeDetailsDto.getEmployeeStatus());
					if (employeeStatus.isPresent()) {
						employeeDetail.setEmployeeStatus(employeeStatus.get());
					}
				} else {
					employeeDetail.setEmployeeStatus(employeeDetail.getEmployeeStatus());
				}

				if (employeeDetailsDto.getEmployeeCategory() != null
						&& !employeeDetailsDto.getEmployeeCategory().equals("")) {
					Optional<EmploymentTypes> employmentType = employmentTypeRepository
							.findById(employeeDetailsDto.getEmployeeCategory());
					if (employmentType.isPresent()) {
						employeeDetail.setEmployeeCategory(employmentType.get());
//						if (employmentType.get().getEmploymentTypesName().equalsIgnoreCase("Permanent")) {
//							employeeDetail.setTotalLeave(18);
//							employeeDetail.setBalance(18);
//						} else {
//							employeeDetail.setTotalLeave(12);
//							employeeDetail.setBalance(12);
//						}
					}
				} else {
					employeeDetail.setEmployeeCategory(employeeDetail.getEmployeeCategory());
				}

				if (tot > 0) {
					employeeDetail.setTotalLeave(tot);
				}
				if (bal > 0) {
					employeeDetail.setBalance(bal);
				}

				if (employeeDetailsDto.getEmploymentDesignation() != null
						&& !employeeDetailsDto.getEmploymentDesignation().equals("")) {
					Optional<Designations> designation = designationsRepository
							.findById(employeeDetailsDto.getEmploymentDesignation());
					if (designation != null) {
						employeeDetail.setEmploymentDesignation(designation.get());
					}

				} else {
					employeeDetail.setEmploymentDesignation(employeeDetail.getEmploymentDesignation());
				}

				/*---Leave info---*/
				/*
				 * if(employeeDetailsDto.getFinancialYear()!=null) { Optional<FinancialYear>
				 * financialYear =
				 * financialYearRepository.findById(employeeDetailsDto.getFinancialYear());
				 * if(financialYear.isPresent()) {
				 * employeeDetail.setFinancialYear(financialYear.get()); } }else {
				 * employeeDetail.setFinancialYear(employeeDetail.getFinancialYear()); }
				 * 
				 * if(employeeDetailsDto.getLeaveType()!=null) { Optional<LeaveType> leaveType =
				 * leaveTypeRepository.findById(employeeDetailsDto.getLeaveType());
				 * if(leaveType.isPresent()) { employeeDetail.setLeaveType(leaveType.get()); }
				 * }else { employeeDetail.setLeaveType(employeeDetail.getLeaveType()); }
				 */

				/*---Work Info---*/
				/*
				 * if(employeeDetailsDto.getEmploymentType()!=null) { Optional<EmploymentTypes>
				 * employmentType =
				 * employmentTypeRepository.findById(employeeDetailsDto.getEmploymentType());
				 * if(employmentType.isPresent()) {
				 * employeeDetail.setEmploymentType(employmentType.get()); } }else {
				 * employeeDetail.setEmploymentType(employeeDetail.getEmploymentType()); }
				 * 
				 * if(employeeDetailsDto.getEmployeeStatus()!=null) { Optional<EmployeeStatus>
				 * employeeStatus =
				 * employeeStatusRepository.findById(employeeDetailsDto.getEmployeeStatus());
				 * if(employeeStatus.isPresent()) {
				 * employeeDetail.setEmployeeStatus(employeeStatus.get()); } }else {
				 * employeeDetail.setEmployeeStatus(employeeDetail.getEmployeeStatus()); }
				 * 
				 * if(employeeDetailsDto.getDepartment()!=null) { Optional<Department>
				 * department =
				 * depertmentRepository.findById(employeeDetailsDto.getDepartment());
				 * if(department.isPresent()) { employeeDetail.setDepartment(department.get());
				 * } }else { employeeDetail.setDepartment(employeeDetail.getDepartment()); }
				 * 
				 * if (employeeDetail.getDateOfJoining() != null) { LocalDate localdate =
				 * employeeDetail.getDateOfJoining().withDayOfMonth(1); LocalDate myObj =
				 * LocalDate.now().withDayOfMonth(1);
				 * 
				 * Period diff = Period.between(localdate, myObj); //exp
				 * =System.out.println(diff.getYears()+" year(s)"+diff.getMonths()+" month(s)");
				 * System.out.println(diff);
				 * employeeDetail.setCurrentExperience(diff.getYears()+"Year(s) "+diff.getMonths
				 * ()+"Month(s)");
				 * 
				 * } else {
				 * employeeDetail.setCurrentExperience(employeeDetail.getCurrentExperience()); }
				 */
				/*
				 * if(employeeDetailsDto.getAddedBy()!=null) { Optional<Employee> employee =
				 * employeeRepository.findById(employeeDetailsDto.getAddedBy()); if
				 * (employee.isPresent()) {
				 * employeeDetail.setAddedBy(employee.get().getEmployeeName()); } }else {
				 * employeeDetail.setAddedBy(employeeDetail.getAddedBy()); }
				 * 
				 * employeeDetail.setAddedDate(LocalDate.now());
				 */

				/*---College---*/

				if (!CollectionUtils.isEmpty(employeeDetailsDto.getInstitutions())) {
					List<InstitutionDto> institutionDtos = new ArrayList<>();
					for (InstitutionDto inst : employeeDetailsDto.getInstitutions()) {
						InstitutionDto institution = new InstitutionDto();
						BeanUtils.copyProperties(inst, institution);
						institutionDtos.add(institution);
					}
					employeeDetail.setInstitutions(institutionDtos);
				} else {
					employeeDetail.setInstitutions(employeeDetail.getInstitutions());
				}

				/*--- certificates---*/

				if (!CollectionUtils.isEmpty(employeeDetailsDto.getCertificates())) {
					List<CertificateDto> certificateDto = new ArrayList<>();
					for (CertificateDto certify : employeeDetailsDto.getCertificates()) {
						CertificateDto certificate = new CertificateDto();
						BeanUtils.copyProperties(certify, certificate);
						certificateDto.add(certificate);
					}
					employeeDetail.setCertificates(certificateDto);
				} else {
					employeeDetail.setCertificates(employeeDetail.getCertificates());
				}

				if (!CollectionUtils.isEmpty(employeeDetailsDto.getSkillsDetails())) {
					List<SkillsDetailsDto> skillsDetailsDtos = new ArrayList<>();
					for (SkillsDetailsDto skillsDetails : employeeDetailsDto.getSkillsDetails()) {
						SkillsDetailsDto skillsDetailsDto = new SkillsDetailsDto();
						BeanUtils.copyProperties(skillsDetails, skillsDetailsDto);
						skillsDetailsDtos.add(skillsDetailsDto);
					}
					employeeDetail.setSkillsDetails(skillsDetailsDtos);
				} else {
					employeeDetail.setSkillsDetails(employeeDetail.getSkillsDetails());
				}

				if (!CollectionUtils.isEmpty(employeeDetailsDto.getEmploymentDetails())) {
					List<EmploymentDetailsDto> employment = new ArrayList<>();
					for (EmploymentDetailsDto emplDetails : employeeDetailsDto.getEmploymentDetails()) {
						EmploymentDetailsDto employmentDetailsDto = new EmploymentDetailsDto();
						BeanUtils.copyProperties(emplDetails, employmentDetailsDto);
						employment.add(employmentDetailsDto);
					}
					employeeDetail.setEmploymentDetails(employment);
				} else {
					employeeDetail.setEmploymentDetails(employeeDetail.getEmploymentDetails());
				}

				if (employeeDetailsDto.getBusinessUnit() != null && !employeeDetailsDto.getBusinessUnit().equals("")) {
					Optional<BusinessUnit> businessUnit = businessUnitRepository
							.findById(employeeDetailsDto.getBusinessUnit());
					if (businessUnit.isPresent()) {
						employeeDetail.setBusinessUnit(businessUnit.get());
					}
				} else {
					employeeDetail.setBusinessUnit(employeeDetail.getBusinessUnit());
				}

				if (employeeDetailsDto.getMasterDepartment() != null
						&& !employeeDetailsDto.getMasterDepartment().equals("")) {
					Optional<MasterDepartment> masterDepartment = masterDepartmentRepository
							.findById(employeeDetailsDto.getMasterDepartment());
					if (masterDepartment.isPresent()) {
						employeeDetail.setMasterDepartment(masterDepartment.get());
					}
				} else {
					employeeDetail.setMasterDepartment(employeeDetail.getMasterDepartment());
				}

				if (!employeeDetailsDto.isStatusFlag()) {
					employeeDetail.setStatusFlag(false);
				}

				/*
				 * if(employeeDetailsDto.getPassword() != null &&
				 * !employeeDetailsDto.getPassword().equals("")) {
				 * employeeDetail.setPassword(this.encode(employeeDetailsDto.getPassword())); }
				 * else { employeeDetail.setPassword(employeeDetail.getPassword()); }
				 */
				/*
				 * if(employeeDetailsDto.getRole() != null &&
				 * !employeeDetailsDto.getRole().equals("")) { Optional<Role>
				 * role=roleRepository.findById(employeeDetailsDto.getRole());
				 * if(role.isPresent()) { employeeDetail.setRole(role.get()); } } else {
				 * employeeDetail.setRole(employeeDetail.getRole()); }
				 */
				if (employeeDetailsDto.getRoleId() != 0) {
					if (employeeDetailsDto.getRoleId() == 1) {
						employeeDetail.setRole(GeneralConstants.ADMIN);
					} else if (employeeDetailsDto.getRoleId() == 2) {
						employeeDetail.setRole(GeneralConstants.MANAGER);
					} else if (employeeDetailsDto.getRoleId() == 3) {
						employeeDetail.setRole(GeneralConstants.TEAM_LEADER);
					} else if (employeeDetailsDto.getRoleId() == 4) {
						employeeDetail.setRole(GeneralConstants.TEAM_MEMBER);
					} else if (employeeDetailsDto.getRoleId() == 5) {
						employeeDetail.setRole(GeneralConstants.BUSINESS_UNIT_HEAD);
					} else if (employeeDetailsDto.getRoleId() == 6) {
						employeeDetail.setRole(GeneralConstants.DEPARTMENT_HEAD);
					}
				} else {
					employeeDetail.setRole(employeeDetail.getRole());
				}

				/*
				 * if(employeeDetailsDto.getReportingManager()!=null &&
				 * !employeeDetailsDto.getReportingManager().equals("")) {
				 * Optional<ReportingPerson> reportingPerson=
				 * reportingPersonRepository.findById(employeeDetailsDto.getReportingManager());
				 * if(reportingPerson.isPresent()) {
				 * employeeDetail.setReportingManager(reportingPerson.get().getReportingPerson()
				 * ); } }else {
				 * employeeDetail.setReportingManager(employeeDetail.getReportingManager()); }
				 */

				if (!CollectionUtils.isEmpty(employeeDetailsDto.getReportingManager())) {
					List<String> reportingPersons = new ArrayList<>();
//					employeeDetailsDto.getReportingManager().forEach(rm -> {
//						Optional<EmployeeDetails> reportingPerson = employeeDetailsRepository.findById(rm);
//						if(reportingPerson.isPresent()) {
//							reportingPersons.add(reportingPerson.get().getEmployeeCode()+"-"+reportingPerson.get().getName());
//						}
//					});
					reportingPersons.addAll(employeeDetailsDto.getReportingManager());
				} else {
					employeeDetail.setReportingManager(employeeDetail.getReportingManager());
				}

//				Optional<EmploymentTypes> employmentType = employmentTypeRepository.findById(employeeDetailsDto.getEmployeeCategory());

				if (employeeDetailsDto.getEmailId() != null && !employeeDetailsDto.getEmailId().equals("")) {
					employeeDetail.setEmailId(employeeDetailsDto.getEmailId());
				} else {
					employeeDetail.setEmailId(employeeDetail.getEmailId());
				}

				List<InsuranceCoverageDto> insuranceCoverageList = new ArrayList<>();
				if (!CollectionUtils.isEmpty(employeeDetailsDto.getInsuranceCoverages())) {
					for (InsuranceCoverageDto insuranceCoverageDto : employeeDetailsDto.getInsuranceCoverages()) {
						InsuranceCoverageDto insuranceCoverage = new InsuranceCoverageDto();
						insuranceCoverage.setCoverageName(insuranceCoverageDto.getCoverageName());
						insuranceCoverage.setRelationship(insuranceCoverageDto.getRelationship());
						insuranceCoverageList.add(insuranceCoverage);
					}
					employeeDetail.setInsuranceCoverages(insuranceCoverageList);
				} else {
					employeeDetail.setInsuranceCoverages(employeeDetail.getInsuranceCoverages());
				}

				employeeDetailsRepository.save(employeeDetail);
				entity.put("Status", "200");
				entity.put("Message", "EmployeeDetails Updated Successfully");
				entity.put("Data", employeeDetail);

			} else {
				entity.put("Status", "405");
				entity.put("Message", "couldn't updated");
			}

		}

		return entity;
	}

	public String encode(String text) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
		return bCryptPasswordEncoder.encode(text);

	}

	public boolean decode(String text, String text2) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(text, text2);

	}

	public LinkedHashMap<String, Object> login(String employeeName, String password) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findByNameOrEmployeeCode(employeeName,
				employeeName);

		if (employeeDetails.size() > 0) {
			for (EmployeeDetails employeeDetail : employeeDetails) {
				// System.out.println(user[2].toString());
				// System.out.println(this.encode(password.trim()));
				if (employeeDetail.isStatusFlag()) {
					if (password.equals(employeeDetail.getPassword())) {
						entity.put("status", "200");
						entity.put("message", "Successfully Login");
						entity.put("signupEmployeeId", employeeDetail.getId());
						entity.put("role", employeeDetail.getRole());
						entity.put("roleId", employeeDetail.getRoleId());
						entity.put("employeeCode", employeeDetail.getEmployeeCode());
						entity.put("employeeName", employeeDetail.getName());

					} else {
						entity.put("status", "400");
						entity.put("message", "Please Check Password");
					}
				} else {
					entity.put("status", "400");
					entity.put("message", "User is De-Activated");

				}

			}
		} else {
			entity.put("status", "400");
			entity.put("message", "No User Found");
		}
		return entity;
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null || srcValue.toString().equals("0") || srcValue.equals(""))
				emptyNames.add(pd.getName());
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public LinkedHashMap<String, Object> getEmployeeById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(id);
		if (employeeDetails.isPresent()) {

			List<String> reportingPerson = new ArrayList<>();
			if (!CollectionUtils.isEmpty(employeeDetails.get().getReportingManager())) {
				for (String rp : employeeDetails.get().getReportingManager()) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(rp);
					if (employee.isPresent()) {
						reportingPerson
								.add(rp + "," + employee.get().getEmployeeCode() + "-" + employee.get().getName());
					}
				}
			}
			employeeDetails.get().setReportingManager(reportingPerson);

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", employeeDetails);
		} else {
			entity.put("Status", "405");
			entity.put("Message", "Data not present");

		}

		return entity;
	}

	public LinkedHashMap<String, Object> getRoleList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<Role> role = roleRepository.findAll();

		if (role.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", role);
		} else {
			entity.put("Status", "400");
			entity.put("Message", " No Data is present");
		}

		return entity;
	}

	/*
	 * public LinkedHashMap<String, Object> getEmployeeList() {
	 * LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
	 * 
	 * List<EmployeesListDto> employeesListDtos = new ArrayList<>();
	 * 
	 * List<EmployeeDetails> employee = employeeDetailsRepository.findAll();
	 * 
	 * for (EmployeeDetails employeeDetails : employee) { EmployeesListDto
	 * employeesListDto = new EmployeesListDto(); if(employeeDetails.getId()!=null)
	 * { employeesListDto.setName(employeeDetails.getName()+"("+
	 * employeeDetails.getEmployeeCode()+")"); //
	 * employeesListDto.setRole(employeeDetails.getEmploymentDesignation());
	 * employeesListDto.setEmailId(employeeDetails.getEmailId());
	 * if(employeeDetails.getId()!=null && employeeDetails.getName()!=null) {
	 * employeesListDto.setEmployeeStatusFlag(true); }else {
	 * employeesListDto.setEmployeeStatusFlag(false); }
	 * employeesListDtos.add(employeesListDto);
	 * 
	 * entity.put("Status", "200"); entity.put("Message", "Data is present");
	 * entity.put("Data", employeesListDtos); } } return entity; }
	 */

	/*
	 * public LinkedHashMap<String, Object> getEmployeesByDepartment(String
	 * department) { LinkedHashMap<String, Object> entity = new
	 * LinkedHashMap<String, Object>();
	 * 
	 * List<EmployeesListDto> employeesListDtos = new ArrayList<>();
	 * 
	 * List<EmployeeDetails> employee =
	 * employeeDetailsRepository.findByDepartment(department);
	 * 
	 * for (EmployeeDetails employeeDetails : employee) { EmployeesListDto
	 * employeesListDto = new EmployeesListDto();
	 * employeesListDto.setEmployeename(employeeDetails.getFirstname()+
	 * employeeDetails.getEmployeeCode());
	 * employeesListDto.setRole(employeeDetails.getEmploymentDesignation());
	 * employeesListDto.setEmailId(employeeDetails.getEmailId());
	 * employeesListDto.setEmployeeStatus(employeeDetails.getEmployeeStatus().
	 * getEmployeeStatus()); employeesListDtos.add(employeesListDto); }
	 * entity.put("Status:", "200"); entity.put("Message:", "Data is present");
	 * entity.put("Data:", employeesListDtos);
	 * 
	 * return entity; }
	 * 
	 * public LinkedHashMap<String, Object> getEmployeesByEmployeeStatus(String
	 * employeeStatus) { LinkedHashMap<String, Object> entity = new
	 * LinkedHashMap<String, Object>();
	 * 
	 * List<EmployeesListDto> employeesListDtos = new ArrayList<>();
	 * 
	 * List<EmployeeDetails> employee =
	 * employeeDetailsRepository.findByEmployeeStatus(employeeStatus);
	 * 
	 * for (EmployeeDetails employeeDetails : employee) { EmployeesListDto
	 * employeesListDto = new EmployeesListDto();
	 * employeesListDto.setEmployeename(employeeDetails.getFirstname()+
	 * employeeDetails.getEmployeeCode());
	 * employeesListDto.setRole(employeeDetails.getEmploymentDesignation());
	 * employeesListDto.setEmailId(employeeDetails.getEmailId());
	 * employeesListDto.setEmployeeStatus(employeeDetails.getEmployeeStatus().
	 * getEmployeeStatus()); employeesListDtos.add(employeesListDto); }
	 * entity.put("Status:", "200"); entity.put("Message:", "Data is present");
	 * entity.put("Data:", employeesListDtos);
	 * 
	 * return entity; }
	 */
	/*
	 * public LinkedHashMap<String, Object> getEmployeesByEmployeeRole(String
	 * employeeRole) { LinkedHashMap<String, Object> entity = new
	 * LinkedHashMap<String, Object>();
	 * 
	 * List<EmployeesListDto> employeesListDtos = new ArrayList<>();
	 * 
	 * List<EmployeeDetails> employee =
	 * employeeDetailsRepository.findByEmployeeRole(employeeRole);
	 * 
	 * for (EmployeeDetails employeeDetails : employee) { EmployeesListDto
	 * employeesListDto = new EmployeesListDto(); //
	 * employeesListDto.setEmployeename(employeeDetails.getName()); //
	 * employeesListDto.setRole(employeeDetails.getEmployeeRole());
	 * if(employeeDetails.getDateOfJoining() != null) {
	 * employeesListDto.setDateOfJoining(employeeDetails.getDateOfJoining().toString
	 * ()); } else { employeesListDto.setDateOfJoining(null); }
	 * employeesListDto.setEmployeeStatus(employeeDetails.getEmployeeStatus());
	 * employeesListDtos.add(employeesListDto); } entity.put("Status:", "200");
	 * entity.put("Message:", "Data is present"); entity.put("Data:",
	 * employeesListDtos);
	 * 
	 * return entity; }
	 */
	/*
	 * public LinkedHashMap<String, Object>
	 * getAllEmployeeDetails(EmployeeDetailsFilterDto employeeDetailsFilterDto) {
	 * LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
	 * 
	 * Query query=new Query();
	 * 
	 * if(employeeDetailsFilterDto.getName()!=null &&
	 * !employeeDetailsFilterDto.getName().equals("")) {
	 * query.addCriteria(Criteria.where("name").is(employeeDetailsFilterDto.getName(
	 * ))); }
	 * 
	 * if(employeeDetailsFilterDto.getDepartment()!=null &&
	 * !employeeDetailsFilterDto.getDepartment().equals("")) {
	 * query.addCriteria(Criteria.where("department").is(employeeDetailsFilterDto.
	 * getDepartment())); }
	 * 
	 * if(employeeDetailsFilterDto.getRole()!=null &&
	 * !employeeDetailsFilterDto.getRole().equals("")) {
	 * query.addCriteria(Criteria.where("role").is(employeeDetailsFilterDto.getRole(
	 * ))); }
	 * 
	 * if(employeeDetailsFilterDto.getEmployeeStatus()!=null &&
	 * !employeeDetailsFilterDto.getEmployeeStatus().equals("")) {
	 * query.addCriteria(Criteria.where("employeeStatus").is(
	 * employeeDetailsFilterDto.getEmployeeStatus())); } List<EmployeeDetails>
	 * employeeDetails = mongoTemplate.find(query, EmployeeDetails.class);
	 * entity.put("Status:", "200"); entity.put("Message:", "Data is present");
	 * entity.put("Data:", employeeDetails);
	 * 
	 * return entity; }
	 */

	public LinkedHashMap<String, Object> getAllEmployDetailsList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		new ArrayList<>();

		List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findAll();

		if (employeeDetails.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", employeeDetails);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllGenderList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		// List<EmployeeDetailsDto> employeeDetailsDtos = new ArrayList<>();

		List<Gender> gender = genderRepository.findAll();

		if (gender.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", gender);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllMartialStatusList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		// List<EmployeeDetailsDto> employeeDetailsDtos = new ArrayList<>();

		List<MartialStatus> martialStatus = martialStatusRepository.findAll();

		if (martialStatus.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", martialStatus);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllReportingPersonList(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		// List<EmployeeDetailsDto> employeeDetailsDtos = new ArrayList<>();
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		if (employee.isPresent()) {
			List<EmployeeDetails> reportingPerson = employeeDetailsRepository.findAll();

			List<EmployeeNameDto> employeeNameDtos = new ArrayList<>();

			for (EmployeeDetails employeeDetails : reportingPerson) {
//			if (employeeDetails.getRole().equals(GeneralConstants.TEAM_LEADER)
//					|| employeeDetails.getRole().equals(GeneralConstants.MANAGER)) {
				if (!employeeDetails.getId().equals(id)) {
					EmployeeNameDto employeeNameDto = new EmployeeNameDto();
					employeeNameDto.setId(employeeDetails.getId());
					employeeNameDto.setName(employeeDetails.getEmployeeCode() + "-" + employeeDetails.getName());
					employeeNameDtos.add(employeeNameDto);
				}
			}

			if (employeeNameDtos.size() > 0) {
				entity.put("Status", "200");
				entity.put("Message", "Data is present");
				entity.put("Data", employeeNameDtos);
			} else {
				entity.put("Status", "400");
				entity.put("Message", "No Data is present");
			}
		} else {
			entity.put("Status", "400");
			entity.put("Message", "Invalid employee id");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllEmployeeStatusList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		// List<EmployeeDetailsDto> employeeDetailsDtos = new ArrayList<>();

		List<EmployeeStatus> employeeStatus = employeeStatusRepository.findAll();

		if (employeeStatus.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", employeeStatus);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllEmployeeCategoryList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		// List<EmployeeDetailsDto> employeeDetailsDtos = new ArrayList<>();

		List<EmployeeCategory> employeeCategories = employeeCategoryRepository.findAll();

		if (employeeCategories.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", employeeCategories);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> saveOrUpdateRole(Role role) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		if (role.getId() == null) {
			Role roles = new Role();
			roles.setRole(role.getRole());
			roleRepository.save(roles);

			entity.put("Status", "200");
			entity.put("Message", "Data is Saved");
			entity.put("Data", roles);
		} else {
			Optional<Role> roles = roleRepository.findById(role.getId());
			if (roles.get().getId() != null) {
				Role Role = new Role();
				Role.setRole(role.getRole());
				roleRepository.save(Role);

				entity.put("Status", "200");
				entity.put("Message", "Data is Updated");
				entity.put("Data", Role);

			} else {
				entity.put("Status", "400");
				entity.put("Message", "couldn't Updated");
			}

		}
		return entity;
	}

	public LinkedHashMap<String, Object> getAllEmployeeNameList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<EmployeeNameDto> employeeNameDtos = new ArrayList<>();

		List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findAll();

		for (EmployeeDetails employeeName : employeeDetails) {
			EmployeeNameDto employeeNameDto = new EmployeeNameDto();
			employeeNameDto.setId(employeeName.getId());
			employeeNameDto.setName(employeeName.getEmployeeCode() + "-" + employeeName.getName());
			employeeNameDtos.add(employeeNameDto);
		}

		if (employeeNameDtos.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", employeeNameDtos);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}
		return entity;
	}

	public LinkedHashMap<String, Object> getAllManagersList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<EmployeeNameDto> employeeNameDtos = new ArrayList<>();

		List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findAll();

		for (EmployeeDetails manager : employeeDetails) {
			if (manager.getRole().equals(GeneralConstants.MANAGER)) {
				EmployeeNameDto employeeNameDto = new EmployeeNameDto();
				employeeNameDto.setId(manager.getId());
				employeeNameDto.setName(manager.getEmployeeCode() + "-" + manager.getName());
				employeeNameDtos.add(employeeNameDto);
			}
		}

		if (employeeNameDtos.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", employeeNameDtos);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}
		return entity;
	}

	public LinkedHashMap<String, Object> getAllBloodGroup() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<BloodGroup> bloodGroups = bloodGroupRepository.findAll();

		if (bloodGroups.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", bloodGroups);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllProficiency() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<Proficiency> proficiencies = proficiencyRepository.findAll();

		if (proficiencies.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", proficiencies);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllBoards() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<Board> boards = boardRepository.findAll();

		if (boards.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", boards);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> changePassword(ChangePassword changePassword) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();

		Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository.findById(changePassword.getId());

		if (employeeDetail.isPresent()) {

			if (employeeDetail.get().isStatusFlag()) {
				if (employeeDetail.get().getPassword().equals(changePassword.getOldPassword())) {

					if (changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {

						employeeDetail.get().setPassword(changePassword.getNewPassword());
						employeeDetailsRepository.save(employeeDetail.get());

						entity.put("status", "200");
						entity.put("message", "password changes successfully");

					} else {
						entity.put("status", "400");
						entity.put("message", "Password Mis-match");
					}

				} else {
					entity.put("status", "400");
					entity.put("message", "Old Password Mis-match");
				}
			} else {
				entity.put("status", "400");
				entity.put("message", "User is De-Activated");

			}

		} else {
			entity.put("status", "400");
			entity.put("message", "No User Found");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getEmployeesByManager(String id, String businessUnit, String department) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		List<EmployeeDetails> employeeDetails = null;
		Optional<EmployeeDetails> employeeOp = employeeDetailsRepository.findById(id);
		if (employeeOp.get().getRole().equals(GeneralConstants.ADMIN)
				|| employeeOp.get().getRole().equals(GeneralConstants.HR)) {
			if ((businessUnit == null || businessUnit.isEmpty()) && (department == null || department.isEmpty())) {
				employeeDetails = employeeDetailsRepository.findAll();
			} else if ((businessUnit != null && !businessUnit.isEmpty())
					&& (department == null || department.isEmpty())) {
				employeeDetails = getEmployeesByBusinessUnitAndDepartment(businessUnit, department);
			} else if ((businessUnit != null && !businessUnit.isEmpty())
					&& (department != null && !department.isEmpty())) {
				employeeDetails = getEmployeesByBusinessUnitAndDepartment(businessUnit, department);
			}
		} else if (employeeOp.get().getRole().equals(GeneralConstants.BUSINESS_UNIT_HEAD)) {
			if ((businessUnit == null || businessUnit.isEmpty()) && (department == null || department.isEmpty())) {
				employeeDetails = getAllEmployeesByBusinessUnit(id);
			} else if ((businessUnit != null && !businessUnit.isEmpty())
					&& (department == null || department.isEmpty())) {
				employeeDetails = getEmployeesByBusinessUnitAndDepartment(businessUnit, department);
			} else if ((businessUnit != null && !businessUnit.isEmpty())
					&& (department != null && !department.isEmpty())) {
				employeeDetails = getEmployeesByBusinessUnitAndDepartment(businessUnit, department);
			}
		} else if (employeeOp.get().getRole().equals(GeneralConstants.DEPARTMENT_HEAD)) {
			employeeDetails = getAllEmployeesByMasterDepartment(id);
		} else if (employeeOp.get().getRole().equals(GeneralConstants.MANAGER)) {
			employeeDetails = getAllEmployeesByReportingPerson(id);
		}
		if (employeeOp.isPresent()) {
			if (!CollectionUtils.isEmpty(employeeDetails)) {
				entity.put("Status", "200");
				entity.put("Message", "Data is present");
				entity.put("Data", employeeDetails);
			}
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}
		return entity;
	}

	@Override
	public List<EmployeeDetails> getAllEmployeesByReportingPerson(String id) {
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		List<EmployeeDetails> employees = new ArrayList<>();
		if (employee.isPresent()) {
			List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findAll();
			if (!CollectionUtils.isEmpty(employeeDetails)) {
				for (EmployeeDetails employeeDetail : employeeDetails) {
					if (!CollectionUtils.isEmpty(employeeDetail.getReportingManager())) {
						for (String rm : employeeDetail.getReportingManager()) {
							if (employee.get().getId().equals(rm)) {
								employees.add(employeeDetail);
							}
						}
					}
				}
			}
		}
		if (employees.size() > 0) {
			return employees;
		} else {
			return null;
		}
	}

	/*---- jwt login token generation ----*/

	// private static String secret="This_is_secret";

	private String doGenerateToken(EmployeeDetails employeeDetails) {

		Claims claims = Jwts.claims().setSubject(employeeDetails.getId());
//		Jwts.claims().setSubject(employeeDetails.getEmployeeCode());
		claims.put("emailId", employeeDetails.getEmailId());
		claims.put("id", employeeDetails.getId());
		claims.put("employeeCode", employeeDetails.getEmployeeCode());
		claims.put("role", employeeDetails.getRole());
		claims.put("name", employeeDetails.getName());

		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET).compact();

	}

	/*---- jwt login validation ----*/

	public UsernamePasswordAuthenticationToken validateLogin(String emailId, String password) {

		String employee = null;
		EmployeeDetails details = null;
		boolean emailAuthFlag = false;

		if (emailId != null && !emailId.isEmpty() && password != null && !password.isEmpty()) {

			emailAuthFlag = true;
			details = employeeDetailsRepository.findByEmailId(emailId);
			if (details == null) {
				throw new UsernameNotFoundException(emailId + "Not found");
			}
		}

		if (!details.getRole().equals(GeneralConstants.HR) && (details.isStatusFlag() == false)) {
			throw new UsernameNotFoundException("Account not approved");
		}

		String role = details.getRole();

		details.getId();

		if ((emailAuthFlag == false) || (details.getPassword().equals(password))) {
			if (details.getEmployeeToken() == null || details.getEmployeeToken().isEmpty()) {
				employee = doGenerateToken(details);
				details.setEmployeeToken(employee);
				details.setRole(role);
			} else {
				employee = details.getEmployeeToken();
			}

			request.getSession().setAttribute("LoggedIn", "TRUE");
			request.getSession().setAttribute("EmployeeName", details.getName());
			request.getSession().setAttribute("Role", details.getRole());
			request.getSession().setAttribute("EmployeeCode", details.getEmployeeCode());
			request.getSession().setAttribute("Id", details.getId());

			employeeDetailsRepository.save(details);

			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(role));

			User user = new User(employee, "", authorities);
			return new UsernamePasswordAuthenticationToken(user, null, authorities);
		}

		if (emailAuthFlag == false) {
			throw new UsernameNotFoundException("Incorrect User");
		} else {
			throw new UsernameNotFoundException("Incorrect Password");

		}
	}

	public LinkedHashMap<String, Object> getMyAccount() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(utility.getEmployeeId());
		if (employeeDetails.isPresent()) {

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", employeeDetails);
		} else {
			entity.put("Status", "405");
			entity.put("Message", "Data not present");

		}

		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> excelUpload(MultipartFile file) throws IOException {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Workbook workbook = new XSSFWorkbook(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		DataFormatter dataFormatter = new DataFormatter();
		int i = 0;
		int j = 0;
		List<String> errors = new ArrayList<>();
		DateTimeFormatter format1 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<String> empCode = new ArrayList<>();
		boolean flag = true;
		int rowCount = sheet.getLastRowNum() + 1;
		boolean rpFlag = true;
		for (int k = 1; k <= rowCount; k++) {
			Row row = sheet.getRow(k);
			List<String> error = new ArrayList<>();
			EmployeeDetails employee = new EmployeeDetails();
			List<InsuranceCoverageDto> insuranceCoverageDtos = new ArrayList<>();
			InsuranceCoverageDto insuranceCoverage = new InsuranceCoverageDto();
			flag = true;
			if (row != null) {
				for (Cell cell : row) {
					String cellValue = dataFormatter.formatCellValue(cell);
					if (cell.getColumnIndex() == 0) {
						if (cellValue == null || cellValue.isEmpty()) {
							flag = false;
						}
					}
					if (flag == true) {
						if (cell.getColumnIndex() == 1) {
							if (cellValue != null && !cellValue.isEmpty()) {
								Optional<EmployeeDetails> emp = employeeDetailsRepository.findByEmployeeCode(cellValue);
								if (!emp.isPresent()) {
									employee.setEmployeeCode(cellValue);
									empCode.add(cellValue);
								} else {
									error.add("Employee Already exist with the Code " + cellValue + " at row"
											+ row.getRowNum() + " at Col " + cell.getColumnIndex());
								}
							} else {
								error.add("Enter Employee Code at row " + row.getRowNum() + " at Col "
										+ cell.getColumnIndex());
							}
						}
						if (cell.getColumnIndex() == 2) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setName(cellValue);
							}
						}
						if (cell.getColumnIndex() == 3) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setLastName(cellValue);
							}
						}
						if (cell.getColumnIndex() == 4) {
							if (cellValue != null && !cellValue.isEmpty()) {
								LocalDate date3 = LocalDate.parse(format.format(format1.parse(cellValue)).toString(),
										format);
								employee.setDateOfJoining(date3);
							}
						}
						if (cell.getColumnIndex() == 5) {
							if (cellValue != null && !cellValue.isEmpty()) {
								EmployeeDetails emp = employeeDetailsRepository.findByEmailId(cellValue);
								if (emp == null) {
									employee.setEmailId(cellValue);
								} else {
									error.add("Email Id Already exist " + cellValue + " at row" + row.getRowNum()
											+ " at Col " + cell.getColumnIndex());
								}
							}
						}
						if (cell.getColumnIndex() == 6) {
							if (cellValue != null && !cellValue.isEmpty()) {
//								Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
//								Matcher match = ptrn.matcher(cellValue);
								employee.setMobile(cellValue);
							}
						}
						if (cell.getColumnIndex() == 7) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setPhone(cellValue);
							}
						}
						if (cell.getColumnIndex() == 8) {
							if (cellValue != null && !cellValue.isEmpty()) {
								Optional<Gender> gender = genderRepository.findAll().stream()
										.filter(e -> e.getGender().equalsIgnoreCase(cellValue)).findFirst();
								if (gender.isPresent()) {
									employee.setGender(gender.get());
								}
							}
						}
						if (cell.getColumnIndex() == 9) {
							if (cellValue != null && !cellValue.isEmpty()) {
								LocalDate date3 = LocalDate.parse(format.format(format1.parse(cellValue)).toString(),
										format);
								employee.setDateOfBirth(date3);
							}
						}
						if (cell.getColumnIndex() == 10) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setBloodGroup(cellValue);
							}
						}
						if (cell.getColumnIndex() == 11) {
							if (cellValue != null && !cellValue.isEmpty()) {
								Optional<MartialStatus> maritalStatus = martialStatusRepository.findAll().stream()
										.filter(e -> e.getStatus().equalsIgnoreCase(cellValue)).findFirst();
								if (maritalStatus.isPresent()) {
									employee.setMartialStatus(maritalStatus.get());
								}
							}
						}
						if (cell.getColumnIndex() == 12) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setEmployeeLocation(cellValue);
							}
						}
						if (cell.getColumnIndex() == 13) {
							if (cellValue != null && !cellValue.isEmpty()) {
								Optional<Designations> designations = designationsRepository.findAll().stream()
										.filter(e -> e.getDesignationName().equalsIgnoreCase(cellValue)).findFirst();
								if (designations.isPresent()) {
									employee.setEmploymentDesignation(designations.get());
								} else {
									error.add("Enter Valid Designation at row " + row.getRowNum() + " at Col "
											+ cell.getColumnIndex());
								}
							} else {
								error.add("Enter Employee Designation at row " + row.getRowNum() + " at Col "
										+ cell.getColumnIndex());
							}
						}
						if (cell.getColumnIndex() == 14) {
							if (cellValue != null && !cellValue.isEmpty()) {
								if (cellValue.equalsIgnoreCase("Permanent")) {
									System.out.println(cellValue);
								}
								Optional<EmploymentTypes> category = employmentTypeRepository.findAll().stream()
										.filter(e -> e.getEmploymentTypesName().equalsIgnoreCase(cellValue))
										.findFirst();

								if (category.isPresent()) {
									employee.setEmployeeCategory(category.get());
								} else {
									error.add("Enter Valid Employee Category at row " + row.getRowNum() + " at Col "
											+ cell.getColumnIndex());
								}
							} else {
								error.add("Enter Employee Category at row " + row.getRowNum() + " at Col "
										+ cell.getColumnIndex());
							}
						}
						if (cell.getColumnIndex() == 15) {
							if (cellValue != null && !cellValue.isEmpty()) {
								Optional<EmployeeStatus> status = employeeStatusRepository.findAll().stream()
										.filter(e -> e.getEmployeeStatus().equalsIgnoreCase(cellValue)).findAny();

								if (status.isPresent()) {
									employee.setEmployeeStatus(status.get());
								} else {
									error.add("Enter Valid Employee Status at row " + row.getRowNum() + " at Col "
											+ cell.getColumnIndex());
								}
							} else {
								error.add("Enter Employee Status at row " + row.getRowNum() + " at Col "
										+ cell.getColumnIndex());
							}
						}
						if (cell.getColumnIndex() == 16) {
							if (cellValue != null && !cellValue.isEmpty()) {
								if (cellValue.equalsIgnoreCase("ADMIN") || cellValue.equalsIgnoreCase("MANAGER")
										|| cellValue.equalsIgnoreCase("TEAM_LEADER")
										|| cellValue.equalsIgnoreCase("TEAM_MEMBER")
										|| cellValue.equalsIgnoreCase("BUSINESS_UNIT_HEAD")
										|| cellValue.equalsIgnoreCase("DEPARTMENT_HEAD")) {
									if (cellValue.equalsIgnoreCase("ADMIN")) {
										employee.setRole(GeneralConstants.ADMIN);
										employee.setRoleId(1);
									} else if (cellValue.equalsIgnoreCase("MANAGER")) {
										employee.setRole(GeneralConstants.MANAGER);
										employee.setRoleId(2);
									} else if (cellValue.equalsIgnoreCase("TEAM_LEADER")) {
										employee.setRole(GeneralConstants.TEAM_LEADER);
										employee.setRoleId(3);
									} else if (cellValue.equalsIgnoreCase("TEAM_MEMBER")) {
										employee.setRole(GeneralConstants.TEAM_MEMBER);
										employee.setRoleId(4);
									} else if (cellValue.equalsIgnoreCase("BUSINESS_UNIT_HEAD")) {
										employee.setRole(GeneralConstants.BUSINESS_UNIT_HEAD);
										employee.setRoleId(5);
									} else if (cellValue.equalsIgnoreCase("DEPARTMENT_HEAD")) {
										employee.setRole(GeneralConstants.DEPARTMENT_HEAD);
										employee.setRoleId(6);
									}
								} else {
									error.add("Enter valid Role at row " + row.getRowNum() + " at Col "
											+ cell.getColumnIndex());
								}
							} else {
								error.add("Enter Employee Role at row " + row.getRowNum() + " at Col "
										+ cell.getColumnIndex());
							}
						}
						if (cell.getColumnIndex() == 17) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setFathername(cellValue);
							}
						}
						if (cell.getColumnIndex() == 18) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setEmergencyContactNumber(cellValue);
							}
						}
						if (cell.getColumnIndex() == 19) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setEmergencyContactRelation(cellValue);
							}
						}
						if (cell.getColumnIndex() == 20) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setPersonalEmailId(cellValue);
							}
						}
						if (cell.getColumnIndex() == 23) {
							if (cellValue != null && !cellValue.isEmpty()) {
								Optional<BusinessUnit> businessUnit = businessUnitRepository.findAll().stream()
										.filter(e -> e.getBusinessUnitName().equalsIgnoreCase(cellValue)).findFirst();
								if (businessUnit.isPresent()) {
									employee.setBusinessUnit(businessUnit.get());
								} else {
									error.add("Enter Valid Business Unit at row " + row.getRowNum() + " at Col "
											+ cell.getColumnIndex());
								}
							} else {
								error.add("Enter Business Unit at row " + row.getRowNum() + " at Col "
										+ cell.getColumnIndex());
							}
						}
						if (cell.getColumnIndex() == 24) {
							if (cellValue != null && !cellValue.isEmpty()) {
								Optional<MasterDepartment> department = masterDepartmentRepository.findAll().stream()
										.filter(e -> e.getDepartmentName().equalsIgnoreCase(cellValue)).findFirst();
								if (department.isPresent()) {
									employee.setMasterDepartment(department.get());
								} else {
									error.add("Enter Valid Department at row " + row.getRowNum() + " at Col "
											+ cell.getColumnIndex());
								}
							} else {
								error.add("Enter Department at row " + row.getRowNum() + " at Col "
										+ cell.getColumnIndex());
							}
						}
						if (cell.getColumnIndex() == 25) {
							if (cellValue != null && !cellValue.isEmpty()) {
								if (!CollectionUtils.isEmpty(empCode)) {
									List<String> code = empCode.stream().filter(e -> e.equals(cellValue))
											.collect(Collectors.toList());
									if (!CollectionUtils.isEmpty(code)) {
										String id = code.get(code.size() - 1);
										Optional<EmployeeDetails> emp = employeeDetailsRepository
												.findByEmployeeCode(id);
										if (emp.isPresent()) {
											List<String> rp = new ArrayList<>();
											rp.add(emp.get().getId());
											employee.setReportingManager(rp);
										} else {
											error.add(
													"Enter Valid Reporting Manager... Because there is no Employee present in the previous record with "
															+ cellValue + " at row" + row.getRowNum() + " at Col "
															+ cell.getColumnIndex());
										}
									} else {
										error.add(
												"Enter Valid Reporting Manager... Because there is no Employee present in the previous record with "
														+ cellValue + " at row" + row.getRowNum() + " at Col "
														+ cell.getColumnIndex());
									}
								}
							}
						}
						if (cell.getColumnIndex() == 26) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setAadhaar(cellValue);
							}
						}
						if (cell.getColumnIndex() == 27) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setPan(cellValue);
							}
						}
						if (cell.getColumnIndex() == 28) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setPassport(cellValue);
							}
						}
						if (cell.getColumnIndex() == 29) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setPassportValidity(cellValue);
							}
						}
						if (cell.getColumnIndex() == 30) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setPfNo(cellValue);
							}
						}
						if (cell.getColumnIndex() == 31) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setEsiNo(cellValue);
							}
						}
						if (cell.getColumnIndex() == 32) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setGroupInsurance(cellValue);
							}
						}
						if (cell.getColumnIndex() == 33) {
							if (cellValue != null && !cellValue.isEmpty()) {
								employee.setInsuranceValidity(cellValue);
							}
						}

						if (cell.getColumnIndex() == 34) {
							if (cellValue != null && !cellValue.isEmpty()) {
								insuranceCoverage.setCoverageName(cellValue);
							}
						}
						if (cell.getColumnIndex() == 35) {
							if (cellValue != null && !cellValue.isEmpty()) {
								insuranceCoverage.setRelationship(cellValue);
							}
						}
					}
				}
				if (error.size() > 0 && flag == true) {
					errors.addAll(error);
					j++;
				}
				if (error.size() == 0 && flag == true) {
					if ((insuranceCoverage.getCoverageName() != null && !insuranceCoverage.getCoverageName().isEmpty())
							|| (insuranceCoverage.getRelationship() != null
									&& !insuranceCoverage.getRelationship().isEmpty())) {
						insuranceCoverageDtos.add(insuranceCoverage);
						employee.setInsuranceCoverages(insuranceCoverageDtos);
					}
					employee.setStatusFlag(true);
					employee.setPassword(employee.getEmployeeCode());
					employeeDetailsRepository.save(employee);
					i++;
					if (rpFlag == true) {
						if (employee.getId() != null && !employee.getId().isEmpty()) {
							List<String> rp = new ArrayList<>();
							rp.add(employee.getId());
							employee.setReportingManager(rp);
							employeeDetailsRepository.save(employee);
							rpFlag = false;
						}
					}
				}
			}
		}
		if (errors.size() > 0) {
			entity.put("Status", "200");
			entity.put("Saved Employees", i);
			entity.put("Unsaved Employees", j);
			entity.put("Message", "Please correct the following errors");
			entity.put("Errors", errors);
		} else {
			entity.put("Status", "200");
			entity.put("Message", "Employees Created Successfully");
		}

		return entity;
	}

	@Override
	public List<EmployeeDetails> getAllEmployeesByBusinessUnit(String id) {
		Optional<EmployeeDetails> employeeOp = employeeDetailsRepository.findById(id);
		List<EmployeeDetails> employeeDetailsList = new ArrayList<>();
		if (employeeOp.isPresent()) {
			List<BusinessUnit> businessUnits = businessUnitRepository.findByBusinessUnitHead(id);
			if (!CollectionUtils.isEmpty(businessUnits)) {
				for (BusinessUnit businessUnit : businessUnits) {
					List<EmployeeDetails> employeeDetails = employeeDetailsRepository
							.findByBusinessUnitId(businessUnit.getId());
					employeeDetailsList.addAll(employeeDetails);
				}
			}
		}
		if (employeeDetailsList.size() > 0) {
			return employeeDetailsList;
		} else {
			return null;
		}
	}

	@Override
	public List<EmployeeDetails> getAllEmployeesByMasterDepartment(String id) {
		Optional<EmployeeDetails> employeeOp = employeeDetailsRepository.findById(id);
		List<EmployeeDetails> employeeDetailsList = new ArrayList<>();
		if (employeeOp.isPresent()) {
			List<MasterDepartment> masterDepartments = masterDepartmentRepository.findByDepartmentHead(id);
			if (!CollectionUtils.isEmpty(masterDepartments)) {
				for (MasterDepartment masterDepartment : masterDepartments) {
					List<EmployeeDetails> employeeDetails = employeeDetailsRepository
							.findByMasterDepartmentId(masterDepartment.getId());
					employeeDetailsList.addAll(employeeDetails);
				}
			}
		}
		if (employeeDetailsList.size() > 0) {
			return employeeDetailsList;
		} else {
			return null;
		}
	}

	@Override
	public List<EmployeeDetails> getEmployeesByBusinessUnitAndDepartment(String businessUnit, String department) {
		List<EmployeeDetails> employeeDetails = new ArrayList<>();
		if ((businessUnit != null && !businessUnit.isEmpty()) && (department == null || department.isEmpty())) {
			Optional<BusinessUnit> businessUnitOp = businessUnitRepository.findById(businessUnit);
			if (!businessUnitOp.isPresent()) {
				return null;
			}
			employeeDetails = employeeDetailsRepository.findByBusinessUnitId(businessUnit);
		} else if ((businessUnit != null && !businessUnit.isEmpty()) && (department != null && !department.isEmpty())) {
			employeeDetails = employeeDetailsRepository.findByBusinessUnitIdAndMasterDepartmentId(businessUnit,
					department);
		}
		if (!CollectionUtils.isEmpty(employeeDetails)) {
			return employeeDetails;
		} else {
			return null;
		}
	}

	@Override
	public List<EmployeeNameDto> getEmployeesNameByBusinessUnit(String id, String projectId) {
		List<EmployeeNameDto> employeeNameDtos = new ArrayList<>();
		Optional<BusinessUnit> businessUnit = businessUnitRepository.findById(id);
		if (!businessUnit.isPresent()) {
			return null;
		}
		List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findByBusinessUnitId(id);
		if (!CollectionUtils.isEmpty(employeeDetails)) {
			employeeDetails = employeeDetails.stream().sorted(Comparator.comparing(EmployeeDetails::getEmployeeCode))
					.collect(Collectors.toList());
			for (EmployeeDetails employeeDetailOp : employeeDetails) {
				EmployeeNameDto employeeNameDto = new EmployeeNameDto();
				employeeNameDto.setId(employeeDetailOp.getId());
				employeeNameDto.setName(employeeDetailOp.getEmployeeCode() + "-" + employeeDetailOp.getName());
				employeeNameDtos.add(employeeNameDto);
			}
		}
		if (!CollectionUtils.isEmpty(employeeNameDtos)) {
			List<ProjectEmployee> proEmployees = projectEmployeesRepository.findByProjectId(projectId);
			if (!CollectionUtils.isEmpty(proEmployees)) {
				for (ProjectEmployee proEmp : proEmployees) {
					if (!proEmp.isRemoved()) {
						employeeNameDtos.removeIf(emp -> emp.getId().equals(proEmp.getEmployeeId()));
					}
				}
			}
			return employeeNameDtos;
		} else {
			return null;
		}
	}
}
