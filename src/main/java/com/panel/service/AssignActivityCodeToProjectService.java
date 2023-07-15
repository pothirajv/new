package com.panel.service;


import java.util.List;

import com.panel.dto.AssignActivityCodeToProjectDto;
import com.panel.dto.AssignActivityCodeToProjectResponseDto;
import com.panel.model.AssignActivityCodeToProject;

public interface AssignActivityCodeToProjectService {

	public AssignActivityCodeToProjectResponseDto SaveAndUpdateAssActcodeToProject(AssignActivityCodeToProjectDto assignActivityCodeToProjectDto);
	
	public List<AssignActivityCodeToProject> getAllAssignActtopro(AssignActivityCodeToProjectDto assignActivityCodeToProjectDto);
	
}
