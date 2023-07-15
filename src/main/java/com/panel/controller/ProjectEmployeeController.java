package com.panel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panel.dto.ProjectEmployeeDto;
import com.panel.dto.AssignEmployeesToProjectResponseDto;
import com.panel.model.ProjectEmployee;
import com.panel.service.ProjectEmployeeService;

@RestController
@RequestMapping("/assignemptopro")
@CrossOrigin("*")
public class ProjectEmployeeController {
	
	@Autowired
	
	ProjectEmployeeService	assignEmployeesToProjectService;
	
@PostMapping("/saveupdate")	
public AssignEmployeesToProjectResponseDto SaveAndUpdateAssinEmpToPro(ProjectEmployeeDto assignEmployeesToProjectDto) {

	
		return assignEmployeesToProjectService.SaveAndUpdateAssinEmpToPro(assignEmployeesToProjectDto);
		
}

@GetMapping("/getall")
public List<ProjectEmployee> getAllEmpToProj(ProjectEmployeeDto assignEmployeesToProjectDto) {
	
	
	return assignEmployeesToProjectService.getAllEmpToProj(assignEmployeesToProjectDto);

}

}