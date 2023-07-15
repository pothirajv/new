package com.panel.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.plaf.ToolBarUI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.panel.dto.BusinessUnitDto;
import com.panel.dto.DepartmentDto;
import com.panel.dto.MasterDepartmentDto;
import com.panel.dto.MasterDepartmentProjectDto;
import com.panel.model.BusinessUnit;
import com.panel.model.Department;
import com.panel.model.EmployeeDetails;
import com.panel.model.MasterDepartment;
import com.panel.model.ProjectInformation;
import com.panel.repository.BusinessUnitRepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.MasterDepartmentRepository;
import com.panel.repository.ProjectInformationRepository;
import com.panel.service.BusinessUnitService;
import com.panel.service.MasterDepartmentService;

@Service
public class MasterDepartmentServiceImpl implements MasterDepartmentService {

	@Autowired
	MasterDepartmentRepository masterDepartmentRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	BusinessUnitRepository businessUnitRepository;

	@Autowired
	ProjectInformationRepository projectInformationRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	public LinkedHashMap<String, Object> saveAndUpdateMasterDepartment(MasterDepartmentDto masterDepartmentDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		int i = 0;
		if (masterDepartmentDto.getId() == null) {
//		Optional<BusinessUnit> businessUnit = businessUnitRepository.findById(masterDepartmentDto.getBusinessUnitId());
			Optional<MasterDepartment> department = masterDepartmentRepository
					.findByDepartmentCode(masterDepartmentDto.getDepartmentCode());

			if (!department.isPresent()) {
				MasterDepartment masterDepartment = new MasterDepartment();
				BeanUtils.copyProperties(masterDepartmentDto, masterDepartment);

				if (masterDepartmentDto.getDepartmentAlternate() != null
						&& !masterDepartmentDto.getDepartmentAlternate().isEmpty()) {
//			Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository.findById(masterDepartmentDto.getDepartmentAlternate());
//			if(employeeDetail.isPresent()) {
					masterDepartment.setDepartmentAlternate(masterDepartmentDto.getDepartmentAlternate());
//			}
				} else {
					masterDepartment.setDepartmentAlternate(null);
				}

				if (masterDepartmentDto.getDepartmentHead() != null
						&& !masterDepartmentDto.getDepartmentHead().isEmpty()) {
					Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
							.findById(masterDepartmentDto.getDepartmentHead());
					if (employeeDetails.isPresent()) {
						masterDepartment.setDepartmentHead(masterDepartmentDto.getDepartmentHead());
					}
				}
				List<MasterDepartment> dpts = masterDepartmentRepository.findAll();
				if (masterDepartmentDto.getDepartmentName() != null
						&& !masterDepartmentDto.getDepartmentName().isEmpty()) {
					for (MasterDepartment md : dpts) {
						if (md.getDepartmentName().equalsIgnoreCase(masterDepartmentDto.getDepartmentName())) {
							i++;
						}
					}
				}
				if (i == 0) {
					masterDepartmentRepository.save(masterDepartment);
					entity.put("Status", "200");
					entity.put("Message", "Master Department Saved Successfully");
					entity.put("Data", masterDepartment);
				} else {
					entity.put("Status", "400");
					entity.put("Message", "Department name is already exist..");
				}

			} else {
				entity.put("Status", "400");
				entity.put("Message", "Department code is already exist..");
			}

		} else {

			Optional<MasterDepartment> masterDepartments = masterDepartmentRepository
					.findOneById(masterDepartmentDto.getId());
			if (masterDepartments.get().getId() != null) {

				MasterDepartment masterDepartment = masterDepartments.get();
				BeanUtils.copyProperties(masterDepartmentDto, masterDepartment,
						getNullPropertyNames(masterDepartmentDto));
				if (masterDepartmentDto.getDepartmentHead() != null
						&& !masterDepartmentDto.getDepartmentHead().isEmpty()) {
					Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
							.findById(masterDepartmentDto.getDepartmentHead());
					if (employeeDetails.isPresent()) {
						masterDepartment.setDepartmentHead(masterDepartmentDto.getDepartmentHead());
					}
				} else {
					masterDepartment.setDepartmentHead(masterDepartment.getDepartmentHead());
				}
				if (masterDepartmentDto.getDepartmentAlternate() != null
						&& !masterDepartmentDto.getDepartmentAlternate().isEmpty()) {
					masterDepartment.setDepartmentAlternate(masterDepartmentDto.getDepartmentAlternate());
				} else {
					masterDepartment.setDepartmentAlternate(masterDepartment.getDepartmentAlternate());
				}

				if (masterDepartmentDto.getDepartmentName() != null
						&& !masterDepartmentDto.getDepartmentName().isEmpty()) {
					masterDepartment.setDepartmentName(masterDepartmentDto.getDepartmentName());
				} else {
					masterDepartment.setDepartmentName(masterDepartment.getDepartmentName());
				}
				masterDepartmentRepository.save(masterDepartment);
				entity.put("Status", "200");
				entity.put("Message", "Master Department Updated Successfully");
				entity.put("Data", masterDepartment);

			} else {
				entity.put("Status", "405");
				entity.put("Message", "couldn't updated");
			}
		}
		return entity;
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null || srcValue.toString().equals("0"))
				emptyNames.add(pd.getName());
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public LinkedHashMap<String, Object> getAllMasterDepartments() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<MasterDepartmentDto> masterDepartmentDtos = new ArrayList<>();

