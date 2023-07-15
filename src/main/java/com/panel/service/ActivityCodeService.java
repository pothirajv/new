package com.panel.service;

import java.util.LinkedHashMap;
import com.panel.dto.ActivityCodeAssignDto;


public interface ActivityCodeService {
	
	
	
	public LinkedHashMap<String, Object> SaveAndUpdateActivityCode(ActivityCodeAssignDto activityCodeAssignDto);
	
	public LinkedHashMap<String, Object> getAllActivityCode();
	
	public LinkedHashMap<String, Object> getActivityCodeById(String id);
	
	public LinkedHashMap<String, Object> getActivityCodeByProject(String id);
	
	public LinkedHashMap<String, Object> getActivityCodeNameByProject(String id);

}
