package com.panel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.panel.model.Employee;



public interface EmployeeRepository extends MongoRepository<Employee, String> {

	List<Employee> findByPhone(String phone);

	List<Employee> findByEmail(String email);

	List<Employee> findByEmployeeName(String employeeName);

	//List<Object[]> findByEmployeeName(String employeeName);

	//List<Object[]> findByEmail(String email);

	//List<Object[]> findByPhone(String phone);

//	@Query(value="select * from employee e where e.employee_name=:employeeName or e.email=:employeeName",nativeQuery=true)
	public List<Employee> findByEmployeeNameOrEmail(String employeeName,String email);

	Optional<Employee> findById(String id);

	Optional<Employee> findOneById(String addedBy);

	
	
}
