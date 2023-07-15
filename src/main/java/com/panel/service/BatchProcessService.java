package com.panel.service;

import java.util.LinkedHashMap;

import com.panel.dto.BatchProcessDto;

public interface BatchProcessService {

	public LinkedHashMap<String, Object> assignLeaveToEmployees(BatchProcessDto batchProcessDto);
}
