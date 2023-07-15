package com.panel.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.util.CollectionUtils;

import com.panel.dto.AssignedResourcesDesigDto;
import com.panel.dto.DesignationsListDto;
import com.panel.dto.DesignationsUpdateListDto;
import com.panel.dto.Designationsdto;
import com.panel.model.AssignedResourceDesig;
import com.panel.model.Designations;
import com.panel.model.EmployeeDetails;
import com.panel.model.ProjectEmployee;
import com.panel.model.ProjectInformation;
import com.panel.model.UpdateDesignations;
import com.panel.repository.AssignedResourcesDesigRepository;
import com.panel.repository.DesignationsRepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.ProjectEmployeesRepository;
import com.panel.repository.ProjectInformationRepository;
import com.panel.repository.UpdateDesignationsRepository;
import com.panel.service.DesignationsService;

@Service
public class DesignationServiceImpl implements DesignationsService {

	@Autowired
	DesignationsRepository designationsRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	AssignedResourcesDesigRepository assignedResourcesDesigRepository;

	@Autowired
	UpdateDesignationsRepository updateDesignationsRepository;

	@Autowired
	ProjectEmployeesRepository projectEmployeesRepository;

	@Autowired
	ProjectInformationRepository projectInformationRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	public LinkedHashMap<String, Object> saveOrUpdateDesignations(Designationsdto designationsdto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		int i = 0;
		if (designationsdto.getId() == null) {

			Designations designations = new Designations();
			BeanUtils.copyProperties(designationsdto, designations);

			designations.setCreatedDate(LocalDate.now());
			designations.setUpdateddate(LocalDate.now());

			if (designationsdto.getCreatedBy() != null) {
//				Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(designationsdto.getCreatedBy());
//				if (employeeDetails.isPresent()) {
				designations.setCreatedBy(designationsdto.getCreatedBy());
//				}
			} else {
				designations.setCreatedBy(designations.getCreatedBy());
			}
			if (designationsdto.getUpdatedBy() != null) {
//				Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(designationsdto.getUpdatedBy());
//				if (employeeDetails.isPresent()) {
				designations.setUpdatedBy(designationsdto.getUpdatedBy());
//				}
			} else {
				designations.setUpdatedBy(designations.getUpdatedBy());
			}

			List<Designations> desigs = designationsRepository.findAll();
			for (Designations des : desigs) {
				if (des.getDesignationName().equalsIgnoreCase(designationsdto.getDesignationName().trim())) {
					i++;
				}
			}

			if (i == 0) {
				designationsRepository.save(designations);
				entity.put("status", "200");
				entity.put("message", "Designations Saved Successfully");
				entity.put("data", designations);
			} else {
				entity.put("status", "400");
				entity.put("message", "Designations Already exist");
			}

		} else {

			Optional<Designations> designation = designationsRepository.findOneById(designationsdto.getId());
			if (designation.get().getId() != null) {

				Designations designations = designation.get();
				// BeanUtils.copyProperties(designationsdto,
				// designations,getNullPropertyNames(designations));

				designations.setDesignationName(designationsdto.getDesignationName());
				designations.setDesignationDescription(designationsdto.getDesignationDescription());
				designations.setCreatedDate(designations.getCreatedDate());
				designations.setUpdateddate(LocalDate.now());

				if (designationsdto.getCreatedBy() != null) {
//					Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(designationsdto.getCreatedBy());
//					if (employeeDetails.isPresent()) {
					designations.setCreatedBy(designationsdto.getCreatedBy());
//					}
				} else {
					designations.setCreatedBy(designations.getCreatedBy());
				}
				if (designationsdto.getUpdatedBy() != null) {
//					Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(designationsdto.getUpdatedBy());
//					if (employeeDetails.isPresent()) {
					designations.setUpdatedBy(designationsdto.getUpdatedBy());
//					}
				} else {
					designations.setUpdatedBy(designations.getUpdatedBy());
				}

				UpdateDesignations update = new UpdateDesignations();
				update.setDesignationName(designations.getDesignationName());
				update.setDesignationDescription(designations.getDesignationDescription());
				update.setUpdateddate(LocalDate.now());
				update.setUpdatedBy(designations.getUpdatedBy());
				update.setDesignations(designations);
				updateDesignationsRepository.save(update);

				designationsRepository.save(designations);
				entity.put("status", "200");
				entity.put("message", "Designations Updated Successfully");
				entity.put("data", designations);

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

	public LinkedHashMap<String, Object> getDesignationsList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<DesignationsListDto> designationsListDtos = new ArrayList<>();

		List<Designations> designation = designationsRepository.findAll();

		for (Designations designations : designation) {
			DesignationsListDto designationsListDto = new DesignationsListDto();

			designationsListDto.setDesignationName(designations.getDesignationName());
			designationsListDto.setCreatedDate(designations.getCreatedDate());
			Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(designations.getCreatedBy());
			if (employee.isPresent()) {
				designationsListDto.setCreatdBy(employee.get().getName());
			}
			designationsListDtos.add(designationsListDto);

		}
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", designationsListDtos);

		return entity;
	}

	public LinkedHashMap<String, Object> getUpdatedDesignationList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<DesignationsUpdateListDto> designationsUpdateListDtos = new ArrayList<>();

		List<Designations> designation = designationsRepository.findAll();

		for (Designations designations : designation) {
			DesignationsUpdateListDto designationsUpdateListDto = new DesignationsUpdateListDto();

			designationsUpdateListDto.setUpdateddate(designations.getUpdateddate());
			if (designations.getUpdatedBy() != null) {
				Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(designations.getUpdatedBy());
				if (employee.isPresent()) {
					designationsUpdateListDto.setUpdatedBy(employee.get().getName());
				}
			}
			designationsUpdateListDtos.add(designationsUpdateListDto);

		}
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", designationsUpdateListDtos);

		return entity;
	}

	public LinkedHashMap<String, Object> getDesignationById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<ProjectEmployee> projectEmployees = projectEmployeesRepository.findAll();

		Map<String, Object> assign = new LinkedHashMap<String, Object>();

		Optional<Designations> designations = designationsRepository.findById(id);
		if (designations.isPresent()) {
			List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findAll();
			for (EmployeeDetails employeeDetail : employeeDetails) {
				List<String> project = new ArrayList<>();
				if (employeeDetail.getEmploymentDesignation() != null) {
					if (designations.get().getId().equals(employeeDetail.getEmploymentDesignation().getId())) {
						if (!CollectionUtils.isEmpty(projectEmployees)) {
							for (ProjectEmployee projectEmployee : projectEmployees) {
//						String s = employeeDetail.getEmployeeCode()+"-"+employeeDetail.getName();
								if (!projectEmployee.isRemoved()) {
									if (projectEmployee.getEmployeeId().equals(employeeDetail.getId())) {
										Optional<ProjectInformation> projectInfo = projectInformationRepository
												.findById(projectEmployee.getProjectId());
										project.add(projectInfo.get().getProjectName());
									}
								}
							}

							assign.put(employeeDetail.getName(), project);
						}
					}
				}
			}

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", designations);
			entity.put("Resources", assign);

		} else {
			entity.put("Status", "405");
			entity.put("Message", "Data is not present");

		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllDesignationsList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<DesignationsListDto> designationsListDtos = new ArrayList<>();

		List<Designations> designation = designationsRepository.findAll();

		List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findAll();

		for (Designations designations : designation) {
			int count = 0;
			for (EmployeeDetails employeeDetail : employeeDetails) {
				if (employeeDetail.getEmploymentDesignation() != null) {
					if (designations.getId().equals(employeeDetail.getEmploymentDesignation().getId())) {
						count = count + 1;
					}
					designations.setNoOfResources(count);
				}
			}
			if (designations.getCreatedBy() != null) {
				Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(designations.getCreatedBy());
				if (employee.isPresent()) {
					designations.setCreatedBy(employee.get().getName());
				}
			}
			if (designations.getUpdatedBy() != null) {
				Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(designations.getUpdatedBy());
				if (employee.isPresent()) {
					designations.setUpdatedBy(employee.get().getName());
				}
			}
		}

		if (designation.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", designation);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getDesignationsFilter(Designationsdto designationsdto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Query query = new Query();

		if (designationsdto.getDesignationName() != null && !designationsdto.getDesignationName().equals("")) {
			query.addCriteria(Criteria.where("designationName").is(designationsdto.getDesignationName()));
		}

		if (designationsdto.getCreatedBy() != null && !designationsdto.getCreatedBy().equals("")) {
			query.addCriteria(Criteria.where("createdBy").is(designationsdto.getCreatedBy()));
		}

		List<Designations> designations = mongoTemplate.find(query, Designations.class);
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", designations);

		return entity;
	}

	public LinkedHashMap<String, Object> saveOrUpdateAssignedResources(
			AssignedResourcesDesigDto assignedResourcesDesigDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		if (assignedResourcesDesigDto.getId() == null) {
			if (assignedResourcesDesigDto.getEmployeeCode() != null) {
				Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository
						.findByEmployeeCode(assignedResourcesDesigDto.getEmployeeCode());
				if (employeeDetail.isPresent()) {

					AssignedResourceDesig assignedResourceDesig = new AssignedResourceDesig();
					BeanUtils.copyProperties(assignedResourcesDesigDto, assignedResourceDesig);

					assignedResourceDesig.setEmployeeCode(employeeDetail.get().getEmployeeCode());
					assignedResourceDesig.setEmpName(employeeDetail.get().getName());
					assignedResourceDesig.setResponsibilities(assignedResourcesDesigDto.getResponsibilities());
					assignedResourceDesig.setCreatedDate(LocalDate.now());
					assignedResourceDesig.setUpdateddate(LocalDate.now());

					if (assignedResourcesDesigDto.getDesignationName() != null) {
						Optional<Designations> designations = designationsRepository
								.findById(assignedResourcesDesigDto.getDesignationName());
						if (designations.isPresent()) {
							assignedResourceDesig.setDesignations(designations.get());
							;
						}
					} else {
						assignedResourceDesig.setDesignations(null);
					}
					if (assignedResourcesDesigDto.getCreatedBy() != null) {
						Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
								.findById(assignedResourcesDesigDto.getCreatedBy());
						if (employeeDetails.isPresent()) {
							assignedResourceDesig.setCreatedBy(employeeDetails.get().getName());
						}
					} else {
						assignedResourceDesig.setCreatedBy(assignedResourceDesig.getCreatedBy());
					}
					if (assignedResourcesDesigDto.getUpdatedBy() != null) {
						Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
								.findById(assignedResourcesDesigDto.getUpdatedBy());
						if (employeeDetails.isPresent()) {
							assignedResourceDesig.setUpdatedBy(employeeDetails.get().getName());
						}
					} else {
						assignedResourceDesig.setUpdatedBy(assignedResourceDesig.getUpdatedBy());
					}

					assignedResourcesDesigRepository.save(assignedResourceDesig);
					entity.put("Status", "200");
					entity.put("Message", "Assign Resources Saved Successfully");
					entity.put("Data", assignedResourceDesig);

				} else {
					entity.put("Status", "400");
					entity.put("Message", "Invalid EmployeeCode");
				}
			}
		} else {

			Optional<AssignedResourceDesig> assignedResourceDesig = assignedResourcesDesigRepository
					.findById(assignedResourcesDesigDto.getId());
			if (assignedResourceDesig.get().getId() != null) {
				if (assignedResourcesDesigDto.getEmployeeCode() != null) {
					Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository
							.findByEmployeeCode(assignedResourcesDesigDto.getEmployeeCode());
					if (employeeDetail.isPresent()) {

						AssignedResourceDesig assignedResourceDesigs = assignedResourceDesig.get();
						// BeanUtils.copyProperties(employmentTypesDto,
						// employmentTypes,getNullPropertyNames(employmentTypes));
						assignedResourceDesigs.setCreatedDate(assignedResourceDesigs.getCreatedDate());
						assignedResourceDesigs.setUpdateddate(LocalDate.now());
						assignedResourceDesigs.setEmpName(employeeDetail.get().getName());

						if (employeeDetail.isPresent()) {
							assignedResourceDesigs.setEmployeeCode(employeeDetail.get().getEmployeeCode());
						} else {
							assignedResourceDesigs.setEmployeeCode(assignedResourceDesigs.getEmployeeCode());
						}

						if (assignedResourcesDesigDto.getResponsibilities() != null) {
							assignedResourceDesigs.setResponsibilities(assignedResourcesDesigDto.getResponsibilities());
						} else {
							assignedResourceDesigs.setResponsibilities(assignedResourceDesigs.getResponsibilities());
						}

						if (assignedResourcesDesigDto.getDesignationName() != null) {
							Optional<Designations> designations = designationsRepository
									.findById(assignedResourcesDesigDto.getDesignationName());
							if (designations.isPresent()) {
								assignedResourceDesigs.setDesignations(designations.get());
								;
							}
						} else {
							assignedResourceDesigs.setDesignations(assignedResourceDesigs.getDesignations());
						}

						if (assignedResourcesDesigDto.getCreatedBy() != null) {
							Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
									.findById(assignedResourcesDesigDto.getCreatedBy());
							if (employeeDetails.isPresent()) {
								assignedResourceDesigs.setCreatedBy(employeeDetails.get().getName());
							}
						} else {
							assignedResourceDesigs.setCreatedBy(assignedResourceDesigs.getCreatedBy());
						}
						if (assignedResourcesDesigDto.getUpdatedBy() != null) {
							Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
									.findById(assignedResourcesDesigDto.getUpdatedBy());
							if (employeeDetails.isPresent()) {
								assignedResourceDesigs.setUpdatedBy(employeeDetails.get().getName());
							}
						} else {
							assignedResourceDesigs.setUpdatedBy(assignedResourceDesigs.getUpdatedBy());
						}

						assignedResourcesDesigRepository.save(assignedResourceDesigs);
						entity.put("Status", "200");
						entity.put("Message", "Assign Resources Updated Successfully");
						entity.put("Data", assignedResourceDesigs);
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

		List<AssignedResourceDesig> assignedResourceDesigs = assignedResourcesDesigRepository.findAll();

		if (assignedResourceDesigs.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", assignedResourceDesigs);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllUpdateDesignationsList(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<UpdateDesignations> updateList = updateDesignationsRepository.findAll();

		List<UpdateDesignations> listup = new ArrayList<>();

		for (UpdateDesignations updateDesignations : updateList) {
			if (updateDesignations.getDesignations().getId().equals(id)) {
				listup.add(updateDesignations);
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

	public LinkedHashMap<String, Object> getUpdateDesignationsById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<UpdateDesignations> designations = updateDesignationsRepository.findById(id);
		if (designations.isPresent()) {

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", designations);

		} else {
			entity.put("Status", "405");
			entity.put("Message", "Data is not present");

		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAssignedResourcesList(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<AssignedResourceDesig> assignList = assignedResourcesDesigRepository.findAll();

		List<AssignedResourceDesig> listup = new ArrayList<>();

		for (AssignedResourceDesig assignedResourceDesig : assignList) {
			if (assignedResourceDesig.getDesignations().getId().equals(id)) {
				listup.add(assignedResourceDesig);
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

}
