package com.panel.controller;

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

import com.panel.dto.DepartmentDto;
import com.panel.service.DepartmentService;

@RestController
@RequestMapping("/Department")
@CrossOrigin("*")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	
	@PostMapping("/saveUpdateDepartment")
	public ResponseEntity<Object> saveOrUpdateDepartment(@RequestBody  DepartmentDto departmentDto)  {
		return new ResponseEntity<>(departmentService.saveOrUpdateDepartment(departmentDto),HttpStatus.OK);
	}
	
	@GetMapping("/departmentList")
	public ResponseEntity<Object> getDepartmentList()  {
		return new ResponseEntity<>(departmentService.getDepartmentList(),HttpStatus.OK);
	}
	
	@GetMapping("/UpdateddepartmentList")
	public ResponseEntity<Object> getUpdatedDepartmentList()  {
		return new ResponseEntity<>(departmentService.getUpdatedDepartmentList(),HttpStatus.OK);
	}

	@GetMapping("/departmentById")
	public ResponseEntity<Object> getDepartmentById(@RequestParam String id)  {
		return new ResponseEntity<>(departmentService.getDepartmentById(id),HttpStatus.OK);
	}

	@GetMapping("/getAllDepartmentsList")
	public ResponseEntity<Object> getAllDepartmentsList()  {
		return new ResponseEntity<>(departmentService.getAllDepartmentsList(),HttpStatus.OK);
	}
	
	@GetMapping("/departmentFilter")
	public ResponseEntity<Object> getDepartmentFilter(@RequestBody Optional<DepartmentDto> departmentDtoOp) {
		DepartmentDto departmentDto;
		if(departmentDtoOp.isPresent()) {
			departmentDto = departmentDtoOp.get();
		} else {
			departmentDto = new DepartmentDto();
		}
		return new ResponseEntity<>(departmentService.getDepartmentFilter(departmentDto),HttpStatus.OK);
	}

}
