package com.panel.controller;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.panel.service.LeaveTransactionHistoryService;

@RestController
@RequestMapping("/leaveHistory")
@CrossOrigin("*")
public class LeaveTransactionHistoryController {

	@Autowired
	LeaveTransactionHistoryService leaveTransactionHistoryService;

	@GetMapping("/employee")
	public ResponseEntity<Object> getLeaveTransactionHistoryByEmployee(
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
			@RequestParam String id) {
		return new ResponseEntity<Object>(leaveTransactionHistoryService.getLeaveTransactionHistoryByEmployee(fromDate, toDate, id), HttpStatus.OK);
	}

}
