package com.panel.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.panel.dto.AssignedResourcesEmpTypeDto;
import com.panel.dto.EmploymentTypesDto;
import com.panel.dto.EmploymentTypesListDto;
import com.panel.dto.EmploymentTypesUpdateListDto;
import com.panel.model.AssignedResourcesEmpType;
import com.panel.model.EmployeeDetails;
import com.panel.model.EmploymentTypes;
import com.panel.model.UpdateEmploymentType;
import com.panel.repository.AssignedResourcesEmpTypeRepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.EmploymentTypeRepository;
import com.panel.repository.UpdateEmploymentTypesRepository;
import com.panel.service.EmploymentTypeService;

@Service
public class EmploymentTypeServiceImpl implements EmploymentTypeService {

	@Autowired
	EmploymentTypeRepository employmentTypeRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	AssignedResourcesEmpTypeRepository assignedResourcesRepository;

	@Autowired
	UpdateEmploymentTypesRepository updateEmploymentTypesRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	public LinkedHashMap<String, Object> saveOrUpdateEmploymentTypes(EmploymentTypesDto employmentTypesDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		int i = 0;
		if (employmentTypesDto.getId() == null) {

			EmploymentTypes employmentTypes = new EmploymentTypes();
			BeanUtils.copyProperties(employmentTypesDto, employmentTypes);

			employmentTypes.setCreatedDate(LocalDate.now());
			employmentTypes.setUpdateddate(LocalDate.now());

			if (employmentTypesDto.getCreatedBy() != null) {
//				Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
//						.findById(employmentTypesDto.getCreatedBy());
//				if (employeeDetails.isPresent()) {
				employmentTypes.setCreatedBy(employmentTypesDto.getCreatedBy());
//				}
			} else {
				employmentTypes.setCreatedBy(employmentTypes.getCreatedBy());
			}
			if (employmentTypesDto.getUpdatedBy() != null) {
//				Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
//						.findById(employmentTypesDto.getUpdatedBy());
//				if (employeeDetails.isPresent()) {
				employmentTypes.setUpdatedBy(employmentTypesDto.getUpdatedBy());
//				}
			} else {
				employmentTypes.setUpdatedBy(employmentTypes.getUpdatedBy());
			}

			List<EmploymentTypes> empTyps = employmentTypeRepository.findAll();

			for (EmploymentTypes types : empTyps) {
				if (types.getEmploymentTypesName().equalsIgnoreCase(employmentTypesDto.getEmploymentTypesName())) {
					i++;
				}
			}

			if (i == 0) {
				employmentTypeRepository.save(employmentTypes);
				entity.put("status", "200");
				entity.put("message", "EmploymentType Saved Successfully");
				entity.put("data", employmentTypes);
			} else {
				entity.put("status", "400");
				entity.put("message", "EmploymentType already exists");
			}

		} else {

			Optional<EmploymentTypes> employmentType = employmentTypeRepository.findOneById(employmentTypesDto.getId());
			if (employmentType.get().getId() != null) {

				EmploymentTypes employmentTypes = employmentType.get();
				// BeanUtils.copyProperties(employmentTypesDto,
				// employmentTypes,getNullPropertyNames(employmentTypes));
				employmentTypes.setEmploymentTypesName(employmentTypesDto.getEmploymentTypesName());
				employmentTypes.setEmploymentTypesDescription(employmentTypesDto.getEmploymentTypesDescription());
				employmentTypes.setCreatedDate(employmentTypes.getCreatedDate());
				employmentTypes.setUpdateddate(LocalDate.now());

				if (employmentTypesDto.getCreatedBy() != null) {
//					Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
//							.findById(employmentTypesDto.getCreatedBy());
//					if (employeeDetails.isPresent()) {
					employmentTypes.setCreatedBy(employmentTypesDto.getCreatedBy());
//					}
				} else {
					employmentTypes.setCreatedBy(employmentTypes.getCreatedBy());
				}
				if (employmentTypesDto.getUpdatedBy() != null) {
//					Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
//							.findById(employmentTypesDto.getUpdatedBy());
//					if (employeeDetails.isPresent()) {
					employmentTypes.setUpdatedBy(employmentTypesDto.getUpdatedBy());
//					}
				} else {
					employmentTypes.setUpdatedBy(employmentTypes.getUpdatedBy());
				}

				UpdateEmploymentType update = new UpdateEmploymentType();
				update.setEmploymentTypesName(employmentTypes.getEmploymentTypesName());
				update.setEmploymentTypesDescription(employmentTypes.getEmploymentTypesDescription());
				update.setUpdateddate(LocalDate.now());
				update.setUpdatedBy(employmentTypes.getUpdatedBy());
				update.setEmploymentTypes(employmentTypes);
				updateEmploymentTypesRepository.save(update);

				employmentTypeRepository.save(employmentTypes);
				entity.put("status", "200");
				entity.put("message", "EmploymentType Updated Successfully");
				entity.put("data", employmentTypes);

			} else {
				entity.put("status", "405");
				entity.put("message", "couldn't updated");
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

	public LinkedHashMap<String, Object> getEmploymenttypesList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<EmploymentTypesListDto> employmentTypesListDtos = new ArrayList<>();

		List<EmploymentTypes> employmentTypes = employmentTypeRepository.findAll();

		for (EmploymentTypes employmentType : employmentTypes) {
			EmploymentTypesListDto employmentTypesListDto = new EmploymentTypesListDto();

			employmentTypesListDto.setEmploymentTypesName(employmentType.getEmploymentTypesName());
			employmentTypesListDto.setCreatedDate(employmentType.getCreatedDate());
			employmentTypesListDto.setCreatdBy(employmentType.getCreatedBy());
			employmentTypesListDtos.add(employmentTypesListDto);

		}
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", employmentTypesListDtos);

		return entity;
	}

	public LinkedHashMap<String, Object> getUpdateEmploymenttypesList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<EmploymentTypesUpdateListDto> employmentTypesUpdateListDtos = new ArrayList<>();

		List<EmploymentTypes> employmentTypes = employmentTypeRepository.findAll();

		for (EmploymentTypes employmentType : employmentTypes) {
			EmploymentTypesUpdateListDto employmentTypesUpdateListDto = new EmploymentTypesUpdateListDto();
			employmentTypesUpdateListDto.setUpdatedBy(employmentType.getUpdatedBy());
			employmentTypesUpdateListDto.setUpdateddate(employmentType.getUpdateddate());
			employmentTypesUpdateListDtos.add(employmentTypesUpdateListDto);

		}
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", employmentTypesUpdateListDtos);

		return entity;
	}

	public LinkedHashMap<String, Object> getEmploymentTypesById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<String> assign = new ArrayList<>();
		Optional<EmploymentTypes> employmentTypes = employmentTypeRepository.findById(id);
		if (employmentTypes.isPresent()) {
			List<EmployeeDetails> employeedetails = employeeDetailsRepository.findAll();
			for (EmployeeDetails employeeDetails : employeedetails) {
				if (employeeDetails.getEmployeeCategory() != null) {
					if (employmentTypes.get().getId().equals(employeeDetails.getEmployeeCategory().getId())) {
						if (employeeDetails.getBusinessUnit() != null) {
							assign.add(employeeDetails.getName() + "-"
									+ employeeDetails.getBusinessUnit().getBusinessUnitName());
						} else {
							assign.add(employeeDetails.getName());
						}
					}
				}
			}

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", employmentTypes);
			entity.put("Resources", assign);

		} else {
			entity.put("Status", "405");
			entity.put("Message", "Data is not present");

		}

		return entity;
	}
	/*
	 * public LinkedHashMap<String, Object> getAllEmploymentTypesList() {
	 * LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
	 * 
	 * List<EmploymentTypesDto> employmentTypesDtos = new ArrayList<>();
	 * 
	 * List<EmploymentTypes> employmentTypes = employmentTypeRepository.findAll();
	 * List<AssignedResourcesEmpType> assignedResourcesEmpTypes =
	 * assignedResourcesRepository.findAll();
	 * 
	 * for(EmploymentTypes employmenttype : employmentTypes) { int count = 0;
	 * for(AssignedResourcesEmpType assignedResource : assignedResourcesEmpTypes) {
	 * if(employmenttype.getEmploymentTypesName().equals(assignedResource.
	 * getEmploymentTypes().getEmploymentTypesName())) { count = count + 1; }
	 * employmenttype.setNoOfResources(count); } }
	 * 
	 * if (employmentTypes.size() > 0) { entity.put("Status", "200");
	 * entity.put("Message", "Data is present"); entity.put("Data",
	 * employmentTypes); } else { entity.put("Status", "400"); entity.put("Message",
	 * "No Data is present"); }
	 * 
	 * return entity; }
	 */

