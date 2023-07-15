package com.panel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.UserModel;


public interface UserRepository extends MongoRepository<UserModel,String> {

	UserModel findByEmailIgnoreCase(String email);

	

}
