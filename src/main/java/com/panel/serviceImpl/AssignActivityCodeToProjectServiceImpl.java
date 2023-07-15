package com.panel.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Service;

import com.panel.dto.AssignActivityCodeToProjectDto;
import com.panel.dto.AssignActivityCodeToProjectResponseDto;
import com.panel.model.AssignActivityCodeToProject;
import com.panel.repository.AssignActivityCodeToProjectRepository;
import com.panel.service.AssignActivityCodeToProjectService;

@Service
public class AssignActivityCodeToProjectServiceImpl implements AssignActivityCodeToProjectService {
	
	@Autowired
	
	AssignActivityCodeToProjectRepository assignActivityCodeToProjectRepository;
	
	@Autowired
	
	MongoTemplate mongoTemplate;

	@Override
	public AssignActivityCodeToProjectResponseDto SaveAndUpdateAssActcodeToProject(
			AssignActivityCodeToProjectDto assignActivityCodeToProjectDto) {
		// TODO Auto-generated method stub
		
		AssignActivityCodeToProjectResponseDto response = new AssignActivityCodeToProjectResponseDto();
		if(assignActivityCodeToProjectDto.getId()==null) {
			
			AssignActivityCodeToProject assignActivityCodeToProject1 = new AssignActivityCodeToProject();
			assignActivityCodeToProject1.setActivityCode(assignActivityCodeToProjectDto.getActivityCode());
			assignActivityCodeToProject1.setProjectId(assignActivityCodeToProjectDto.getProjectId());
			assignActivityCodeToProject1.setAuthority(assignActivityCodeToProjectDto.getAuthority());
			assignActivityCodeToProjectRepository.save(assignActivityCodeToProject1);
		}
		
		return response;
	}

	@Override
	public List<AssignActivityCodeToProject> getAllAssignActtopro(
			AssignActivityCodeToProjectDto assignActivityCodeToProjectDto) {
		
		
		List<AssignActivityCodeToProject> assignActivityCodeToProjects = assignActivityCodeToProjectRepository.findAll(); 
		
		return assignActivityCodeToProjects;
	}

}
