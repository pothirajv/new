package com.panel.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.BatchProcess;

public interface BatchProcessRepository extends MongoRepository<BatchProcess, String>{

	BatchProcess findByMonth(String month);

	List<BatchProcess> findByBatchType(String string);

}