	public LinkedHashMap<String, Object> getEmploymentTypesFilter(EmploymentTypesDto employmentTypesDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Query query = new Query();

		if (employmentTypesDto.getEmploymentTypesName() != null
				&& !employmentTypesDto.getEmploymentTypesName().equals("")) {
			query.addCriteria(Criteria.where("employmentTypesName").is(employmentTypesDto.getEmploymentTypesName()));
		}

		if (employmentTypesDto.getCreatedBy() != null && !employmentTypesDto.getCreatedBy().equals("")) {
			query.addCriteria(Criteria.where("createdBy").is(employmentTypesDto.getCreatedBy()));
		}

		List<EmploymentTypes> employmentTypes = mongoTemplate.find(query, EmploymentTypes.class);
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", employmentTypes);

		return entity;
	}

	public LinkedHashMap<String, Object> saveOrUpdateAssignedResources(
			AssignedResourcesEmpTypeDto assignedResourcesDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		if (assignedResourcesDto.getId() == null) {
			if (assignedResourcesDto.getEmployeeCode() != null) {
				Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository
						.findByEmployeeCode(assignedResourcesDto.getEmployeeCode());
				if (employeeDetail.isPresent()) {

					AssignedResourcesEmpType assignedResources = new AssignedResourcesEmpType();
					BeanUtils.copyProperties(assignedResourcesDto, assignedResources);
					assignedResources.setEmployeeCode(employeeDetail.get().getEmployeeCode());
					assignedResources.setEmpName(employeeDetail.get().getName());
					assignedResources.setCreatedDate(LocalDate.now());
					assignedResources.setUpdateddate(LocalDate.now());
					assignedResources.setResponsibilities(assignedResourcesDto.getResponsibilities());

					if (assignedResourcesDto.getEmploymentTypesName() != null) {
						Optional<EmploymentTypes> employmentTypes = employmentTypeRepository
								.findById(assignedResourcesDto.getEmploymentTypesName());
						if (employmentTypes.isPresent()) {
							assignedResources.setEmploymentTypes(employmentTypes.get());
						}

					} else {
						assignedResources.setEmploymentTypes(null);
					}
					if (assignedResourcesDto.getCreatedBy() != null) {
//						Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
//								.findById(assignedResourcesDto.getCreatedBy());
//						if (employeeDetails.isPresent()) {
						assignedResources.setCreatedBy(assignedResourcesDto.getCreatedBy());
//						}
					} else {
						assignedResources.setCreatedBy(assignedResources.getCreatedBy());
					}
					if (assignedResourcesDto.getUpdatedBy() != null) {
//						Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
//								.findById(assignedResourcesDto.getUpdatedBy());
//						if (employeeDetails.isPresent()) {
						assignedResources.setUpdatedBy(assignedResourcesDto.getUpdatedBy());
//						}
					} else {
						assignedResources.setUpdatedBy(assignedResources.getUpdatedBy());
					}

					assignedResourcesRepository.save(assignedResources);
					entity.put("Status", "200");
					entity.put("Message", "Assign Resources Saved Successfully");
					entity.put("Data", assignedResources);

				} else {
					entity.put("Status", "400");
					entity.put("Message", "Invalid EmployeeCode");
				}
			}
		} else {

			Optional<AssignedResourcesEmpType> assignedResources = assignedResourcesRepository
					.findById(assignedResourcesDto.getId());
			if (assignedResources.get().getId() != null) {
				if (assignedResourcesDto.getEmployeeCode() != null) {
					Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository
							.findByEmployeeCode(assignedResourcesDto.getEmployeeCode());
					if (employeeDetail.isPresent()) {

						AssignedResourcesEmpType assignedResource = assignedResources.get();
						// BeanUtils.copyProperties(assignedResourcesDto,assignedResource,getNullPropertyNames(assignedResource));
						assignedResource.setCreatedDate(assignedResource.getCreatedDate());
						assignedResource.setUpdateddate(LocalDate.now());

						if (employeeDetail.isPresent()) {
							assignedResource.setEmployeeCode(employeeDetail.get().getEmployeeCode());
						} else {
							assignedResource.setEmployeeCode(assignedResource.getEmployeeCode());
						}
						assignedResource.setEmpName(employeeDetail.get().getName());

						if (assignedResourcesDto.getResponsibilities() != null) {
							assignedResource.setResponsibilities(assignedResourcesDto.getResponsibilities());
						} else {
							assignedResource.setResponsibilities(assignedResource.getResponsibilities());
						}

						if (assignedResourcesDto.getEmploymentTypesName() != null) {
							Optional<EmploymentTypes> employmentTypes = employmentTypeRepository
									.findById(assignedResourcesDto.getEmploymentTypesName());
							if (employmentTypes.isPresent()) {
								assignedResource.setEmploymentTypes(employmentTypes.get());
							}
						} else {
							assignedResource.setEmploymentTypes(assignedResource.getEmploymentTypes());
						}

						if (assignedResourcesDto.getCreatedBy() != null) {
//							Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
//									.findById(assignedResourcesDto.getCreatedBy());
//							if (employeeDetails.isPresent()) {
							assignedResource.setCreatedBy(assignedResourcesDto.getCreatedBy());
//							}
						} else {
							assignedResource.setCreatedBy(assignedResource.getCreatedBy());
						}
						if (assignedResourcesDto.getUpdatedBy() != null) {
//							Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
//									.findById(assignedResourcesDto.getUpdatedBy());
//							if (employeeDetails.isPresent()) {
							assignedResource.setUpdatedBy(assignedResourcesDto.getUpdatedBy());
//							}
						} else {
							assignedResource.setUpdatedBy(assignedResource.getUpdatedBy());
						}

						assignedResourcesRepository.save(assignedResource);
						entity.put("Status", "200");
						entity.put("Message", "Assign Resources Updated Successfully");
						entity.put("Data", assignedResource);
					} else {
						entity.put("Status", "400");
						entity.put("Message", "Invalid EmployeeCode");
					}
				}
			} else {
				entity.put("Status", "405");
				entity.put("Message", "couldn't updated");
			}
		}
		return entity;
	}

