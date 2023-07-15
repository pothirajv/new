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

import com.panel.dto.DepartmenListDto;
import com.panel.dto.DepartmentDto;
import com.panel.dto.DepartmentUpdateListDto;
import com.panel.model.Department;
import com.panel.model.EmployeeDetails;
import com.panel.repository.DepartmentRepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	public LinkedHashMap<String, Object> saveOrUpdateDepartment(DepartmentDto departmentDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		if (departmentDto.getId() == null) {

			Department department = new Department();
			BeanUtils.copyProperties(departmentDto, department);

			department.setCreatedDate(LocalDate.now());
			department.setUpdateddate(LocalDate.now());

			if (departmentDto.getCreatedBy() != null) {
//				Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(departmentDto.getCreatedBy());
//				if (employeeDetails.isPresent()) {
				department.setCreatedBy(departmentDto.getCreatedBy());
//				}
			} else {
				department.setCreatedBy(department.getCreatedBy());
			}
			if (departmentDto.getUpdatedBy() != null) {
//				Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(departmentDto.getUpdatedBy());
//				if (employeeDetails.isPresent()) {
				department.setUpdatedBy(departmentDto.getUpdatedBy());
//				}
			} else {
				department.setUpdatedBy(department.getCreatedBy());
			}

			departmentRepository.save(department);
			entity.put("Status", "200");
			entity.put("Message", "Department Saved Successfully");
			entity.put("Data", department);

		} else {

			Optional<Department> department = departmentRepository.findOneById(departmentDto.getId());
			if (department.get().getId() != null) {

				Department departments = department.get();
//				BeanUtils.copyProperties(departmentDto, departments,getNullPropertyNames(departments));
				departments.setDepartmentName(departmentDto.getDepartmentName());
				departments.setDepartmentDescription(departmentDto.getDepartmentDescription());
				departments.setCreatedDate(departments.getCreatedDate());
				departments.setUpdateddate(LocalDate.now());

				if (departmentDto.getCreatedBy() != null) {
//					Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(departmentDto.getCreatedBy());
//					if (employeeDetails.isPresent()) {
					departments.setCreatedBy(departmentDto.getCreatedBy());
//					}
				} else {
					departments.setCreatedBy(departments.getCreatedBy());
				}
				if (departmentDto.getUpdatedBy() != null) {
//					Optional<EmployeeDetails> employeeDetails = employeeDetailsRepository.findById(departmentDto.getUpdatedBy());
//					if (employeeDetails.isPresent()) {
					departments.setUpdatedBy(departmentDto.getUpdatedBy());
//					}
				} else {
					departments.setUpdatedBy(departments.getUpdatedBy());
				}

				departmentRepository.save(departments);
				entity.put("Status", "200");
				entity.put("Message", "Department Updated Successfully");
				entity.put("Data", departments);

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

	public LinkedHashMap<String, Object> getDepartmentList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<DepartmenListDto> departmentListDtos = new ArrayList<>();

		List<Department> department = departmentRepository.findAll();

		for (Department departments : department) {
			DepartmenListDto departmenListDto = new DepartmenListDto();

			departmenListDto.setDepartmentName(departments.getDepartmentName());
			departmenListDto.setCreatedDate(departments.getCreatedDate());
			Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(departments.getCreatedBy());
			if (employee.isPresent()) {
				departmenListDto.setCreatdBy(employee.get().getName());
			}
			departmentListDtos.add(departmenListDto);

		}
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", departmentListDtos);

		return entity;
	}

	public LinkedHashMap<String, Object> getUpdatedDepartmentList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<DepartmentUpdateListDto> departmenUpdatetListDtos = new ArrayList<>();

		List<Department> department = departmentRepository.findAll();

		for (Department departments : department) {
			DepartmentUpdateListDto departmentUpdateListDto = new DepartmentUpdateListDto();

			departmentUpdateListDto.setUpdateddate(departments.getUpdateddate());
			Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(departments.getUpdatedBy());
			if (employee.isPresent()) {
				departmentUpdateListDto.setUpdatedBy(employee.get().getName());
			}
			departmenUpdatetListDtos.add(departmentUpdateListDto);

		}
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", departmenUpdatetListDtos);

		return entity;
	}

	public LinkedHashMap<String, Object> getDepartmentById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<Department> department = departmentRepository.findById(id);
		if (department.isPresent()) {
			Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(department.get().getCreatedBy());
			if (employee.isPresent()) {
				department.get().setCreatedBy(employee.get().getName());
			}
			if (department.get().getUpdatedBy() != null) {
				Optional<EmployeeDetails> employeeOp = employeeDetailsRepository
						.findById(department.get().getUpdatedBy());
				if (employeeOp.isPresent()) {
					department.get().setUpdatedBy(employeeOp.get().getName());
				}
			}

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", department);
		} else {
			entity.put("Status", "405");
			entity.put("Message", "Data is not present");

		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllDepartmentsList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<DepartmentDto> departmentDtos = new ArrayList<>();

		List<Department> department = departmentRepository.findAll();

		if (department.size() > 0) {
			for (Department dept : department) {
				Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(dept.getCreatedBy());
				if (employee.isPresent()) {
					dept.setCreatedBy(employee.get().getName());
				}
				if (dept.getUpdatedBy() != null) {
					Optional<EmployeeDetails> employeeOp = employeeDetailsRepository.findById(dept.getUpdatedBy());
					if (employeeOp.isPresent()) {
						dept.setUpdatedBy(employeeOp.get().getName());
					}
				}
			}

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", department);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getDepartmentFilter(DepartmentDto departmentDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Query query = new Query();

		if (departmentDto.getDepartmentName() != null && !departmentDto.getDepartmentName().equals("")) {
			query.addCriteria(Criteria.where("departmentName").is(departmentDto.getDepartmentName()));
		}

		if (departmentDto.getCreatedBy() != null && !departmentDto.getCreatedBy().equals("")) {
			query.addCriteria(Criteria.where("createdBy").is(departmentDto.getCreatedBy()));
		}

		List<Department> departments = mongoTemplate.find(query, Department.class);
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", departments);

		return entity;
	}
}
