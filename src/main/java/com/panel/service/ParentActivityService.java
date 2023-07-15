package com.panel.service;

import java.util.LinkedHashMap;

import com.panel.dto.ParentActivityDto;

public interface ParentActivityService {

	public LinkedHashMap<String, Object> saveParentActivity(ParentActivityDto parentActivityDto);
	
	public LinkedHashMap<String, Object> getParentActivityById(String id);
	
	public LinkedHashMap<String, Object> getAllParentActivity();
}
