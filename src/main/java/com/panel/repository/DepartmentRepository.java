package com.panel.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.Department;



public interface DepartmentRepository extends MongoRepository<Department, String>{

	Optional<Department> findOneById(String id);

	

}
