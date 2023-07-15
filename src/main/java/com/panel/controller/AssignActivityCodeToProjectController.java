package com.panel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.panel.dto.AssignActivityCodeToProjectDto;
import com.panel.dto.AssignActivityCodeToProjectResponseDto;
import com.panel.model.AssignActivityCodeToProject;
import com.panel.service.AssignActivityCodeToProjectService;

@RestController
@RequestMapping("/assignactive")
@CrossOrigin("*")
public class AssignActivityCodeToProjectController {

	@Autowired
	AssignActivityCodeToProjectService assignActivityCodeToProjectService;

	@PostMapping("/saveupdate")
	public AssignActivityCodeToProjectResponseDto SaveAndUpdateAssActcodeToProject(
			AssignActivityCodeToProjectDto assignActivityCodeToProjectDto)

	{

		return assignActivityCodeToProjectService.SaveAndUpdateAssActcodeToProject(assignActivityCodeToProjectDto);
	}

	@GetMapping("/getall")
	public List<AssignActivityCodeToProject> getAllAssignActtopro(
			AssignActivityCodeToProjectDto assignActivityCodeToProjectDto) 
	
	{

		return assignActivityCodeToProjectService.getAllAssignActtopro(assignActivityCodeToProjectDto);

	}

}