package com.panel.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;

public interface LeaveTransactionHistoryService {
	
	public LinkedHashMap<String, Object> getLeaveTransactionHistoryByEmployee(LocalDate fromDate, LocalDate toDate, String id);

}
