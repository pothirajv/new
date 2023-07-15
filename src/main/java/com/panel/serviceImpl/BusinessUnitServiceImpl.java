package com.panel.serviceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.panel.dto.BusinessUnitDto;
import com.panel.model.BusinessUnit;
import com.panel.model.EmployeeDetails;
import com.panel.model.MasterDepartment;
import com.panel.repository.BusinessUnitRepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.MasterDepartmentRepository;
import com.panel.service.BusinessUnitService;
import com.panel.support.constant.GeneralConstants;

@Service
public class BusinessUnitServiceImpl implements BusinessUnitService {

	@Autowired
	BusinessUnitRepository businessUnitRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	MasterDepartmentRepository masterDepartmentRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	public LinkedHashMap<String, Object> saveAndUpdateBusinessUnit(BusinessUnitDto businessUnitDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		int i = 0;
		if (businessUnitDto.getId() == null) {
			Optional<BusinessUnit> businessUnits = businessUnitRepository
					.findByBusinessUnitCode(businessUnitDto.getBusinessUnitCode());
			if (!businessUnits.isPresent()) {

				BusinessUnit businessUnit = new BusinessUnit();
				BeanUtils.copyProperties(businessUnitDto, businessUnit);

				if (businessUnitDto.getBusinessUnitAlternate() != null
						&& !businessUnitDto.getBusinessUnitAlternate().isEmpty()) {
					Optional<EmployeeDetails> employeeAl = employeeDetailsRepository
							.findById(businessUnitDto.getBusinessUnitAlternate());
					if (employeeAl.isPresent()) {
						businessUnit.setBusinessUnitAlternate(employeeAl.get().getId());
					}
				} else {
					businessUnit.setBusinessUnitAlternate(null);
				}
				if (businessUnitDto.getBusinessUnitHead() != null && !businessUnitDto.getBusinessUnitHead().isEmpty()) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(businessUnitDto.getBusinessUnitHead());
					if (employee.isPresent()) {
						businessUnit.setBusinessUnitHead(employee.get().getId());
					}
				}
				List<BusinessUnit> bus = businessUnitRepository.findAll();

				for (BusinessUnit unit : bus) {
					if (unit.getBusinessUnitName().equalsIgnoreCase(businessUnitDto.getBusinessUnitName().trim())) {
						i++;
					}
				}
				if (i == 0) {
					businessUnitRepository.save(businessUnit);
					entity.put("status", "200");
					entity.put("message", "Business Unit Saved Successfully");
					entity.put("data", businessUnit);
				} else {
					entity.put("status", "400");
					entity.put("message", "Business Unit Name is already exist..");
				}

			} else {
				entity.put("status", "400");
				entity.put("message", "Business Unit code is already exist..");
			}

		} else {

			Optional<BusinessUnit> businessUnit = businessUnitRepository.findOneById(businessUnitDto.getId());
			if (businessUnit.get().getId() != null) {
				BusinessUnit businessUnits = businessUnit.get();

				BeanUtils.copyProperties(businessUnitDto, businessUnits); /* getNullPropertyNames(businessUnitDto) */

				Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository
						.findById(businessUnitDto.getBusinessUnitHead());
				if (employeeDetails.isPresent()) {
					if (businessUnitDto.getBusinessUnitHead() != null
							&& !businessUnitDto.getBusinessUnitHead().isEmpty()) {
						businessUnits.setBusinessUnitHead(businessUnitDto.getBusinessUnitHead());
					} else {
						businessUnits.setBusinessUnitHead(businessUnits.getBusinessUnitHead());
					}
//					businessUnits.setBusinessUnitHead(employeeDetails.get().getId());
					if (businessUnitDto.getBusinessUnitAlternate() != null
							&& !businessUnitDto.getBusinessUnitAlternate().isEmpty()) {
						businessUnits.setBusinessUnitAlternate(businessUnitDto.getBusinessUnitAlternate());
					} else {
						businessUnits.setBusinessUnitHead(businessUnits.getBusinessUnitHead());
					}
					businessUnitRepository.save(businessUnits);
					entity.put("status", "200");
					entity.put("message", "Business Unit Updated Successfully");
					entity.put("data", businessUnits);

				} else {
					entity.put("status", "400");
					entity.put("message", "Enter correct employee code");
				}
			} else {
				entity.put("status", "405");
				entity.put("message", "couldn't updated");
			}
		}
		return entity;
	}
	/*
	 * public static String[] getNullPropertyNames(Object source) { final
	 * BeanWrapper src = new BeanWrapperImpl(source);
	 * java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
	 * 
	 * Set<String> emptyNames = new HashSet<String>(); for
	 * (java.beans.PropertyDescriptor pd : pds) { Object srcValue =
	 * src.getPropertyValue(pd.getName()); if (srcValue == null ||
	 * srcValue.toString().equals("0")) emptyNames.add(pd.getName()); }
	 * 
	 * String[] result = new String[emptyNames.size()]; return
	 * emptyNames.toArray(result); }
	 */

	public LinkedHashMap<String, Object> getAllBusinessUnits() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<BusinessUnitDto> businessUnitDtos = new ArrayList<>();

		List<BusinessUnit> businessUnits = businessUnitRepository.findAll();

		if (businessUnits.size() > 0) {
			for (BusinessUnit businessUnit : businessUnits) {
				if (businessUnit.getBusinessUnitHead() != null && !businessUnit.getBusinessUnitHead().isEmpty()) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(businessUnit.getBusinessUnitHead());
					if (employee.isPresent()) {
						businessUnit
								.setBusinessUnitHead(employee.get().getEmployeeCode() + "-" + employee.get().getName());
					}
				}
				if (businessUnit.getBusinessUnitAlternate() != null
						&& !businessUnit.getBusinessUnitAlternate().isEmpty()) {
					Optional<EmployeeDetails> employeeOp = employeeDetailsRepository
							.findById(businessUnit.getBusinessUnitAlternate());
					if (employeeOp.isPresent()) {
						businessUnit.setBusinessUnitAlternate(
								employeeOp.get().getEmployeeCode() + "-" + employeeOp.get().getName());
					}
				}
			}

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", businessUnits);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getBusinessUnitById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<BusinessUnit> businessUnit = businessUnitRepository.findById(id);
		if (businessUnit.isPresent()) {
//			if (businessUnit.get().getBusinessUnitHead() != null
//					&& !businessUnit.get().getBusinessUnitHead().isEmpty()) {
//				Optional<EmployeeDetails> employee = employeeDetailsRepository
//						.findById(businessUnit.get().getBusinessUnitHead());
//				businessUnit.get()
//						.setBusinessUnitHead(employee.get().getEmployeeCode() + "-" + employee.get().getName());
//			}
//			if (businessUnit.get().getBusinessUnitAlternate() != null
//					&& !businessUnit.get().getBusinessUnitAlternate().isEmpty()) {
//				Optional<EmployeeDetails> employeeOp = employeeDetailsRepository
//						.findById(businessUnit.get().getBusinessUnitAlternate());
//				if (employeeOp.isPresent()) {
//					businessUnit.get().setBusinessUnitAlternate(
//							employeeOp.get().getEmployeeCode() + "-" + employeeOp.get().getName());
//				}
//			}

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", businessUnit);
		} else {
			entity.put("Status", "405");
			entity.put("Message", "Data is not present");

		}

		return entity;
	}

	public LinkedHashMap<String, Object> getBusinessUnitFilter(BusinessUnitDto businessUnitDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Query query = new Query();

		if (businessUnitDto.getBusinessUnitName() != null && !businessUnitDto.getBusinessUnitName().equals("")) {
			query.addCriteria(Criteria.where("businessUnitName").is(businessUnitDto.getBusinessUnitName()));
		}

		if (businessUnitDto.getBusinessUnitCode() != null && !businessUnitDto.getBusinessUnitCode().equals("")) {
			query.addCriteria(Criteria.where("businessUnitCode").is(businessUnitDto.getBusinessUnitCode()));
		}

		List<BusinessUnit> businessUnits = mongoTemplate.find(query, BusinessUnit.class);
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", businessUnits);

		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getBusinessUnitByHead(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		if (employee.isPresent()) {
			List<BusinessUnit> businessUnits = null;
			if (employee.get().getRole().equals(GeneralConstants.ADMIN)) {
				businessUnits = businessUnitRepository.findAll();
			} else if (employee.get().getRole().equals(GeneralConstants.BUSINESS_UNIT_HEAD)) {
				businessUnits = businessUnitRepository.findByBusinessUnitHead(id);
			} else if (employee.get().getRole().equals(GeneralConstants.DEPARTMENT_HEAD)) {
				List<MasterDepartment> masterDepartments = masterDepartmentRepository.findByDepartmentHead(id);
				if (!CollectionUtils.isEmpty(masterDepartments)) {
					Set<String> bus = new HashSet<>();
					for (MasterDepartment dept : masterDepartments) {
						Optional<BusinessUnit> businessUnit = businessUnitRepository.findById(dept.getBusinessUnitId());
						if (businessUnit.isPresent()) {
							bus.add(businessUnit.get().getId());
						}
					}
					if (!CollectionUtils.isEmpty(bus)) {
						List<BusinessUnit> business = new ArrayList<>();
						for (String ids : bus) {
							Optional<BusinessUnit> busOptional = businessUnitRepository.findById(ids);
							if (busOptional.isPresent()) {
								business.add(busOptional.get());
							}
						}
						businessUnits = business.stream()
								.sorted(Comparator.comparing(BusinessUnit::getBusinessUnitName))
								.collect(Collectors.toList());
					}
				}
			}

			if (!CollectionUtils.isEmpty(businessUnits)) {
				entity.put("Status", "200");
				entity.put("Message", "Data is present");
				entity.put("Data", businessUnits);
			}
		}
		return entity;
	}

}
