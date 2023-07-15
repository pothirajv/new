package com.panel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.BusinessUnit;

public interface BusinessUnitRepository extends MongoRepository<BusinessUnit, String> {

	Optional<BusinessUnit> findOneById(String id);

	Optional<BusinessUnit> findByBusinessUnitCode(String businessUnitCode);

	List<BusinessUnit> findByBusinessUnitHead(String id);

}
