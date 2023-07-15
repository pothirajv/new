	package com.panel.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.EmployeeDetails;
import com.panel.model.Role;



public interface EmployeeDetailsRepository extends MongoRepository<EmployeeDetails, String>{
	

	//List<EmployeeDetails> findByDepartment(String department);

//	List<EmployeeDetails> findByEmployeeStatus(String employeeStatus);

	//List<EmployeeDetails> findByEmployeeRole(String employeeRole);

	//EmployeeDetails findByName(String name);

//	EmployeeDetails findOneById(long id);
	
	//List<EmployeeDetails> findAllEmployeeList();

	
	Optional<EmployeeDetails> findOneById(String id);

	Optional<EmployeeDetails> findByEmployeeCode(String employeeCode);

	List<EmployeeDetails> findByNameOrEmailId(String Name, String emailId);

	List<EmployeeDetails> findByReportingManager(String employeeCode);
	
	List<EmployeeDetails> findByRoleIn(List<String> role );

	EmployeeDetails findByEmailId(String emailId);

	EmployeeDetails findByEmailIdIgnoreCase(String emailId);

	List<EmployeeDetails> findByNameOrEmployeeCode(String employeeName, String employeeName2);

	List<EmployeeDetails> findByBusinessUnitId(String bu);

	List<EmployeeDetails> findByMasterDepartmentId(String id);

	List<EmployeeDetails> findByBusinessUnitIdAndMasterDepartmentId(String businessUnit, String department);


	//public EmployeeDetails findByEmailId(String emailId);
	
//	List<EmployeeDetails> findByEmployeeCode(String employeeCode);
	
	
	//EmployeeDetails findOneById(String employeeId);

	//EmployeeDetails findByName(String employeeName);

	//EmployeeDetails fineOneById(String id);
	
}
