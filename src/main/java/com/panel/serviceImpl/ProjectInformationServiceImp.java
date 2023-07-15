package com.panel.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.panel.dto.EmployeeNameDto;
import com.panel.dto.ProjectAssignDto;
import com.panel.dto.ProjectEmployeeDto;
import com.panel.dto.ProjectInformationDto;
import com.panel.dto.ProjectInformationFilterDto;
import com.panel.dto.ProjectNameDto;
import com.panel.model.ProjectEmployee;
import com.panel.model.BusinessUnit;
import com.panel.model.ClientInformation;
import com.panel.model.EmployeeDetails;
import com.panel.model.MasterDepartment;
import com.panel.model.ProjectInformation;
import com.panel.model.UpdateProject;
import com.panel.repository.ProjectEmployeesRepository;
import com.panel.repository.ClientInformationrepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.MasterDepartmentRepository;
import com.panel.repository.ProjectInformationRepository;
import com.panel.repository.ActivityCodeRepository;
import com.panel.repository.BusinessUnitRepository;
import com.panel.repository.UpdateProjectRepository;
import com.panel.service.ProjectInformationService;
import com.panel.support.constant.GeneralConstants;

@Service
public class ProjectInformationServiceImp implements ProjectInformationService {

	@Autowired
	ProjectInformationRepository projectInformationrepository;

	@Autowired
	ClientInformationrepository clientInformationrepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	ProjectEmployeesRepository projectEmployeesRepository;

	@Autowired
	UpdateProjectRepository updateProjectRepository;

	@Autowired
	ActivityCodeRepository activityCodeRepository;

	@Autowired
	BusinessUnitRepository businessUnitRepository;

	@Autowired
	MasterDepartmentRepository masterDepartmentRepository;

