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

import com.panel.dto.MasterDepartmentDto;
import com.panel.service.MasterDepartmentService;
@RestController
@RequestMapping("/masterDepartment")
@CrossOrigin("*")
public class MasterDepartmentController {
	
	@Autowired
	MasterDepartmentService masterDepartmentService;
	
	@PostMapping("/saveUpdateMasterDepartment")
	public ResponseEntity<Object> saveAndUpdateMasterDepartment(@RequestBody  MasterDepartmentDto masterDepartmentDto)  {
		return new ResponseEntity<>(masterDepartmentService.saveAndUpdateMasterDepartment(masterDepartmentDto),HttpStatus.OK);
	}

	@GetMapping("/getAllMasterDepartments")
	public ResponseEntity<Object> getAllMasterDepartments()  {
		return new ResponseEntity<>(masterDepartmentService.getAllMasterDepartments(),HttpStatus.OK);
	}

	@GetMapping("/masterDepartmentById")
	public ResponseEntity<Object> getMasterDepartmentById(@RequestParam String id)  {
		return new ResponseEntity<>(masterDepartmentService.getMasterDepartmentById(id),HttpStatus.OK);
	}
	
	@GetMapping("/masterDepartmentFilter")
	public ResponseEntity<Object> getDepartmentFilter(@RequestBody Optional<MasterDepartmentDto> masterDepartmentDtoOp) {
		MasterDepartmentDto masterDepartmentDto;
		if(masterDepartmentDtoOp.isPresent()) {
			masterDepartmentDto = masterDepartmentDtoOp.get();
		} else {
			masterDepartmentDto = new MasterDepartmentDto();
		}
		return new ResponseEntity<>(masterDepartmentService.getMasterDepartmentFilter(masterDepartmentDto),HttpStatus.OK);
	}
	
	@GetMapping("/deptByBU")
	public ResponseEntity<Object> getMasterDepartmentsByBusinessUnit(@RequestParam String id){
		return new ResponseEntity<>(masterDepartmentService.getMasterDepartmentsByBusinessUnit(id),HttpStatus.OK);
	}
	
	@GetMapping("/unitAndDept")
	public ResponseEntity<Object> getMasterDepartmentByBusinessUnitAndDeptHead(@RequestParam String unitId, @RequestParam String id) {
		return new ResponseEntity<>(masterDepartmentService.getMasterDepartmentByBusinessUnitAndDeptHead(unitId, id),HttpStatus.OK);
	}

	@GetMapping("/byUnitHead")
	public ResponseEntity<Object> getMasterDepartmentByBusinessUnitHead(@RequestParam String id) {
		return new ResponseEntity<>(masterDepartmentService.getMasterDepartmentByBusinessUnitHead(id),HttpStatus.OK);
	}
	
	@GetMapping("/project")
	public ResponseEntity<Object> getMasterDepartmentAndProjects(@RequestParam String id) {
		return new ResponseEntity<>(masterDepartmentService.getMasterDepartmentAndProjects(id),HttpStatus.OK);
	}
}
