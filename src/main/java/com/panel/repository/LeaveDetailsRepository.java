package com.panel.repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.panel.model.LeaveDetails;


public interface LeaveDetailsRepository extends MongoRepository<LeaveDetails, String>{

	Optional<LeaveDetails> findOneById(String id);

	List<LeaveDetails> findByFromDate(LocalDate now, LocalDate plusDays);

	List<LeaveDetails> findByFromDateBetween(LocalDate now, LocalDate plusDays);

	List<LeaveDetails> findByToDateBetween(LocalDate now, LocalDate plusDays);

	List<LeaveDetails> findByEmpCodeAndStatus(String employeeCode, boolean b);

//	LeaveDetails findByEmployeeId(String employeeId);

//	LeaveDetails findByEmployeeIdAndDateBetween(String employeeId, LocalDate fromDate, String toDate);


//	List<LeaveDetails> findByFromDateBetween(LocalDate local, LocalDate localDate);

//	LeaveDetails findByEmployeeNameAndFromDateBetween(String name, LocalDate local, String toDate);

//	List<LeaveDetails> findByEmployeeName(String employeeName);

//	List<LeaveDetails> findByDepartment(String department);

//	List<LeaveDetails> findByRemainingLeave(int remainingLeave);


	

}