	@Autowired
	MongoTemplate mongoTemplate;

//save	
	public LinkedHashMap<String, Object> SaveAndUpdateProjectinfo(ProjectInformationDto projectinformationDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		if (projectinformationDto.getId() == null) {

			ProjectInformation proInformation = new ProjectInformation();

			proInformation.setProjectName(projectinformationDto.getProjectName());
			proInformation.setProject_Description(projectinformationDto.getProjectDescription());
			proInformation.setCreatedDate(LocalDate.now());
			proInformation.setUpdatedDate(LocalDate.now());

			DateTimeFormatter In_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter Out_formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			String date = projectinformationDto.getFromDate();
			LocalDate date1 = LocalDate.parse(Out_formatter.format(In_formatter.parse(date)).toString(), Out_formatter);
			proInformation.setFromDate(date1);

			DateTimeFormatter In_formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter Out_formatter1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			String dates = projectinformationDto.getTodate();
			LocalDate dates1 = LocalDate.parse(Out_formatter1.format(In_formatter1.parse(dates)).toString(),
					Out_formatter1);
			proInformation.setToDate(dates1);

			Optional<ClientInformation> client = clientInformationrepository
					.findById(projectinformationDto.getClient());
			if (client.isPresent()) {
				proInformation.setClient(client.get());
			} else {
				throw new IllegalStateException("No Client Present");
			}

			if (projectinformationDto.getBusinessUnitId() != null
					&& !projectinformationDto.getBusinessUnitId().isEmpty()) {
				Optional<BusinessUnit> businessUnit = businessUnitRepository
						.findById(projectinformationDto.getBusinessUnitId());
				if (businessUnit.isPresent()) {
					proInformation.setBusinessUnitId(businessUnit.get().getId());
					proInformation.setBusinessUnit(businessUnit.get());
				}
			}

			if (projectinformationDto.getMasterDepartmentId() != null
					&& !projectinformationDto.getMasterDepartmentId().isEmpty()) {
				Optional<MasterDepartment> masterDept = masterDepartmentRepository
						.findById(projectinformationDto.getMasterDepartmentId());
				if (masterDept.isPresent()) {
					proInformation.setMasterDepartmentId(masterDept.get().getId());
					proInformation.setMasterDepartment(masterDept.get());
				}
			}

			if (projectinformationDto.getProjectManager() != null
					&& !projectinformationDto.getProjectManager().isEmpty()) {
				Optional<EmployeeDetails> employee = employeeDetailsRepository
						.findById(projectinformationDto.getProjectManager());
				if (employee.isPresent()) {
					proInformation.setProjectManager(employee.get().getId());
				}
			}

//			Optional<EmployeeDetails> employee = employeeDetailsRepository
//					.findById(projectinformationDto.getCreatedBy());
//			if (employee.isPresent()) {
			proInformation.setCreatedBy(projectinformationDto.getCreatedBy());
//			}

			if (projectinformationDto.getUpdatedBy() != null) {
//				Optional<EmployeeDetails> employeeOp = employeeDetailsRepository
//						.findById(projectinformationDto.getUpdatedBy());
//				if (employeeOp.isPresent()) {
				proInformation.setUpdatedBy(projectinformationDto.getUpdatedBy());
//				}
			}

			projectInformationrepository.save(proInformation);

			entity.put("status", "200");
			entity.put("message", "Project Saved Successfully");
			entity.put("Data", proInformation);
		}

//update		
		else {

			Optional<ProjectInformation> projeOptional = projectInformationrepository
					.findById(projectinformationDto.getId());
			if (projeOptional != null) {

				ProjectInformation proInformation = projeOptional.get();

				BeanUtils.copyProperties(projectinformationDto, proInformation,
						getNullPropertyNames(projectinformationDto));

				proInformation.setUpdatedDate(LocalDate.now());
				if (projectinformationDto.getFromDate() != null) {
					DateTimeFormatter In_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date = projectinformationDto.getFromDate();
					LocalDate date1 = LocalDate.parse(Out_formatter.format(In_formatter.parse(date)).toString(),
							Out_formatter);
					proInformation.setFromDate(date1);
					;
				} else {
					proInformation.setFromDate(proInformation.getFromDate());
				}

				if (projectinformationDto.getTodate() != null) {
					DateTimeFormatter In_formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String dates = projectinformationDto.getTodate();
					LocalDate dates1 = LocalDate.parse(Out_formatter1.format(In_formatter1.parse(dates)).toString(),
							Out_formatter1);
					proInformation.setToDate(dates1);
				} else {
					proInformation.setToDate(proInformation.getToDate());
				}

				if (projectinformationDto.getUpdatedBy() != null) {
//					Optional<EmployeeDetails> employeeOp = employeeDetailsRepository
//							.findById(projectinformationDto.getUpdatedBy());
//					if (employeeOp.isPresent()) {
					proInformation.setUpdatedBy(projectinformationDto.getUpdatedBy());
//					}
				}

				if (projectinformationDto.getProjectDescription() != null) {

					proInformation.setProject_Description(projectinformationDto.getProjectDescription());
				} else {
					proInformation.setProject_Description(proInformation.getProject_Description());
				}
				if (projectinformationDto.getClient() != null) {
					Optional<ClientInformation> client = clientInformationrepository
							.findById(projectinformationDto.getClient());
					if (client.isPresent()) {
						proInformation.setClient(client.get());
					}
				} else {
					proInformation.setClient(proInformation.getClient());
				}

				if (projectinformationDto.getBusinessUnitId() != null
						&& !projectinformationDto.getBusinessUnitId().isEmpty()) {
					Optional<BusinessUnit> businessUnit = businessUnitRepository
							.findById(projectinformationDto.getBusinessUnitId());
					if (businessUnit.isPresent()) {
						proInformation.setBusinessUnitId(businessUnit.get().getId());
						proInformation.setBusinessUnit(businessUnit.get());
					}
				} else {
					proInformation.setBusinessUnitId(proInformation.getBusinessUnitId());
					proInformation.setBusinessUnit(proInformation.getBusinessUnit());
				}

				if (projectinformationDto.getMasterDepartmentId() != null
						&& !projectinformationDto.getMasterDepartmentId().isEmpty()) {
					Optional<MasterDepartment> masterDept = masterDepartmentRepository
							.findById(projectinformationDto.getMasterDepartmentId());
					if (masterDept.isPresent()) {
						proInformation.setMasterDepartmentId(masterDept.get().getId());
						proInformation.setMasterDepartment(masterDept.get());
					}
				} else {
					proInformation.setMasterDepartmentId(proInformation.getBusinessUnitId());
					proInformation.setMasterDepartment(proInformation.getMasterDepartment());
				}

				if (projectinformationDto.getProjectManager() != null
						&& !projectinformationDto.getProjectManager().isEmpty()) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(projectinformationDto.getProjectManager());
					if (employee.isPresent()) {
						proInformation.setProjectManager(employee.get().getId());
					}
				} else {
					proInformation.setProjectManager(proInformation.getProjectManager());
				}

				UpdateProject project = new UpdateProject();
				project.setUpdatedDate(proInformation.getUpdatedDate());
				project.setUpdatedBy(proInformation.getUpdatedBy());
				project.setProject(proInformation);

				updateProjectRepository.save(project);

				projectInformationrepository.save(proInformation);

				entity.put("status", "200");
				entity.put("message", "Project Updated Successfully");
				entity.put("Data", proInformation);
			}

			else {
				entity.put("status", "400");
				entity.put("message", "No Data is present");
			}

		}

