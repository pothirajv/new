package com.panel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.Gender;



public interface GenderRepository extends MongoRepository<Gender, String>{


	

}
