package com.panel.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.panel.dto.TimeLogEntryDto;
import com.panel.service.TimeLogEntryServices;

@RestController
@RequestMapping("/timelog")
@CrossOrigin("*")
public class TimeLogEntryController {

	@Autowired
	TimeLogEntryServices timeLogEntryServices;

	@PostMapping("/savetimelog")
	public LinkedHashMap<String, Object> SaveAndUpdate(@RequestBody TimeLogEntryDto timeLogEntryDto) {

		return timeLogEntryServices.SaveAndUpdate(timeLogEntryDto);
	}

	@GetMapping("/getall")
	public LinkedHashMap<String, Object> getAllTimeLogEntries() {
		// TODO Auto-generated method stub

		return timeLogEntryServices.getAllTimeLogEntries();
	}

	@GetMapping("/getbyid")
	public LinkedHashMap<String, Object> getTimeLogEntryById(@RequestParam String id) {

		return timeLogEntryServices.getTimeLogEntryById(id);
	}

	@GetMapping("/employee")
	public ResponseEntity<Object> getTimeLogEntryByEmployee(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate, @RequestParam String empCode) {
		return new ResponseEntity<>(timeLogEntryServices.getTimeLogEntryByEmployee(fromDate, toDate, empCode),
				HttpStatus.OK);
	}

	@GetMapping("/deletebyid")
	public LinkedHashMap<String, Object> deleteTimeLogEntryById(@RequestParam String id) {

		return timeLogEntryServices.deleteTimeLogEntryById(id);
	}

	@GetMapping("/emptimelog")
	public LinkedHashMap<String, Object> getAllEmployeeTimelog(@RequestParam String id) {

		return timeLogEntryServices.getAllEmployeeTimelog(id);

	}

	@GetMapping("/month")
	public ResponseEntity<Object> getMonthlyTimeLogEntries(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
		return new ResponseEntity<>(timeLogEntryServices.getMonthlyTimeLogEntries(fromDate, toDate), HttpStatus.OK);
	}
	
	@GetMapping("/status")
	public ResponseEntity<Object> getAllTimeLogStatus() {
		return new ResponseEntity<>(timeLogEntryServices.getAllTimeLogStatus(), HttpStatus.OK);
	}

	@GetMapping("/pdf")
	public ResponseEntity<Object> getTimeLogEntriesByProjectAndMonth(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate, @RequestParam String projectId) {
		byte[] content = timeLogEntryServices.getTimeLogEntriesByProjectAndMonth(fromDate, toDate, projectId);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    String month = fromDate.getMonth().toString();
	    String filename = month+"_Log Report.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return new ResponseEntity<>(content, headers, HttpStatus.OK);
	}
}
