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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.panel.dto.AssignedResourcesDesigDto;
import com.panel.dto.DepartmentDto;
import com.panel.dto.Designationsdto;
import com.panel.service.DepartmentService;
import com.panel.service.DesignationsService;

@RestController
@RequestMapping("/Designations")
@CrossOrigin("*")
public class DesignationController {
	
	@Autowired
	DesignationsService designationsService;
	
	@PostMapping("/saveUpdateDesignation")
	public ResponseEntity<Object> saveOrUpdateDesignations(@RequestBody  Designationsdto designationsdto)  {
		return new ResponseEntity<>(designationsService.saveOrUpdateDesignations(designationsdto),HttpStatus.OK);
	}
	
	@GetMapping("/designationList")
	public ResponseEntity<Object> getDesignationsList()  {
		return new ResponseEntity<>(designationsService.getDesignationsList(),HttpStatus.OK);
	}
	
	@GetMapping("/UpdateddesignationList")
	public ResponseEntity<Object> getUpdatedDesignationList()  {
		return new ResponseEntity<>(designationsService.getUpdatedDesignationList(),HttpStatus.OK);
	}
	
	@GetMapping("/designationById")
	public ResponseEntity<Object> getDesignationById(@RequestParam String id)  {
		return new ResponseEntity<>(designationsService.getDesignationById(id),HttpStatus.OK);
	}

	@GetMapping("/getAllDesignationsList")
	public ResponseEntity<Object> getAllDesignationsList()  {
		return new ResponseEntity<>(designationsService.getAllDesignationsList(),HttpStatus.OK);
	}

	@GetMapping("/designationsFilter")
	public ResponseEntity<Object> getDepartmentFilter(@RequestBody Optional<Designationsdto> designationsDtoOp) {
		Designationsdto designationsDto;
		if(designationsDtoOp.isPresent()) {
			designationsDto = designationsDtoOp.get();
		} else {
			designationsDto = new Designationsdto();
		}
		return new ResponseEntity<>(designationsService.getDesignationsFilter(designationsDto),HttpStatus.OK);
	}

	@PostMapping("/assignResourceDesig")
	public ResponseEntity<Object> saveOrUpdateAssignedResources(@RequestBody AssignedResourcesDesigDto assignedResourcesDesigDto){
		return new ResponseEntity<>(designationsService.saveOrUpdateAssignedResources(assignedResourcesDesigDto),HttpStatus.OK);
	}
	
	@GetMapping("/allAssignResourceDesig")
	public ResponseEntity<Object> getAllAssignedResourcesList(){
		return new ResponseEntity<>(designationsService.getAllAssignedResourcesList(),HttpStatus.OK);
	}
	@GetMapping("/updateDesig")
	public ResponseEntity<Object> getAllUpdateDesignationsList(@RequestParam String id){
		return new ResponseEntity<>(designationsService.getAllUpdateDesignationsList(id),HttpStatus.OK);
	}
	@GetMapping("/updateDesigById")
	public ResponseEntity<Object> getUpdateDesignationsById(@RequestParam String id){
		return new ResponseEntity<>(designationsService.getUpdateDesignationsById(id),HttpStatus.OK);
	}
	
	@GetMapping("/resourceByDesig")
	public ResponseEntity<Object> getAssignedResourcesList(@RequestParam String id){
		return new ResponseEntity<>(designationsService.getAssignedResourcesList(id),HttpStatus.OK);
	}

}
