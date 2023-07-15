package com.panel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.panel.dto.ParentActivityDto;
import com.panel.service.ParentActivityService;

@RestController
@RequestMapping("/parentActivity")
@CrossOrigin("*")
public class ParentActivityController {
	
	@Autowired
	ParentActivityService parentActivityService;

	@PostMapping("/save")
	public ResponseEntity<Object> saveParentActivity(@RequestBody ParentActivityDto parentActivityDto) {
		return new ResponseEntity<>(parentActivityService.saveParentActivity(parentActivityDto), HttpStatus.OK);
	}
	
	@GetMapping("/id")
	public ResponseEntity<Object> getParentActivityById(@RequestParam String id) {
		return new ResponseEntity<>(parentActivityService.getParentActivityById(id), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<Object> getAllParentActivity() {
		return new ResponseEntity<>(parentActivityService.getAllParentActivity(), HttpStatus.OK);
	}
}
