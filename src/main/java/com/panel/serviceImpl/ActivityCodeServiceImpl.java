package com.panel.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.panel.dto.ActivityCodeAssignDto;
import com.panel.dto.ActivityCodeDto;
import com.panel.model.ActivityCode;
import com.panel.model.ParentActivity;
import com.panel.model.ProjectInformation;
import com.panel.repository.ActivityCodeRepository;
import com.panel.repository.ParentActivityRepository;
import com.panel.repository.ProjectInformationRepository;
import com.panel.service.ActivityCodeService;

@Service
public class ActivityCodeServiceImpl implements ActivityCodeService {

	@Autowired

	ActivityCodeRepository activeCodeRepository;

	@Autowired
	ProjectInformationRepository projectInformationRepository;

	@Autowired

	MongoTemplate mongoTemplate;

	@Override
	public LinkedHashMap<String, Object> SaveAndUpdateActivityCode(ActivityCodeAssignDto activityCodeAssignDto) {

		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		if (!CollectionUtils.isEmpty(activityCodeAssignDto.getActivityCodes())) {
			ActivityCodeDto activityCode = activityCodeAssignDto.getActivityCodes().get(activityCodeAssignDto.getActivityCodes().size() - 1);
			List<ActivityCode> activityCodes = activeCodeRepository.findByProjectId(activityCode.getProjectId());
			List<ActivityCode> activityCodeList = new ArrayList<>();
			List<String> error = new ArrayList<>();
			int i = 1;
			for (ActivityCodeDto activeCodeDto : activityCodeAssignDto.getActivityCodes()) {
				Optional<ProjectInformation> project = projectInformationRepository
						.findById(activeCodeDto.getProjectId());
				if (project.isPresent()) {
					ActivityCode activityCode1 = new ActivityCode();
					List<ActivityCode> activity = activityCodes.stream()
							.filter(a -> a.getActivityCode().equalsIgnoreCase(activeCodeDto.getActivityCode()))
							.collect(Collectors.toList());
					if (CollectionUtils.isEmpty(activity)) {
						activityCode1.setActivityCode(activeCodeDto.getActivityCode());
						activityCode1.setActivityMeta(activeCodeDto.getActivityMeta());
						activityCode1.setAuthority(activeCodeDto.getAuthority());
						activityCode1.setActivityDescription(activeCodeDto.getActivityDescription());
						activityCode1.setProjectId(project.get().getId());
//						activityCode1.setProjectName(project.get().getProjectName());
						activityCode1.setParentActivityId(activeCodeDto.getParentActivityId());
						activityCode1.setCreatedBy(activeCodeDto.getCreatedBy());
						activityCode1.setCreatedOn(LocalDate.now());

						activityCodeList.add(activityCode1);
						i++;
					} else {
						error.add("Change Activity name at row : " + i);
						i++;
					}
				}
			}
			if (error.size() == 0) {
				activeCodeRepository.saveAll(activityCodeList);
				entity.put("status", "200");
				entity.put("message", "Activity Code saved successfully");
				entity.put("Data", activityCodeList);
			} else {
				entity.put("status", "400");
				entity.put("message", "Activity code already exist");
				entity.put("Error", error);
			}
		} else {
			entity.put("status", "400");
			entity.put("message", "No Data Found");
		}
//		else {
//			Optional<ActivityCode> activityCodeOp = activeCodeRepository.findById(activeCodeDto.getId());
//			if (activityCodeOp.isPresent()) {
//				if (activeCodeDto.getProjectId() != null && !activeCodeDto.getProjectId().equals("")) {
//					Optional<ProjectInformation> project = projectInformationRepository.findById(activeCodeDto.getProjectId());
//					if (project.isPresent()) {
//						ActivityCode activityCode = activityCodeOp.get();
//						BeanUtils.copyProperties(activeCodeDto, activityCode, getNullPropertyNames(activeCodeDto));
//						activityCode.setProjectId(project.get().getId());
//						activityCode.setProjectName(project.get().getProjectName());
//						activityCode.setCreatedOn(LocalDate.now());
//						
//						activeCodeRepository.save(activityCode);
//						
//						entity.put("status", "200");
//						entity.put("message", "Activity Code Updated successfully");
//						entity.put("Data", activityCode);
//					}
//				}
//			} else {
//				entity.put("status", "400");
//				entity.put("message", "Invalid Activity Code Id");
//			}
//		}

		return entity;
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null || srcValue.toString().equals("0") || srcValue.equals(""))
				emptyNames.add(pd.getName());
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	@Override
	public LinkedHashMap<String, Object> getAllActivityCode() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<ActivityCode> activCodes = activeCodeRepository.findAll();

		if (activCodes.size() > 0) {

			entity.put("status", "200");
			entity.put("message", "Data is present");
			entity.put("Data", activCodes);

		} else {

			entity.put("status", "400");
			entity.put("message", "No Data present");

		}

		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getActivityCodeById(String id) {

		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<ActivityCode> activOptional = activeCodeRepository.findById(id);

		if (activOptional.isPresent()) {

			entity.put("status", "200");
			entity.put("message", "data is found");
			entity.put("data", activOptional);
		} else {

			entity.put("Status", "405");
			entity.put("Message", "No Data present");
		}

		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getActivityCodeByProject(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<ProjectInformation> project = projectInformationRepository.findById(id);
		if (project.isPresent()) {
			List<ActivityCode> activityCodes = activeCodeRepository.findByProjectId(id);
			if (!CollectionUtils.isEmpty(activityCodes)) {
				entity.put("status", "200");
				entity.put("message", "data is found");
				entity.put("data", activityCodes);
			} else {
				entity.put("Status", "405");
				entity.put("Message", "No Activity Code Found For This Project");
			}
		} else {
			entity.put("Status", "405");
			entity.put("Message", "Invalid Project Id");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getActivityCodeNameByProject(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<ProjectInformation> project = projectInformationRepository.findById(id);
		List<String> metaName = new ArrayList<>();
		if (project.isPresent()) {
			List<ActivityCode> activityCodes = activeCodeRepository.findByProjectId(id);
			for (ActivityCode activityCode : activityCodes) {
				metaName.add(activityCode.getActivityDescription());
			}
			if (!CollectionUtils.isEmpty(metaName)) {
				entity.put("status", "200");
				entity.put("message", "data is found");
				entity.put("data", metaName);
			} else {
				entity.put("Status", "405");
				entity.put("Message", "No Data Found");
			}
		} else {
			entity.put("Status", "405");
			entity.put("Message", "Invalid Project Id");
		}
		return entity;
	}

}
