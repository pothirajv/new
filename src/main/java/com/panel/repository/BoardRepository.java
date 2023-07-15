package com.panel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.Board;



public interface BoardRepository extends MongoRepository<Board, String>{


	

}
