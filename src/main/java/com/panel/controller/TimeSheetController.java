package com.panel.controller;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.panel.dto.TimeSheetDto;
import com.panel.service.TimeSheetService;

@RestController
@RequestMapping("/timesheet")
@CrossOrigin("*")
public class TimeSheetController {

	@Autowired
	TimeSheetService timeSheetService;

	@PostMapping("/saveupdate")
	public ResponseEntity<Object> SaveAndUpdateTimesheet(@RequestBody TimeSheetDto timeSheetDto) {

		return new ResponseEntity<>(timeSheetService.SaveAndUpdateTimesheet(timeSheetDto), HttpStatus.OK);
	}

	@GetMapping("/getall")
	public ResponseEntity<Object> getAllTimesheet() {

		return new ResponseEntity<>(timeSheetService.getAllTimesheet(), HttpStatus.OK);
	}

	@GetMapping("/getbyid")
	public ResponseEntity<Object> getTimeSheetById(@RequestParam String id) {

		return new ResponseEntity<>(timeSheetService.getTimeSheetById(id), HttpStatus.OK);

	}

	@GetMapping("/employee")
	public ResponseEntity<Object> getAllEmployeeTimesheet(String id) {
		return new ResponseEntity<>(timeSheetService.getAllEmployeeTimesheet(id), HttpStatus.OK);
	}

	@GetMapping("/client")
	public ResponseEntity<Object> getClientByProject(String id) {
		return new ResponseEntity<>(timeSheetService.getClientByProject(id), HttpStatus.OK);
	}

	@GetMapping("/status")
	public ResponseEntity<Object> getAllTimeSheetStatus() {
		return new ResponseEntity<>(timeSheetService.getAllTimeSheetStatus(), HttpStatus.OK);
	}

	@GetMapping("/approval")
	public ResponseEntity<Object> getTimeSheetApproval(String id) {
		return new ResponseEntity<>(timeSheetService.getTimeSheetApproval(id), HttpStatus.OK);
	}

	@GetMapping("/timeStatus")
	public ResponseEntity<Object> getTimeSheetStatus() {
		return new ResponseEntity<>(timeSheetService.getTimeSheetStatus(), HttpStatus.OK);
	}

	@PostMapping("/resubmit")
	public ResponseEntity<Object> reSubmitTimeSheet(@RequestParam String id) {
		return new ResponseEntity<>(timeSheetService.reSubmitTimeSheet(id), HttpStatus.OK);
	}

	@GetMapping("/report")
	public ResponseEntity<Object> getTimeSheetBetweenDatesByEmployee(@RequestParam String id,
			@RequestParam(required = false) String month) {
		return new ResponseEntity<>(timeSheetService.getTimeSheetBetweenDatesByEmployee(id, month), HttpStatus.OK);
	}

	@GetMapping("/pending")
	public ResponseEntity<Object> getTimeSheetPendingApproval(@RequestParam String id) {
		return new ResponseEntity<>(timeSheetService.getTimeSheetPendingApproval(id), HttpStatus.OK);
	}

	@GetMapping("/month")
	public ResponseEntity<Object> getWeeksForMonths(
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
		return new ResponseEntity<>(timeSheetService.getWeeksForMonths(fromDate, toDate), HttpStatus.OK);
	}
}
