package com.panel.service;

import java.util.LinkedHashMap;

import com.panel.dto.ActivityMetaRefDto;

public interface ActivityMetaRefService {
	
	LinkedHashMap<String, Object> saveOrUpdateActivityMeta(ActivityMetaRefDto activityMetaRefDto);
	
	LinkedHashMap<String, Object> getActivityMetaById(String id);
	
	LinkedHashMap<String, Object> getAllActivityMetaRef(String id);
	
	LinkedHashMap<String, Object> getActivityMetaByParentActivity(String id);

}
