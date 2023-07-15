package com.panel.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.EmployeeStatus;



public interface EmployeeStatusRepository extends MongoRepository<EmployeeStatus, String>{

	EmployeeStatus findOneById(EmployeeStatus employeeStatus);

}
