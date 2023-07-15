package com.panel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.MasterDepartment;

public interface MasterDepartmentRepository extends MongoRepository<MasterDepartment, String> {

	Optional<MasterDepartment> findOneById(String id);

	Optional<MasterDepartment> findByDepartmentCode(String departmentCode);

	List<MasterDepartment> findByDepartmentHead(String id);

	List<MasterDepartment> findByDepartmentHeadAndBusinessUnitId(String id, String unitId);

	List<MasterDepartment> findByBusinessUnitId(String id);

	

}