		List<MasterDepartment> masterDepartments = masterDepartmentRepository.findAll();
		List<MasterDepartment> mastDepartments = new ArrayList<>();
		if (masterDepartments.size() > 0) {
			for (MasterDepartment masterDepartment : masterDepartments) {
				if (masterDepartment.getDepartmentHead() != null) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(masterDepartment.getDepartmentHead());
					if (employee.isPresent()) {
						masterDepartment
								.setDepartmentHead(employee.get().getEmployeeCode() + "-" + employee.get().getName());
					}
				}
				if (masterDepartment.getDepartmentAlternate() != null) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(masterDepartment.getDepartmentAlternate());
					if (employee.isPresent()) {
						masterDepartment.setDepartmentAlternate(
								employee.get().getEmployeeCode() + "-" + employee.get().getName());
					}
				}
				if (masterDepartment.getBusinessUnitId() != null) {
					Optional<BusinessUnit> businessUnit = businessUnitRepository
							.findById(masterDepartment.getBusinessUnitId());
					if (businessUnit.isPresent()) {
						masterDepartment.setBusinessUnitId(businessUnit.get().getBusinessUnitName());
					}
				}
				mastDepartments.add(masterDepartment);
			}
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", masterDepartments);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getMasterDepartmentById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<MasterDepartment> masterDepartments = masterDepartmentRepository.findById(id);
		if (masterDepartments.isPresent()) {
//			if (masterDepartments.get().getDepartmentHead() != null) {
//				Optional<EmployeeDetails> employee = employeeDetailsRepository
//						.findById(masterDepartments.get().getDepartmentHead());
//				if (employee.isPresent()) {
//					masterDepartments.get()
//							.setDepartmentHead(employee.get().getEmployeeCode() + "-" + employee.get().getName());
//				}
//			}
//			if (masterDepartments.get().getDepartmentAlternate() != null) {
//				Optional<EmployeeDetails> employeeOp = employeeDetailsRepository
//						.findById(masterDepartments.get().getDepartmentAlternate());
//				if (employeeOp.isPresent()) {
//					masterDepartments.get().setDepartmentAlternate(
//							employeeOp.get().getEmployeeCode() + "-" + employeeOp.get().getName());
//				}
//			}
			if (masterDepartments.get().getBusinessUnitId() != null
					&& !masterDepartments.get().getBusinessUnitId().isEmpty()) {
				Optional<BusinessUnit> businessUnit = businessUnitRepository
						.findById(masterDepartments.get().getBusinessUnitId());
				if (businessUnit.isPresent()) {
					masterDepartments.get().setBusinessUnitId(businessUnit.get().getBusinessUnitName());
				}
			}

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", masterDepartments);
		} else {
			entity.put("Status", "405");
			entity.put("Message", "Data is not present");

		}

		return entity;
	}

	public LinkedHashMap<String, Object> getMasterDepartmentFilter(MasterDepartmentDto masterDepartmentDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Query query = new Query();

		if (masterDepartmentDto.getDepartmentName() != null && !masterDepartmentDto.getDepartmentName().equals("")) {
			query.addCriteria(Criteria.where("departmentName").is(masterDepartmentDto.getDepartmentName()));
		}

		if (masterDepartmentDto.getDepartmentCode() != null && !masterDepartmentDto.getDepartmentCode().equals("")) {
			query.addCriteria(Criteria.where("departmentCode").is(masterDepartmentDto.getDepartmentCode()));
		}

		List<MasterDepartment> masterDepartments = mongoTemplate.find(query, MasterDepartment.class);
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", masterDepartments);

		return entity;
	}

	public LinkedHashMap<String, Object> getMasterDepartmentsByBusinessUnit(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<MasterDepartment> masterDepartmentDtos = new ArrayList<>();

		List<MasterDepartment> masterDepartments = masterDepartmentRepository.findAll();

		masterDepartments.forEach(masterDepartment -> {
			if (masterDepartment.getBusinessUnitId().equals(id)) {
				if (masterDepartment.getDepartmentHead() != null) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(masterDepartment.getDepartmentHead());
					if (employee.isPresent()) {
						masterDepartment
								.setDepartmentHead(employee.get().getEmployeeCode() + "-" + employee.get().getName());
					}
				}
				if (masterDepartment.getDepartmentAlternate() != null) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(masterDepartment.getDepartmentAlternate());
					if (employee.isPresent()) {
						masterDepartment.setDepartmentAlternate(
								employee.get().getEmployeeCode() + "-" + employee.get().getName());
					}
				}
				if (masterDepartment.getBusinessUnitId() != null) {
					Optional<BusinessUnit> businessUnit = businessUnitRepository
							.findById(masterDepartment.getBusinessUnitId());
					if (businessUnit.isPresent()) {
						masterDepartment.setBusinessUnitId(businessUnit.get().getBusinessUnitName());
					}
				}
				masterDepartmentDtos.add(masterDepartment);
			}
		});

		if (masterDepartmentDtos.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", masterDepartmentDtos);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getMasterDepartmentByBusinessUnitAndDeptHead(String unitId, String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<BusinessUnit> businessUnit = businessUnitRepository.findById(unitId);
		if (businessUnit.isPresent()) {
			List<MasterDepartment> masterDepartments = masterDepartmentRepository
					.findByDepartmentHeadAndBusinessUnitId(id, unitId);
			if (!CollectionUtils.isEmpty(masterDepartments)) {
				entity.put("Status", "200");
				entity.put("Message", "Data is present");
				entity.put("Data", masterDepartments);
			} else {
				entity.put("Status", "400");
				entity.put("Message", "No Data is present");
			}
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getMasterDepartmentByBusinessUnitHead(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<EmployeeDetails> empOptional = employeeDetailsRepository.findById(id);
		if (!empOptional.isPresent()) {
			return null;
		}
		List<MasterDepartment> masterDepartmentList = new ArrayList<>();
		List<BusinessUnit> businessUnits = businessUnitRepository.findByBusinessUnitHead(id);
		if (!CollectionUtils.isEmpty(businessUnits)) {
			for (BusinessUnit businessUnit : businessUnits) {
				List<MasterDepartment> masterDepartments = masterDepartmentRepository
						.findByBusinessUnitId(businessUnit.getId());
				if (!CollectionUtils.isEmpty(masterDepartments)) {
					for (MasterDepartment masterDepartment : masterDepartments) {
						if (masterDepartment.getDepartmentHead() != null
								&& !masterDepartment.getDepartmentHead().isEmpty()) {
							Optional<EmployeeDetails> employee = employeeDetailsRepository
									.findById(masterDepartment.getDepartmentHead());
							if (employee.isPresent()) {
								masterDepartment.setDepartmentHead(
										employee.get().getEmployeeCode() + "-" + employee.get().getName());
							}
						}
						if (masterDepartment.getDepartmentAlternate() != null
								&& !masterDepartment.getDepartmentAlternate().isEmpty()) {
							Optional<EmployeeDetails> employee = employeeDetailsRepository
									.findById(masterDepartment.getDepartmentAlternate());
							if (employee.isPresent()) {
								masterDepartment.setDepartmentAlternate(
										employee.get().getEmployeeCode() + "-" + employee.get().getName());
							}
						}
						if (masterDepartment.getBusinessUnitId() != null
								&& !masterDepartment.getBusinessUnitId().isEmpty()) {
							Optional<BusinessUnit> bUnit = businessUnitRepository
									.findById(masterDepartment.getBusinessUnitId());
							if (bUnit.isPresent()) {
								masterDepartment.setBusinessUnitId(bUnit.get().getBusinessUnitName());
							}
						}
						masterDepartmentList.add(masterDepartment);
					}
				}
			}
		}
		if (!CollectionUtils.isEmpty(masterDepartmentList)) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", masterDepartmentList);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getMasterDepartmentAndProjects(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<MasterDepartment> masterDepartment = masterDepartmentRepository.findById(id);
		if (!masterDepartment.isPresent()) {
			return null;
		}
		if (masterDepartment.get().getDepartmentHead() != null
				&& !masterDepartment.get().getDepartmentHead().isEmpty()) {
			Optional<EmployeeDetails> employee = employeeDetailsRepository
					.findById(masterDepartment.get().getDepartmentHead());
			if (employee.isPresent()) {
				masterDepartment.get().setDepartmentHead(
						employee.get().getEmployeeCode() + "-" + employee.get().getName());
			}
		}
		if (masterDepartment.get().getDepartmentAlternate() != null
				&& !masterDepartment.get().getDepartmentAlternate().isEmpty()) {
			Optional<EmployeeDetails> employee = employeeDetailsRepository
					.findById(masterDepartment.get().getDepartmentAlternate());
			if (employee.isPresent()) {
				masterDepartment.get().setDepartmentAlternate(
						employee.get().getEmployeeCode() + "-" + employee.get().getName());
			}
		}
		if (masterDepartment.get().getBusinessUnitId() != null
				&& !masterDepartment.get().getBusinessUnitId().isEmpty()) {
			Optional<BusinessUnit> bUnit = businessUnitRepository
					.findById(masterDepartment.get().getBusinessUnitId());
			if (bUnit.isPresent()) {
				masterDepartment.get().setBusinessUnitId(bUnit.get().getBusinessUnitName());
			}
		}
		Set<String> projects = new HashSet<>();
		Set<String> resources = new HashSet<>();
		MasterDepartmentProjectDto masterDepartmentProjectDto = new MasterDepartmentProjectDto();
		List<ProjectInformation> projectInformations = projectInformationRepository.findByMasterDepartmentId(id);
		if (!CollectionUtils.isEmpty(projectInformations)) {
			for (ProjectInformation projectInformation : projectInformations) {
				projects.add(projectInformation.getProjectName());
				if (!CollectionUtils.isEmpty(projectInformation.getAssignedResources())) {
					for (String ids : projectInformation.getAssignedResources()) {
						Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(ids);
						if (employee.isPresent()) {
							resources.add(employee.get().getEmployeeCode() + "-" + employee.get().getName());
						}
					}
				}
			}
			masterDepartmentProjectDto.setMasterDepartment(masterDepartment.get());
			if (!CollectionUtils.isEmpty(projects)) {
				masterDepartmentProjectDto.setProjects(projects.stream().collect(Collectors.toList()));
			} else {
				masterDepartmentProjectDto.setProjects(null);
			}
			if (!CollectionUtils.isEmpty(resources)) {
				masterDepartmentProjectDto.setResources(resources.stream().collect(Collectors.toList()));
			} else {
				masterDepartmentProjectDto.setResources(null);
			}

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", masterDepartmentProjectDto);
		} else {
			masterDepartmentProjectDto.setMasterDepartment(masterDepartment.get());
			masterDepartmentProjectDto.setProjects(null);
			masterDepartmentProjectDto.setResources(null);

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", masterDepartmentProjectDto);
		}
		return entity;
	}
}
