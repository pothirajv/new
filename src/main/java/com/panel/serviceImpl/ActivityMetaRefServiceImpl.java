package com.panel.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.panel.dto.ActivityMetaRefDto;
import com.panel.model.ActivityMetaRef;
import com.panel.model.EmployeeDetails;
import com.panel.model.ParentActivity;
import com.panel.repository.ActivityMetaRefRepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.ParentActivityRepository;
import com.panel.service.ActivityMetaRefService;

@Service
public class ActivityMetaRefServiceImpl implements ActivityMetaRefService {

	@Autowired
	ActivityMetaRefRepository activityMetaRefRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	ParentActivityRepository parentActivityRepository;

	@Override
	public LinkedHashMap<String, Object> saveOrUpdateActivityMeta(ActivityMetaRefDto activityMetaRefDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		if (activityMetaRefDto.getId() == null) {
			ActivityMetaRef activityMeta = new ActivityMetaRef();
			List<ActivityMetaRef> metas = activityMetaRefRepository.findAll();
			List<ActivityMetaRef> meta = metas.stream()
					.filter(m -> m.getActivityMeta().equalsIgnoreCase(activityMetaRefDto.getActivityMeta()))
					.collect(Collectors.toList());
			if (CollectionUtils.isEmpty(meta)) {
				activityMeta.setActivityMeta(activityMetaRefDto.getActivityMeta());
				activityMeta.setActivityMetaDescription(activityMetaRefDto.getActivityMetaDescription());
				if (activityMetaRefDto.getParentActivityId() != null
						&& !activityMetaRefDto.getParentActivityId().isEmpty()) {
					Optional<ParentActivity> parent = parentActivityRepository
							.findById(activityMetaRefDto.getParentActivityId());
					if (parent.isPresent()) {
						activityMeta.setParentActivityId(activityMetaRefDto.getParentActivityId());
						activityMeta.setParentActivity(parent.get());
					}
				} else {
					entity.put("Status", 400);
					entity.put("Message", "Select Parent activity");
					return entity;
				}
				activityMeta.setCreatedDate(LocalDate.now());
				activityMeta.setCreatedBy(activityMetaRefDto.getCreatedBy());
				activityMetaRefRepository.save(activityMeta);

				entity.put("Status", 200);
				entity.put("Message", "Activity Meta Saved Successfully");
				entity.put("Data", activityMeta);
			} else {
				entity.put("Status", 400);
				entity.put("Message", "Activity Meta Already Exist");
			}
		} else {
			Optional<ActivityMetaRef> activityMeta = activityMetaRefRepository.findById(activityMetaRefDto.getId());
			if (activityMeta.isPresent()) {
				if (activityMetaRefDto.getActivityMeta() != null && !activityMetaRefDto.getActivityMeta().equals("")) {
					activityMeta.get().setActivityMeta(activityMetaRefDto.getActivityMeta());
				} else {
					activityMeta.get().setActivityMeta(activityMeta.get().getActivityMeta());
				}
				if (activityMetaRefDto.getActivityMetaDescription() != null
						&& !activityMetaRefDto.getActivityMetaDescription().equals("")) {
					activityMeta.get().setActivityMetaDescription(activityMetaRefDto.getActivityMetaDescription());
				} else {
					activityMeta.get().setActivityMetaDescription(activityMeta.get().getActivityMetaDescription());
				}
				if (activityMetaRefDto.getParentActivityId() != null
						&& !activityMetaRefDto.getParentActivityId().isEmpty()) {
					activityMeta.get().setParentActivityId(activityMetaRefDto.getParentActivityId());
				} else {
					activityMeta.get().setParentActivityId(activityMeta.get().getParentActivityId());
				}
				activityMeta.get().setCreatedDate(LocalDate.now());
				if (activityMetaRefDto.getCreatedBy() != null && !activityMetaRefDto.getCreatedBy().equals("")) {
					activityMeta.get().setCreatedBy(activityMetaRefDto.getCreatedBy());
				} else {
					activityMeta.get().setCreatedBy(activityMeta.get().getCreatedBy());
				}

				activityMetaRefRepository.save(activityMeta.get());

				entity.put("Status", 200);
				entity.put("Message", "Activity Meta Updated Successfully");
				entity.put("Data", activityMeta);
			} else {
				entity.put("Status", 400);
				entity.put("Message", "Invalid Activity Meta Id");
			}
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getActivityMetaById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		Optional<ActivityMetaRef> activityMeta = activityMetaRefRepository.findById(id);
		if (activityMeta.isPresent()) {
			entity.put("Status", 200);
			entity.put("Message", "Activity Meta Saved Successfully");
			entity.put("Data", activityMeta);
		} else {
			entity.put("Status", 400);
			entity.put("Message", "Invalid Activity Meta Id");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getAllActivityMetaRef(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		List<ActivityMetaRef> activityMetaRefs = new ArrayList<>();
		if (id == null) {
			activityMetaRefs = activityMetaRefRepository.findAll();
		} else {
			activityMetaRefs = activityMetaRefRepository.findByParentActivityId(id);
		}

		if (!CollectionUtils.isEmpty(activityMetaRefs)) {
			for (ActivityMetaRef activityMeta : activityMetaRefs) {
				activityMeta.setActivityMeta(
						activityMeta.getActivityMeta() + "-" + activityMeta.getActivityMetaDescription());
				if (activityMeta.getCreatedBy() != null && !activityMeta.getCreatedBy().isEmpty()) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(activityMeta.getCreatedBy());
					if (employee.isPresent()) {
						activityMeta.setCreatedBy(employee.get().getEmployeeCode() + "-" + employee.get().getName());
					}
				}
				if (activityMeta.getParentActivityId() != null && !activityMeta.getParentActivityId().isEmpty()) {
					Optional<ParentActivity> parentActivity = parentActivityRepository
							.findById(activityMeta.getParentActivityId());
					if (parentActivity.isPresent()) {
						activityMeta.setParentActivityId(parentActivity.get().getParentActivity() + "-"
								+ parentActivity.get().getParentActivityDesc());
					}
				}
			}
			activityMetaRefs = activityMetaRefs.stream()
					.sorted(Comparator.comparing(ActivityMetaRef::getParentActivityId)).collect(Collectors.toList());
			entity.put("Status", 200);
			entity.put("Message", "Data is present");
			entity.put("Data", activityMetaRefs);
		} else {
			entity.put("Status", 400);
			entity.put("Message", "No Data Found");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getActivityMetaByParentActivity(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		Optional<ParentActivity> parentActivity = parentActivityRepository.findById(id);
		if (parentActivity.isPresent()) {
			List<ActivityMetaRef> activityMetaRefs = activityMetaRefRepository.findByParentActivityId(id);
			if (!CollectionUtils.isEmpty(activityMetaRefs)) {
				for (ActivityMetaRef activityMeta : activityMetaRefs) {
					activityMeta.setActivityMeta(
							activityMeta.getActivityMeta() + "-" + activityMeta.getActivityMetaDescription());
					if (activityMeta.getParentActivityId() != null && !activityMeta.getParentActivityId().isEmpty()) {
						Optional<ParentActivity> parentAct = parentActivityRepository
								.findById(activityMeta.getParentActivityId());
						if (parentAct.isPresent()) {
							activityMeta.setParentActivityId(parentAct.get().getParentActivity() + "-"
									+ parentAct.get().getParentActivityDesc());
						}
					}
				}
				entity.put("Status", 200);
				entity.put("Message", "Data is present");
				entity.put("Data", activityMetaRefs);
			} else {
				entity.put("Status", 400);
				entity.put("Message", "No Data Found");
			}
		}
		return entity;
	}
}
