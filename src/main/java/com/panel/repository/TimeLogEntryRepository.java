package com.panel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.TimeLogEntry;

public interface TimeLogEntryRepository extends MongoRepository<TimeLogEntry, String> {

	
	//List<TimeLogEntry> findByDateBetweenAndEmployee(LocalDate fromDate, LocalDate todate, String empCode);

	List<TimeLogEntry> findByDateBetween(LocalDate fromDate, LocalDate todate);

	List<TimeLogEntry> findByDateBetweenAndEmployeeId(LocalDate fromDate, LocalDate todate, String empCode);

	List<TimeLogEntry> findByEmployeeAndStatus(String empCode, boolean b);

//	List<TimeLogEntry> findByDateBetweenOrderByEmployee(LocalDate minusDays, LocalDate plusDays);

}
