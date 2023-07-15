package com.panel.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.FinancialYear;


public interface FinancialYearRepository extends MongoRepository<FinancialYear, String>{

	

}
