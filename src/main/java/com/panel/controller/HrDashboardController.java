package com.panel.controller;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panel.service.HrDashboardService;

@RestController
@RequestMapping("/HrDashboard")
@CrossOrigin("*")
public class HrDashboardController {
	
	@Autowired
	HrDashboardService hrDashboardService;
	
	@GetMapping("/employee")
	public ResponseEntity<Object> getAllEmployeeCount(){
		return new ResponseEntity<>(hrDashboardService.getAllEmployeeCount(),HttpStatus.OK);
	}
	
	@GetMapping("/client")
	public ResponseEntity<Object> getAllClientCount(){
		return new ResponseEntity<>(hrDashboardService.getAllClientCount(),HttpStatus.OK);
	}
	
	@GetMapping("/project")
	public ResponseEntity<Object> getAllProjectCount(){
		return new ResponseEntity<>(hrDashboardService.getAllProjectCount(),HttpStatus.OK);
	}
	
	@GetMapping("/leave")
	public ResponseEntity<Object> getTodayLeaves() {
		return new ResponseEntity<>(hrDashboardService.getTodayLeaves(),HttpStatus.OK);
	}

}
