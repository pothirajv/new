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

import com.panel.dto.BusinessUnitDto;
import com.panel.dto.MasterDepartmentDto;
import com.panel.service.BusinessUnitService;
@RestController
@RequestMapping("/businessUnit") 
@CrossOrigin("*")
public class BusinessUnitController {
	
	@Autowired
	BusinessUnitService businessUnitService;
	
	@PostMapping("/saveUpdateBusinessUnit")
	public ResponseEntity<Object> saveAndUpdateBusinessUnit(@RequestBody  BusinessUnitDto businessUnitDto)  {
		return new ResponseEntity<>(businessUnitService.saveAndUpdateBusinessUnit(businessUnitDto),HttpStatus.OK);
	}

	@GetMapping("/getAllBusinessUnits")
	public ResponseEntity<Object> getAllBusinessUnits()  {
		return new ResponseEntity<>(businessUnitService.getAllBusinessUnits(),HttpStatus.OK);
	}

	@GetMapping("/businessUnitById")
	public ResponseEntity<Object> getBusinessUnitById(@RequestParam String id)  {
		return new ResponseEntity<>(businessUnitService.getBusinessUnitById(id),HttpStatus.OK);
	}
	
	@GetMapping("/businessUnitFilter")
	public ResponseEntity<Object> getDepartmentFilter(@RequestBody Optional<BusinessUnitDto> businessUnitDtoOp) {
		BusinessUnitDto businessUnitDto;
		if(businessUnitDtoOp.isPresent()) {
			businessUnitDto = businessUnitDtoOp.get();
		} else {
			businessUnitDto = new BusinessUnitDto();
		}
		return new ResponseEntity<>(businessUnitService.getBusinessUnitFilter(businessUnitDto),HttpStatus.OK);
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> getBusinessUnitByHead(String id) {
		return new ResponseEntity<>(businessUnitService.getBusinessUnitByHead(id),HttpStatus.OK);
	}
}
