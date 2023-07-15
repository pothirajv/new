package com.panel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.ReportingPerson;

public interface ReportingPersonRepository extends MongoRepository<ReportingPerson, String>{

}
