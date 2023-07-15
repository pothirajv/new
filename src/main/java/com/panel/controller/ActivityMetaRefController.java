package com.panel.controller;

import java.util.LinkedHashMap;

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

import com.panel.dto.ActivityMetaRefDto;
import com.panel.service.ActivityMetaRefService;
@RestController
@RequestMapping("/activitymeta")
@CrossOrigin("*")
public class ActivityMetaRefController {
	
	@Autowired
	ActivityMetaRefService activityMetaRefService;

	@PostMapping("/save")
	public ResponseEntity<Object> saveOrUpdateActivityMeta(@RequestBody ActivityMetaRefDto activityMetaRefDto) {
		return new ResponseEntity<Object>(activityMetaRefService.saveOrUpdateActivityMeta(activityMetaRefDto), HttpStatus.OK);
	}
	
	@GetMapping("/id")
	public ResponseEntity<Object> getActivityMetaById(@RequestParam String id) {
		return new ResponseEntity<Object>(activityMetaRefService.getActivityMetaById(id), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<Object> getAllActivityMetaRef(@RequestParam(required = false) String id) {
		return new ResponseEntity<Object>(activityMetaRefService.getAllActivityMetaRef(id), HttpStatus.OK);
	}
	
	@GetMapping("/parent")
	public ResponseEntity<Object> getActivityMetaByParentActivity(String id) {
		return new ResponseEntity<Object>(activityMetaRefService.getActivityMetaByParentActivity(id), HttpStatus.OK);
	}
}
