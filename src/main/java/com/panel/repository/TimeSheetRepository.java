package com.panel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.panel.model.TimeSheet;

public interface TimeSheetRepository extends MongoRepository<TimeSheet, String> {

	@Query("{'fromDate': {$gte: ?0, $lte:?1 }}")
	List<TimeSheet> findByFromDateBetween(LocalDate minusDays, LocalDate toDate);

	List<TimeSheet> findByToDateBetween(LocalDate fromDate, LocalDate plusDays);

	List<TimeSheet> findByEmpId(String employeeId);
	
	@Query("{'fromDate': {$lte: ?0 }, $and: [{'toDate': {$gte: ?1}}]}")
	List<TimeSheet> findByFromDateAndToDate(LocalDate date, LocalDate date1);

	List<TimeSheet> findByFromDateLessThanEqual(LocalDate date1);

	List<TimeSheet> findByFromDateAndToDateAndEmpId(LocalDate fromDate, LocalDate toDate, String id);

	List<TimeSheet> findByEmpIdAndMonth(String id, String month);

	TimeSheet findByWeek(String week);

}
