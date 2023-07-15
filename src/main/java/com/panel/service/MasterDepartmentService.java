package com.panel.service;

import java.util.LinkedHashMap;
import com.panel.dto.MasterDepartmentDto;

public interface MasterDepartmentService {

	public LinkedHashMap<String, Object> saveAndUpdateMasterDepartment(MasterDepartmentDto masterDepartmentDto);
	public LinkedHashMap<String, Object> getAllMasterDepartments();
	public LinkedHashMap<String, Object> getMasterDepartmentById(String id);
	public LinkedHashMap<String, Object> getMasterDepartmentFilter(MasterDepartmentDto masterDepartmentDto);
	public LinkedHashMap<String, Object> getMasterDepartmentsByBusinessUnit(String id);
	public LinkedHashMap<String, Object> getMasterDepartmentByBusinessUnitAndDeptHead(String unitId, String id);
	public LinkedHashMap<String, Object> getMasterDepartmentByBusinessUnitHead(String id);
	public LinkedHashMap<String, Object> getMasterDepartmentAndProjects(String id);
}
