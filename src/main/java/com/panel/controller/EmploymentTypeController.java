package com.panel.controller;

import java.util.LinkedHashMap;
import java.util.Optional;

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

import com.panel.dto.AssignedResourcesEmpTypeDto;
import com.panel.dto.DepartmentDto;
import com.panel.dto.Designationsdto;
import com.panel.dto.EmploymentTypesDto;
import com.panel.service.DepartmentService;
import com.panel.service.EmploymentTypeService;

@RestController
@RequestMapping("/EmploymentTypes")
@CrossOrigin("*")
public class EmploymentTypeController {
	
	@Autowired
	EmploymentTypeService employmentTypeService;
	
	@PostMapping("/saveUpdateEmploymentType")
	public ResponseEntity<Object> saveOrUpdateEmploymentTypes(@RequestBody EmploymentTypesDto employmentTypesDto)  {
		return new ResponseEntity<>(employmentTypeService.saveOrUpdateEmploymentTypes(employmentTypesDto),HttpStatus.OK);
	}
	
	@GetMapping("/emplymentTypeList")
	public ResponseEntity<Object> getEmploymenttypesList()  {
		return new ResponseEntity<>(employmentTypeService.getEmploymenttypesList(),HttpStatus.OK);
	}
	
	@GetMapping("/UpdatedEmploymentTypeList")
	public ResponseEntity<Object> getUpdateEmploymenttypesList()  {
		return new ResponseEntity<>(employmentTypeService.getUpdateEmploymenttypesList(),HttpStatus.OK);
	}
	
	@GetMapping("/employmentTypesById")
	public ResponseEntity<Object> getEmploymentTypesById(@RequestParam String id)  {
		return new ResponseEntity<>(employmentTypeService.getEmploymentTypesById(id),HttpStatus.OK);
	}

	@GetMapping("/getAllEmploymentTypesList")
	public ResponseEntity<Object> getAllEmploymentTypesList()  {
		return new ResponseEntity<>(employmentTypeService.getAllEmploymentTypesList(),HttpStatus.OK);
	}
	
	@GetMapping("/employmentTypesFilter")
	public ResponseEntity<Object> getEmploymentTypesFilter(@RequestBody Optional<EmploymentTypesDto> employmentTypesDtoOp) {
		EmploymentTypesDto employmentTypesDto;
		if(employmentTypesDtoOp.isPresent()) {
			employmentTypesDto = employmentTypesDtoOp.get();
		} else {
			employmentTypesDto = new EmploymentTypesDto();
		}
		return new ResponseEntity<>(employmentTypeService.getEmploymentTypesFilter(employmentTypesDto),HttpStatus.OK);
	}

	@PostMapping("/assignResource")
	public ResponseEntity<Object> saveOrUpdateAssignedResources(@RequestBody AssignedResourcesEmpTypeDto assignedResourcesDto){
		return new ResponseEntity<>(employmentTypeService.saveOrUpdateAssignedResources(assignedResourcesDto),HttpStatus.OK);
	}
	
	@GetMapping("/allAssignResources")
	public ResponseEntity<Object> getAllAssignedResourcesList(){
		return new ResponseEntity<>(employmentTypeService.getAllAssignedResourcesList(),HttpStatus.OK);
	}

	@GetMapping("/allUpdate")
	public ResponseEntity<Object> getAllUpdateEmploymentTypesList(@RequestParam String id){
		return new ResponseEntity<>(employmentTypeService.getAllUpdateEmploymentTypesList(id),HttpStatus.OK);
	}
	@GetMapping("/updateById")
	public ResponseEntity<Object> getUpdateEmploymentTypesById(@RequestParam String id){
		return new ResponseEntity<>(employmentTypeService.getUpdateEmploymentTypesById(id),HttpStatus.OK);
	}
	
	@GetMapping("/allAssignByET")
	public ResponseEntity<Object> getAssignedResourcesList(@RequestParam String id){
		return new ResponseEntity<>(employmentTypeService.getAssignedResourcesList(id),HttpStatus.OK);
	}
}
