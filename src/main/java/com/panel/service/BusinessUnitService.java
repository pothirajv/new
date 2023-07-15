package com.panel.service;

import java.util.LinkedHashMap;

import com.panel.dto.BusinessUnitDto;

public interface BusinessUnitService {

	public LinkedHashMap<String, Object> saveAndUpdateBusinessUnit(BusinessUnitDto businessUnitDto);
	public LinkedHashMap<String, Object> getAllBusinessUnits();
	public LinkedHashMap<String, Object> getBusinessUnitById(String id);
	public LinkedHashMap<String, Object> getBusinessUnitFilter(BusinessUnitDto businessUnitDto);
	public LinkedHashMap<String, Object> getBusinessUnitByHead(String id);
}
