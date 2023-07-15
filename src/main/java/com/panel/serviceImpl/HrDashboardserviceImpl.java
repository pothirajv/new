package com.panel.serviceImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panel.model.ClientInformation;
import com.panel.model.EmployeeDetails;
import com.panel.model.LeaveDetails;
import com.panel.model.ProjectInformation;
import com.panel.repository.ClientInformationrepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.LeaveDetailsRepository;
import com.panel.repository.ProjectInformationRepository;
import com.panel.service.HrDashboardService;

@Service
public class HrDashboardserviceImpl implements HrDashboardService {

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	LeaveDetailsRepository leaveDetailsRepository;

	@Autowired
	ClientInformationrepository clientInformationrepository;

	@Autowired
	ProjectInformationRepository projectInformationRepository;

	public LinkedHashMap<String, Object> getAllEmployeeCount() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findAll();
		int count = employeeDetails.size();
		if (employeeDetails.size() > 0) {

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", count);
		} else {
			entity.put("Status", "400");
			entity.put("Message", " No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllClientCount() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<ClientInformation> ClientInformations = clientInformationrepository.findAll();
		int count = ClientInformations.size();
		if (ClientInformations.size() > 0) {

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", count);
		} else {
			entity.put("Status", "400");
			entity.put("Message", " No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getAllProjectCount() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<ProjectInformation> projectInformations = projectInformationRepository.findAll();
		int count = projectInformations.size();
		if (projectInformations.size() > 0) {

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", count);
		} else {
			entity.put("Status", "400");
			entity.put("Message", " No Data is present");
		}

		return entity;
	}

	public LinkedHashMap<String, Object> getTodayLeaves() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<LeaveDetails> leaveDetails = leaveDetailsRepository.findByFromDateBetween(LocalDate.now().minusDays(10),
				LocalDate.now().plusDays(1));
		
		List<LeaveDetails> leaves = leaveDetailsRepository.findByToDateBetween(LocalDate.now().minusDays(1),
				LocalDate.now().plusDays(10));
		
		long count = 0;
		if (leaveDetails.size() > 0) {
			for (LeaveDetails leaveDetail : leaveDetails) {
				for(LeaveDetails leave : leaves) {
					if(leave.getId().equals(leaveDetail.getId())) {
						long days = ChronoUnit.DAYS.between(leaveDetail.getFromDate(), leaveDetail.getToDate().plusDays(1));
						if (days > 0) {
							count++;
						}
					}
				}
			}

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", count);
		} else {
			entity.put("Status", "400");	
			entity.put("Message", " No Data is present");
			entity.put("Data", count);
		}
		return entity;
	}
}