		return entity;
	}

	public String[] getNullPropertyNames(Object source) {

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

	/*
	 * @Override public List<ProjectInformation> findByProjectName(String
	 * projectName) {
	 * 
	 * List<ProjectInformation> projectInformations =
	 * projectInformationrepository.findByProjectName(projectName);
	 * List<ProjectInformation> projList = new ArrayList<>();
	 * projectInformations.forEach(projectInformation -> { ProjectInformation
	 * projectInformationop = new ProjectInformation();
	 * projList.add(projectInformationop);
	 * 
	 * 
	 * });
	 * 
	 * 
	 * 
	 * return projList; }
	 */
	public LinkedHashMap<String, Object> assignEmployeesToProject(ProjectAssignDto projectAssignDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

//		Optional<ProjectInformation> project = projectInformationrepository
//				.findById(assignEmployeesToProjectDto.getProjectId());
//		ProjectEmployee assignEmployeesToProject = new ProjectEmployee();
//		String resource = null;
//		if (project.isPresent()) {
//			Optional<EmployeeDetails> employee = employeeDetailsRepository
//					.findById(assignEmployeesToProjectDto.getEmployeeId());
//			if (!CollectionUtils.isEmpty(project.get().getAssignedResources())) {
//				for (String res : project.get().getAssignedResources()) {
//					if (res.equals(employee.get().getName())) {
//						resource = res;
//					}
//				}
//
//			}
//			if (resource == null) {
//				if (employee.isPresent()) {
//					assignEmployeesToProject
//							.setEmployee(employee.get().getEmployeeCode() + "-" + employee.get().getName());
//					assignEmployeesToProject.setEmployeeId(employee.get().getId());
//				}
//
//				assignEmployeesToProject.setProject(project.get().getProjectName());
//				assignEmployeesToProject.setProjectId(project.get().getId());
//
//				assignEmployeesToProject.setAssignedDate(LocalDate.now());
//				Optional<EmployeeDetails> assign = employeeDetailsRepository
//						.findById(assignEmployeesToProjectDto.getAssignedBy());
//				if (assign.isPresent()) {
//					assignEmployeesToProject.setAssignedBy(assign.get().getName());
//				}
//
//				projectEmployeesRepository.save(assignEmployeesToProject);
//
//				if (project.get().getAssignedResources() != null) {
//					project.get().getAssignedResources().add(employee.get().getName());
//				} else {
//					List<String> assignResource = new ArrayList<>();
//					assignResource.add(employee.get().getName());
//					project.get().setAssignedResources(assignResource);
//				}
//				entity.put("status", "200");
//				entity.put("message", "data is present");
//				entity.put("data", assignEmployeesToProject);
//			} else {
//				entity.put("status", "400");
//				entity.put("message", "Employee already assigned to this project");
//			}
//		}
//		projectInformationrepository.save(project.get());

		if (!CollectionUtils.isEmpty(projectAssignDto.getProjectEmployeeDtos())) {

			for (ProjectEmployeeDto projectEmployeeDto : projectAssignDto.getProjectEmployeeDtos()) {
				List<ProjectEmployee> projectEmployees = projectEmployeesRepository.findByProjectIdAndEmployeeId(
						projectEmployeeDto.getProjectId(), projectEmployeeDto.getEmployeeId());
				Optional<ProjectInformation> project = projectInformationrepository
						.findById(projectEmployeeDto.getProjectId());
				Optional<EmployeeDetails> employee = employeeDetailsRepository
						.findById(projectEmployeeDto.getEmployeeId());
				int i = 0;
				if (!CollectionUtils.isEmpty(projectEmployees)) {
					for (ProjectEmployee proEmployee : projectEmployees) {
						if (!proEmployee.isRemoved()) {
							i++;
						}
					}
				}
				if (CollectionUtils.isEmpty(projectEmployees) || i == 0) {
					ProjectEmployee assignEmployeesToProject = new ProjectEmployee();
					assignEmployeesToProject.setEmployeeId(employee.get().getId());
					assignEmployeesToProject
							.setEmployee(employee.get().getEmployeeCode() + "-" + employee.get().getName());
					assignEmployeesToProject.setProjectId(project.get().getId());
//					assignEmployeesToProject.setProject(project.get().getProjectName());
					assignEmployeesToProject.setResponsibilities(projectEmployeeDto.getResponsibilities());
					assignEmployeesToProject.setAssignedDate(LocalDate.now());
					assignEmployeesToProject.setAssignedBy(projectEmployeeDto.getAssignedBy());
					assignEmployeesToProject.setBusinessUnitId(projectEmployeeDto.getBusinessUnitId());
					assignEmployeesToProject.setRemoved(false);
					projectEmployeesRepository.save(assignEmployeesToProject);
					if (project.get().getAssignedResources() != null) {
						project.get().getAssignedResources().add(employee.get().getId());
					} else {
						List<String> assignResource = new ArrayList<>();
						assignResource.add(employee.get().getId());
						project.get().setAssignedResources(assignResource);
					}

					projectInformationrepository.save(project.get());
				}
			}
			entity.put("status", "200");
			entity.put("message", "Employee Assigned to project successfully");
		} else {
			entity.put("status", "400");
			entity.put("message", "No data is present");
		}
		return entity;

	}

	// filter
	@Override
	public List<ProjectInformation> getAllProjectInformationDetails(
			ProjectInformationFilterDto projectInformationFilterDto) {

		Query query = new Query();

		if (projectInformationFilterDto.getProjectName() != null
				&& !projectInformationFilterDto.getProjectName().equals("")) {

			query.addCriteria(Criteria.where("projectName").is(projectInformationFilterDto.getProjectName()));

		}
		if (projectInformationFilterDto.getProjectId() != null
				&& !projectInformationFilterDto.getProjectId().equals("")) {

			query.addCriteria(Criteria.where("projectId").is(projectInformationFilterDto.getProjectId()));

		}
		List<ProjectInformation> projeInformations = mongoTemplate.find(query, ProjectInformation.class);

		return projeInformations;

	}

//getall projectdetails
	@Override
	public LinkedHashMap<String, Object> getallProjectDetails(String id) {

		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<EmployeeDetails> employeeOp = employeeDetailsRepository.findById(id);
		List<ProjectInformation> projeInformations = null;
		if (employeeOp.get().getRole().equals(GeneralConstants.ADMIN)
				|| employeeOp.get().getRole().equals(GeneralConstants.HR)) {
			projeInformations = projectInformationrepository.findAll();
		} else if (employeeOp.get().getRole().equals(GeneralConstants.BUSINESS_UNIT_HEAD)) {
			projeInformations = getProjectByBusinessUnit(id);
		} else if (employeeOp.get().getRole().equals(GeneralConstants.DEPARTMENT_HEAD)) {
			projeInformations = getProjectByDepartment(id);
			List<ProjectInformation> projectInformations = projectInformationrepository.findByProjectManager(id);
			if (!CollectionUtils.isEmpty(projeInformations)) {
				if (!CollectionUtils.isEmpty(projectInformations)) {
					for (ProjectInformation project : projeInformations) {
						projectInformations.removeIf(p -> p.getId().equals(project.getId()));
					}
				}
			}
			if (!CollectionUtils.isEmpty(projectInformations) && CollectionUtils.isEmpty(projeInformations)) {
				projeInformations = projectInformations;
			} else if (!CollectionUtils.isEmpty(projectInformations)) {
				projeInformations.addAll(projectInformations);
			}
		} else if (employeeOp.get().getRole().equals(GeneralConstants.MANAGER)) {
			projeInformations = getProjectByProjectManager(id);
		}

		List<ProjectInformation> projectInformations = new ArrayList<>();

		if (!CollectionUtils.isEmpty(projeInformations)) {
			for (ProjectInformation projectInformation : projeInformations) {
//			BeanUtils.copyProperties(projeInformations, projectInformation);
				projectInformation.setNoOfResources(0);
				if (projectInformation.getAssignedResources() != null) {
					projectInformation.setNoOfResources(projectInformation.getAssignedResources().size());
				}
				if (projectInformation.getCreatedBy() != null) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(projectInformation.getCreatedBy());
					if (employee.isPresent()) {
						projectInformation.setCreatedBy(employee.get().getName());
					}
				}
				if (projectInformation.getUpdatedBy() != null) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(projectInformation.getUpdatedBy());
					if (employee.isPresent()) {
						projectInformation.setUpdatedBy(employee.get().getName());
					}
				}
				projectInformations.add(projectInformation);
			}
		}

		if (projectInformations.size() > 0) {

			entity.put("status", "200");
			entity.put("message", "Data is present");
			entity.put("Data", projectInformations);

		} else {

			entity.put("status", "400");
			entity.put("message", "No Data is present");

		}

		return entity;
	}

