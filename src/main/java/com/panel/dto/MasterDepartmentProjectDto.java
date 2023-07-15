package com.panel.dto;

import java.util.List;

import com.panel.model.MasterDepartment;

public class MasterDepartmentProjectDto {

	private MasterDepartment masterDepartment;
	private List<String> projects;
	private List<String> resources;

	public MasterDepartment getMasterDepartment() {
		return masterDepartment;
	}

	public void setMasterDepartment(MasterDepartment masterDepartment) {
		this.masterDepartment = masterDepartment;
	}

	public List<String> getProjects() {
		return projects;
	}

	public void setProjects(List<String> projects) {
		this.projects = projects;
	}

	public List<String> getResources() {
		return resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}
}
