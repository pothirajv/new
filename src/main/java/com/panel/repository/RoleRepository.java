package com.panel.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.Role;


public interface RoleRepository extends MongoRepository<Role, String>{

	

}
