package com.panel.serviceImpl;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.panel.dto.ParentActivityDto;
import com.panel.model.EmployeeDetails;
import com.panel.model.ParentActivity;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.ParentActivityRepository;
import com.panel.service.ParentActivityService;

@Service
public class ParentActivityServiceImpl implements ParentActivityService {

	@Autowired
	ParentActivityRepository parentActivityRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Override
	public LinkedHashMap<String, Object> saveParentActivity(ParentActivityDto parentActivityDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		if (parentActivityDto.getId() == null || parentActivityDto.getId().isEmpty()) {
			ParentActivity parentActivity = new ParentActivity();
			parentActivity.setParentActivity(parentActivityDto.getParentActivity());
			parentActivity.setParentActivityDesc(parentActivityDto.getParentActivityDesc());
			parentActivity.setCreatedBy(parentActivityDto.getCreatedBy());
			parentActivity.setCreatedDate(LocalDate.now());
			List<ParentActivity> parentActivities = parentActivityRepository
					.findByParentActivityIgnoreCase(parentActivityDto.getParentActivity());
			if (CollectionUtils.isEmpty(parentActivities)) {
				parentActivityRepository.save(parentActivity);
				entity.put("Status", "200");
				entity.put("Message", "Parent Activity Saved Successfully");
				entity.put("Data", parentActivity);
			} else {
				entity.put("Status", "400");
				entity.put("Message", "Parent Activity Already Exist");
			}
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getParentActivityById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		Optional<ParentActivity> parentActivityOp = parentActivityRepository.findById(id);
		if (parentActivityOp.isPresent()) {
			if (parentActivityOp.get().getCreatedBy() != null && !parentActivityOp.get().getCreatedBy().isEmpty()) {
				Optional<EmployeeDetails> empOptional = employeeDetailsRepository
						.findById(parentActivityOp.get().getCreatedBy());
				if (empOptional.isPresent()) {
					parentActivityOp.get()
							.setCreatedBy(empOptional.get().getEmployeeCode() + "-" + empOptional.get().getName());
				}
			}
			entity.put("Status", "200");
			entity.put("Message", "Parent Activity is present");
			entity.put("Data", parentActivityOp);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data Present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getAllParentActivity() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		List<ParentActivity> parentActivities = parentActivityRepository.findAll();
		if (!CollectionUtils.isEmpty(parentActivities)) {
			for (ParentActivity parentActivity : parentActivities) {
				if (parentActivity.getCreatedBy() != null && !parentActivity.getCreatedBy().isEmpty()) {
					Optional<EmployeeDetails> empOptional = employeeDetailsRepository
							.findById(parentActivity.getCreatedBy());
					if (empOptional.isPresent()) {
						parentActivity
								.setCreatedBy(empOptional.get().getEmployeeCode() + "-" + empOptional.get().getName());
					}
				}
				parentActivity.setParentActivity(
						parentActivity.getParentActivity() + "-" + parentActivity.getParentActivityDesc());
			}
			entity.put("Status", "200");
			entity.put("Message", "Parent Activity is present");
			entity.put("Data", parentActivities);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data Present");
		}
		return entity;
	}

}