//getbyid or view	

	@Override
	public LinkedHashMap<String, Object> getProjectById(String id) {
		// TODO Auto-generated method stub

		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<ProjectInformation> projeOptional = projectInformationrepository.findById(id);
		List<String> assignedResource = new ArrayList<>();
		if (projeOptional.isPresent()) {

			if (projeOptional.get().getAssignedResources() != null) {
				for (String ids : projeOptional.get().getAssignedResources()) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(ids);
					if (employee.isPresent()) {
						assignedResource.add(employee.get().getName());
					}
				}
				projeOptional.get().setAssignedResources(assignedResource);
			}

			entity.put("status", "200");
			entity.put("message", "data is present");
			entity.put("data", projeOptional);

		} else {
			entity.put("status", "400");
			entity.put("message", "data is not present");
		}

		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getProjectEmployeeUpdateList(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<ProjectInformation> project = projectInformationrepository.findById(id);
		List<ProjectEmployee> projectEmployees = projectEmployeesRepository.findAll();
		List<ProjectEmployee> proEmployees = new ArrayList<>();
		if (project.isPresent()) {
			for (ProjectEmployee projectEmployee : projectEmployees) {
				if (!projectEmployee.isRemoved()) {
					if (project.get().getId().equals(projectEmployee.getProjectId())) {
						Optional<EmployeeDetails> employee = employeeDetailsRepository
								.findById(projectEmployee.getEmployeeId());
						if (employee.isPresent()) {
							projectEmployee.setEmployee(employee.get().getName());
						}
						Optional<EmployeeDetails> employeeOp = employeeDetailsRepository
								.findById(projectEmployee.getAssignedBy());
						if (employeeOp.isPresent()) {
							projectEmployee.setAssignedBy(employeeOp.get().getName());
						}
						proEmployees.add(projectEmployee);
					}
				}
			}
			entity.put("status", "200");
			entity.put("message", "data is present");
			entity.put("data", proEmployees);
		} else {
			entity.put("status", "400");
			entity.put("message", "data is not present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getProjectByEmployee(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		List<ProjectEmployee> projectEmployees = projectEmployeesRepository.findAll();
		List<ProjectEmployee> proEmployees = new ArrayList<>();
		if (employee.isPresent()) {
			for (ProjectEmployee projectEmployee : projectEmployees) {
				if (projectEmployee.getEmployeeId().equals(employee.get().getId())) {
					if (!projectEmployee.isRemoved()) {
						Optional<ProjectInformation> projectInfo = projectInformationrepository.findById(projectEmployee.getProjectId());
						projectEmployee.setProject(projectInfo.get().getProjectName());
						proEmployees.add(projectEmployee);
					}
				}
			}
			entity.put("status", "200");
			entity.put("message", "data is present");
			entity.put("data", proEmployees);
		} else {
			entity.put("status", "400");
			entity.put("message", "data is not present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getProjectUpdateDetails(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<ProjectInformation> projectInfo = projectInformationrepository.findById(id);
		List<UpdateProject> projectUpdates = updateProjectRepository.findAll();
		List<UpdateProject> updates = new ArrayList<>();
		if (projectInfo.isPresent()) {
			for (UpdateProject projectUpdate : projectUpdates) {
				if (projectUpdate.getProject().getId().equals(projectInfo.get().getId())) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(projectUpdate.getUpdatedBy());
					if (employee.isPresent()) {
						projectUpdate.setUpdatedBy(employee.get().getName());
					}
					updates.add(projectUpdate);
				}
			}
		}
		if (updates.size() > 0) {
			entity.put("status", "200");
			entity.put("message", "data is present");
			entity.put("data", updates);
		} else {
			entity.put("status", "400");
			entity.put("message", "data is not present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getAllProjectNames() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		List<ProjectInformation> proInformations = projectInformationrepository.findAll();
		List<ProjectNameDto> projetNames = new ArrayList<>();
		for (ProjectInformation project : proInformations) {
			ProjectNameDto proinfo = new ProjectNameDto();
			proinfo.setId(project.getId());
			proinfo.setName(project.getProjectName());
			projetNames.add(proinfo);
		}

		if (projetNames.size() > 0) {
			entity.put("status", "200");
			entity.put("message", "data is present");
			entity.put("data", projetNames);
		} else {
			entity.put("status", "400");
			entity.put("message", "data is not present");
		}
		return entity;
	}

	public LinkedHashMap<String, Object> getUnAssignedEmployeesForProject(String empId, String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		List<EmployeeDetails> employees = new ArrayList<>();
		Optional<ProjectInformation> project = projectInformationrepository.findById(id);
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(empId);
		if (!employee.isPresent()) {
			return null;
		}

		if (employee.get().getRole().equals(GeneralConstants.ADMIN)) {
			employees = employeeDetailsRepository.findAll();
		} else if (employee.get().getRole().equals(GeneralConstants.BUSINESS_UNIT_HEAD)) {
			List<BusinessUnit> businessUnits = businessUnitRepository.findByBusinessUnitHead(empId);
			Set<EmployeeDetails> empSet = new HashSet<>();
			if (!CollectionUtils.isEmpty(businessUnits)) {
				for (BusinessUnit businessUnit : businessUnits) {
					List<EmployeeDetails> employeeDetails = employeeDetailsRepository
							.findByBusinessUnitId(businessUnit.getId());
					if (!CollectionUtils.isEmpty(employeeDetails)) {
						empSet.addAll(employeeDetails);
					}
				}
			}
			employees = empSet.stream().sorted(Comparator.comparing(EmployeeDetails::getEmployeeCode))
					.collect(Collectors.toList());
		} else if (employee.get().getRole().equals(GeneralConstants.DEPARTMENT_HEAD)) {
			List<MasterDepartment> masterDepartments = masterDepartmentRepository.findByDepartmentHead(empId);
			Set<String> emp = new HashSet<>();
			if (!CollectionUtils.isEmpty(masterDepartments)) {
				for (MasterDepartment masterDepartment : masterDepartments) {
					Optional<BusinessUnit> businessUnit = businessUnitRepository
							.findById(masterDepartment.getBusinessUnitId());
					if (businessUnit.isPresent()) {
						List<EmployeeDetails> employeeDetails = employeeDetailsRepository
								.findByBusinessUnitId(businessUnit.get().getId());
						if (!CollectionUtils.isEmpty(employeeDetails)) {
							for (EmployeeDetails employeeOp : employeeDetails) {
								emp.add(employeeOp.getId());
							}
						}
					}
				}
			}
			if (!CollectionUtils.isEmpty(emp)) {
				for (String i : emp) {
					Optional<EmployeeDetails> empOp = employeeDetailsRepository.findById(i);
					if (empOp.isPresent()) {
						employees.add(empOp.get());
					}
				}
			}
			employees = employees.stream().sorted(Comparator.comparing(EmployeeDetails::getEmployeeCode))
					.collect(Collectors.toList());
		} else if (employee.get().getRole().equals(GeneralConstants.MANAGER)) {
			employees = employeeDetailsRepository.findByBusinessUnitId(employee.get().getBusinessUnit().getId());

			employees = employees.stream().sorted(Comparator.comparing(EmployeeDetails::getEmployeeCode))
					.collect(Collectors.toList());
		}
		List<ProjectEmployee> proEmployees = projectEmployeesRepository.findByProjectId(id);
		List<EmployeeNameDto> employeeNameDtos = new ArrayList<>();
		if (!CollectionUtils.isEmpty(proEmployees)) {
			for (ProjectEmployee proEmp : proEmployees) {
				if (!proEmp.isRemoved()) {
					employees.removeIf(emp -> emp.getId().equals(proEmp.getEmployeeId()));
				}
			}
		}
		for (EmployeeDetails employeeName : employees) {
			EmployeeNameDto employeeNameDto = new EmployeeNameDto();
			employeeNameDto.setId(employeeName.getId());
			employeeNameDto.setName(employeeName.getEmployeeCode() + "-" + employeeName.getName());
			employeeNameDtos.add(employeeNameDto);
		}
		if (employeeNameDtos.size() > 0) {
			entity.put("status", "200");
			entity.put("message", "data is present");
			entity.put("data", employeeNameDtos);
		} else {
			entity.put("status", "400");
			entity.put("message", "data is not present");
		}
		return entity;
	}

	@Override
	public List<ProjectInformation> getProjectByBusinessUnit(String id) {
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		List<ProjectInformation> projectInformations = new ArrayList<>();
		if (employee.isPresent()) {
			List<BusinessUnit> businessUnits = businessUnitRepository.findByBusinessUnitHead(id);
			if (!CollectionUtils.isEmpty(businessUnits)) {
				for (BusinessUnit businessUnit : businessUnits) {
					List<ProjectInformation> projects = projectInformationrepository
							.findByBusinessUnitId(businessUnit.getId());
					if (!CollectionUtils.isEmpty(projects)) {
						projectInformations.addAll(projects);
					}
				}
			}
		}
		if (projectInformations.size() > 0) {
			return projectInformations;
		} else {
			return null;
		}
	}

	@Override
	public List<ProjectInformation> getProjectByDepartment(String id) {
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		List<ProjectInformation> projectInformations = new ArrayList<>();
		if (employee.isPresent()) {
			List<MasterDepartment> masterDepartments = masterDepartmentRepository.findByDepartmentHead(id);
			if (!CollectionUtils.isEmpty(masterDepartments)) {
				for (MasterDepartment masterDepartment : masterDepartments) {
					List<ProjectInformation> projects = projectInformationrepository
							.findByMasterDepartmentId(masterDepartment.getId());
					if (!CollectionUtils.isEmpty(projects)) {
						projectInformations.addAll(projects);
					}
				}
			}
		}
		if (projectInformations.size() > 0) {
			return projectInformations;
		} else {
			return null;
		}
	}

	@Override
	public List<ProjectInformation> getProjectByProjectManager(String id) {
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		List<ProjectInformation> projectInformations = new ArrayList<>();
		if (employee.isPresent()) {
			List<ProjectInformation> projects = projectInformationrepository.findByProjectManager(id);
			if (!CollectionUtils.isEmpty(projects)) {
				projectInformations.addAll(projects);
			}
		}
		if (projectInformations.size() > 0) {
			return projectInformations;
		} else {
			return null;
		}
	}

	@Override
	public LinkedHashMap<String, Object> removeResourceFromProject(String empId, String projectId) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<EmployeeDetails> empOptional = employeeDetailsRepository.findById(empId);
		if (!empOptional.isPresent()) {
			return null;
		}
		Optional<ProjectInformation> proOptional = projectInformationrepository.findById(projectId);
		if (!proOptional.isPresent()) {
			return null;
		}
		List<ProjectEmployee> projectEmployees = projectEmployeesRepository.findByProjectIdAndEmployeeId(projectId,
				empId);
		if (!CollectionUtils.isEmpty(projectEmployees)) {
			Optional<ProjectEmployee> projectEmployee = projectEmployees.stream().filter(p -> !p.isRemoved())
					.findFirst();
			if (projectEmployee.isPresent()) {
				projectEmployee.get().setRemoved(true);
				projectEmployee.get().setRemovedDate(LocalDate.now());
				projectEmployeesRepository.save(projectEmployee.get());

				proOptional.get().getAssignedResources().removeIf(e -> e.equals(empId));
				projectInformationrepository.save(proOptional.get());

				entity.put("status", "200");
				entity.put("message", "Employee Removed From Project");
			} else {
				entity.put("status", "400");
				entity.put("message", "Employee Already Removed From Project");
			}
		}
		return entity;
	}

}