	public LinkedHashMap<String, Object> getAllAssignedResourcesList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<AssignedResourcesEmpType> assignedResources = assignedResourcesRepository.findAll();

		if (assignedResources.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", assignedResources);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllUpdateEmploymentTypesList(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<UpdateEmploymentType> updateList = updateEmploymentTypesRepository.findAll();

		List<UpdateEmploymentType> listup = new ArrayList<>();

		for (UpdateEmploymentType updateEmploymentType : updateList) {
			if (updateEmploymentType.getEmploymentTypes().getId().equals(id)) {
				listup.add(updateEmploymentType);
			}
		}

		if (listup.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", listup);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getUpdateEmploymentTypesById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<UpdateEmploymentType> employmentTypes = updateEmploymentTypesRepository.findById(id);
		if (employmentTypes.isPresent()) {

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", employmentTypes);

		} else {
			entity.put("Status", "405");
			entity.put("Message", "Data is not present");

		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAssignedResourcesList(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<AssignedResourcesEmpType> assignList = assignedResourcesRepository.findAll();

		List<AssignedResourcesEmpType> listup = new ArrayList<>();

		for (AssignedResourcesEmpType assignedResourcesEmpType : assignList) {
			if (assignedResourcesEmpType.getEmploymentTypes().getId().equals(id)) {
				listup.add(assignedResourcesEmpType);
			}
		}

		if (listup.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", listup);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllEmploymentTypesList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<EmploymentTypesDto> employmentTypesDtos = new ArrayList<>();

		List<EmploymentTypes> employmentTypes = employmentTypeRepository.findAll();
		List<EmployeeDetails> employeedetails = employeeDetailsRepository.findAll();

		for (EmploymentTypes employmenttype : employmentTypes) {
			int count = 0;
			for (EmployeeDetails employeedetail : employeedetails) {
				if (employeedetail.getEmployeeCategory() != null) {
					if (employmenttype.getId().equals(employeedetail.getEmployeeCategory().getId())) {
						count = count + 1;
					}
					employmenttype.setNoOfResources(count);
				}
			}
			if (employmenttype.getCreatedBy() != null) {
				Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(employmenttype.getCreatedBy());
				if (employee.isPresent()) {
					employmenttype.setCreatedBy(employee.get().getName());
				}
			}
			if (employmenttype.getUpdatedBy() != null) {
				Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(employmenttype.getUpdatedBy());
				if (employee.isPresent()) {
					employmenttype.setUpdatedBy(employee.get().getName());
				}
			}

		}

		if (employmentTypes.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", employmentTypes);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

}
