package com.panel.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.LeaveTransactionHistory;

public interface LeaveTransactionHistoryRepository extends MongoRepository<LeaveTransactionHistory, String>{

	List<LeaveTransactionHistory> findByEmployeeId(String id);

}
