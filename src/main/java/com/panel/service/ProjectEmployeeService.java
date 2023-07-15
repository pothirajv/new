package com.panel.service;

import java.util.List;

import com.panel.dto.ProjectEmployeeDto;
import com.panel.dto.AssignEmployeesToProjectResponseDto;
import com.panel.model.ProjectEmployee;

public interface ProjectEmployeeService {
	
	
	public AssignEmployeesToProjectResponseDto SaveAndUpdateAssinEmpToPro(ProjectEmployeeDto assignEmployeesToProjectDto);

	public List<ProjectEmployee> getAllEmpToProj(ProjectEmployeeDto assignEmployeesToProjectDto);
	
}
