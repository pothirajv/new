package com.panel.controller;

import java.util.LinkedHashMap;
import java.util.List;

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

import com.panel.dto.ActivityCodeAssignDto;
import com.panel.dto.ActivityCodeDto;
import com.panel.service.ActivityCodeService;

@RestController
@RequestMapping("/activecode")
@CrossOrigin("*")
public class ActivityCodeController {

	@Autowired

	ActivityCodeService activeCodeService;

	@PostMapping("/saveandupdate")
	public ResponseEntity<Object> SaveAndUpdateActivityCode(@RequestBody ActivityCodeAssignDto activityCodeAssignDto) {

		return new ResponseEntity<>(activeCodeService.SaveAndUpdateActivityCode(activityCodeAssignDto), HttpStatus.OK);
	}

	@GetMapping("/getall")
	public ResponseEntity<Object> getAllActivityCode() {

		return new ResponseEntity<>(activeCodeService.getAllActivityCode(), HttpStatus.OK);

	}

	@GetMapping("/id")
	public LinkedHashMap<String, Object> getActivityCodeById(@RequestParam String id) {

		return activeCodeService.getActivityCodeById(id);

	}

	@GetMapping("/byproject")
	public ResponseEntity<Object> getActivityCodeByProject(@RequestParam String id) {
		return new ResponseEntity<>(activeCodeService.getActivityCodeByProject(id), HttpStatus.OK);
	}
	
	@GetMapping("/name")
	public ResponseEntity<Object> getActivityCodeNameByProject(@RequestParam String id) {
		return new ResponseEntity<>(activeCodeService.getActivityCodeNameByProject(id), HttpStatus.OK);
	}

}
