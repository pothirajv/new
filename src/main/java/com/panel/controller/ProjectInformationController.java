package com.panel.controller;

import java.util.LinkedHashMap;
import java.util.List;
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

import com.panel.dto.ProjectAssignDto;
import com.panel.dto.ProjectEmployeeDto;
import com.panel.dto.ProjectInformationDto;
import com.panel.dto.ProjectInformationFilterDto;
import com.panel.dto.ProjectInformationResponseDto;
import com.panel.model.ProjectEmployee;
import com.panel.model.ProjectInformation;
import com.panel.service.ProjectInformationService;

@RestController
@RequestMapping("/projectinfo")
@CrossOrigin("*")
public class ProjectInformationController {

	@Autowired
	ProjectInformationService projectInformationservice;

	// SaveandUpdate projectinformation

	@PostMapping("/hr/saveupdate")
	public ResponseEntity<Object> SaveAndUpdateProjectinfo(@RequestBody ProjectInformationDto projectinformationDto) {

		return new ResponseEntity<>(projectInformationservice.SaveAndUpdateProjectinfo(projectinformationDto),
				HttpStatus.OK);
	}

	/*
	 * get the list of project by projectname
	 * 
	 * @PostMapping("/findbyname") public List<ProjectInformation>
	 * findByProjectname(@RequestParam String projectName) {
	 * 
	 * return projectInformationservice.findByProjectName(projectName); }
	 * 
	 * @GetMapping("/filter") public List<ProjectInformation>
	 * getAllProjectInformationDetails( Optional<ProjectInformationFilterDto>
	 * projectInformationDtoOp) {
	 * 
	 * ProjectInformationFilterDto projectInformationFilterDto;
	 * 
	 * if (projectInformationDtoOp.isPresent()) {
	 * 
	 * projectInformationFilterDto = projectInformationDtoOp.get();
	 * 
	 * } else {
	 * 
	 * projectInformationFilterDto = new ProjectInformationFilterDto(); }
	 * 
	 * return projectInformationservice.getAllProjectInformationDetails(
	 * projectInformationFilterDto);
	 * 
	 * }
	 */

	@GetMapping("/hr/getall")
	public ResponseEntity<Object> getallProjectDetails(@RequestParam String id) {

		return new ResponseEntity<>(projectInformationservice.getallProjectDetails(id), HttpStatus.OK);

	}

	@GetMapping("/hr/getprojectbyid")
	public ResponseEntity<Object> getProjectById(@RequestParam String id) {

		return new ResponseEntity<>(projectInformationservice.getProjectById(id), HttpStatus.OK);
	}

	@PostMapping("/hr/assignemp")
	public ResponseEntity<Object> assignEmployeesToProject(@RequestBody ProjectAssignDto projectAssignDto) {

		return new ResponseEntity<>(projectInformationservice.assignEmployeesToProject(projectAssignDto),
				HttpStatus.OK);
	}

	@GetMapping("/hr/updatelist")
	public ResponseEntity<Object> getProjectEmployeeUpdateList(@RequestParam String id) {

		return new ResponseEntity<>(projectInformationservice.getProjectEmployeeUpdateList(id), HttpStatus.OK);

	}

	@GetMapping("/getbyEmp")
	public ResponseEntity<Object> getProjectByEmployee(@RequestParam String id) {

		return new ResponseEntity<>(projectInformationservice.getProjectByEmployee(id), HttpStatus.OK);
	}

	@GetMapping("/hr/updatedetails")
	public ResponseEntity<Object> getProjectUpdateDetails(@RequestParam String id) {
		return new ResponseEntity<>(projectInformationservice.getProjectUpdateDetails(id), HttpStatus.OK);
	}

	@GetMapping("/hr/names")
	public ResponseEntity<Object> getAllProjectNames() {
		return new ResponseEntity<>(projectInformationservice.getAllProjectNames(), HttpStatus.OK);
	}

	@GetMapping("/unmappedEmp")
	public ResponseEntity<Object> getUnAssignedEmployeesForProject(@RequestParam String empId, @RequestParam String id) {
		return new ResponseEntity<>(projectInformationservice.getUnAssignedEmployeesForProject(empId, id),
				HttpStatus.OK);
	}

	@GetMapping("/unitHead")
	public ResponseEntity<Object> getProjectByBusinessUnit(@RequestParam String id) {
		return new ResponseEntity<>(projectInformationservice.getProjectByBusinessUnit(id), HttpStatus.OK);
	}

	@GetMapping("/deptHead")
	public ResponseEntity<Object> getProjectByDepartment(@RequestParam String id) {
		return new ResponseEntity<>(projectInformationservice.getProjectByDepartment(id), HttpStatus.OK);
	}

	@GetMapping("/manager")
	public ResponseEntity<Object> getProjectByProjectManager(@RequestParam String id) {
		return new ResponseEntity<>(projectInformationservice.getProjectByProjectManager(id), HttpStatus.OK);
	}

	@GetMapping("/removeEmp")
	public ResponseEntity<Object> removeResourceFromProject(@RequestParam String empId, @RequestParam String projectId) {
		return new ResponseEntity<>(projectInformationservice.removeResourceFromProject(empId, projectId), HttpStatus.OK);
	}
}
