package com.panel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panel.dto.BatchProcessDto;
import com.panel.service.BatchProcessService;

@RestController
@RequestMapping("/batchProcess")
@CrossOrigin("*")
public class BatchProcessController {
	
	@Autowired
	BatchProcessService batchProcessService;
	
	@PostMapping("/leave")
	public ResponseEntity<Object> assignLeaveToEmployees(@RequestBody BatchProcessDto batchProcessDto) {
		return new ResponseEntity<Object>(batchProcessService.assignLeaveToEmployees(batchProcessDto), HttpStatus.OK);
	}

}
