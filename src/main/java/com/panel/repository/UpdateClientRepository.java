package com.panel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.UpdateClient;

public interface UpdateClientRepository extends MongoRepository<UpdateClient, String>{

}
