package com.panel.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import com.panel.dto.ProjectEmployeeDto;
import com.panel.dto.AssignEmployeesToProjectResponseDto;
import com.panel.model.ProjectEmployee;
import com.panel.repository.ProjectEmployeesRepository;
import com.panel.service.ProjectEmployeeService;

@Service
public class ProjectEmployeeServiceImpl implements ProjectEmployeeService {

	@Autowired

	ProjectEmployeesRepository assignEmployeesToProjectRepository;

	@Autowired

	MongoTemplate mongoTemplate;

	@Override
	public AssignEmployeesToProjectResponseDto SaveAndUpdateAssinEmpToPro(
			ProjectEmployeeDto assignEmployeesToProjectDto) {
		// TODO Auto-generated method stub

		AssignEmployeesToProjectResponseDto response = new AssignEmployeesToProjectResponseDto();

		if (assignEmployeesToProjectDto.getId() == null)

		{
			ProjectEmployee assnEmpToProject = new ProjectEmployee();

//			assnEmpToProject.setAuthority(assignEmployeesToProjectDto.getAuthority());
//			assnEmpToProject.setEmployeeId(assignEmployeesToProjectDto.getEmployeeId());
//			assnEmpToProject.setProject(assignEmployeesToProjectDto.getProject());
//			
//			DateTimeFormatter In_formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//			DateTimeFormatter Out_formatter2 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
//			String dates2 = assignEmployeesToProjectDto.getStartDate();
//			LocalDate date2 = LocalDate.parse(Out_formatter2.format(In_formatter2.parse(dates2)).toString(), Out_formatter2);
//			assnEmpToProject.setStartDate(date2);
//			
//
//			DateTimeFormatter In_formatter3 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//			DateTimeFormatter Out_formatter3 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
//			String dates3 = assignEmployeesToProjectDto.getEndDate();
//			LocalDate date3 = LocalDate.parse(Out_formatter3.format(In_formatter3.parse(dates3)).toString(), Out_formatter3);
//			assnEmpToProject.setEndDate(date3);

			response.setStatus(true);
			response.setCode("200");
			response.setMessage("assigntoemp saved");

		}

		return response;
	}

	@Override
	public List<ProjectEmployee> getAllEmpToProj(ProjectEmployeeDto assignEmployeesToProjectDto) {

		List<ProjectEmployee> assignEmployeesToProjects = assignEmployeesToProjectRepository.findAll();

		return assignEmployeesToProjects;
	}

}
