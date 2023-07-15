package com.panel.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.panel.model.EmployeeDetails;
import com.panel.model.LeaveDetails;
import com.panel.model.LeaveTransactionHistory;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.LeaveDetailsRepository;
import com.panel.repository.LeaveTransactionHistoryRepository;
import com.panel.service.LeaveTransactionHistoryService;

@Service
public class LeaveTransactionHistoryServiceImpl implements LeaveTransactionHistoryService {

	@Autowired
	LeaveTransactionHistoryRepository leaveTransactionHistoryRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	LeaveDetailsRepository leaveDetailsRepository;

	@Override
	public LinkedHashMap<String, Object> getLeaveTransactionHistoryByEmployee(LocalDate fromDate, LocalDate toDate,
			String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository.findById(id);
		if (employeeDetail.isPresent()) {
			List<LeaveDetails> leaveDetails = null;
			List<LeaveDetails> leaveList = new ArrayList<>();
			if ((fromDate != null && !StringUtils.isEmpty(fromDate))
					&& (toDate != null && !StringUtils.isEmpty(toDate))) {
				leaveDetails = leaveDetailsRepository.findByFromDateBetween(fromDate.minusDays(1), toDate.plusDays(1));
			} else {
				leaveDetails = leaveDetailsRepository.findAll();
			}
			if (!CollectionUtils.isEmpty(leaveDetails)) {
				for (LeaveDetails leaveDetail : leaveDetails) {
					if (leaveDetail.getEmpCode().equals(employeeDetail.get().getEmployeeCode())) {
						leaveList.add(leaveDetail);
					}
				}
				if (!CollectionUtils.isEmpty(leaveList)) {
					List<LeaveTransactionHistory> leaveTransactionHistories = leaveTransactionHistoryRepository
							.findByEmployeeId(id);
					if (!CollectionUtils.isEmpty(leaveTransactionHistories)) {
						List<LeaveTransactionHistory> transactionHistories = new ArrayList<>();
						for (LeaveDetails leaveDetail : leaveList) {
							for (LeaveTransactionHistory leaveHistory : leaveTransactionHistories) {
								if (leaveHistory.getLeaveDetailsId() != null
										&& !leaveHistory.getLeaveDetailsId().isEmpty()) {
									if (leaveHistory.getLeaveDetailsId().equals(leaveDetail.getId())) {
										transactionHistories.add(leaveHistory);
									}
								}
							}
							entity.put("status", 200);
							entity.put("message", "Data is present");
							entity.put("data", transactionHistories);
						}
					} else {
						entity.put("status", 400);
						entity.put("message", "You have no Leave transaction history");
					}
				} else {
					entity.put("status", 400);
					entity.put("message", "You have no Leave records");
				}
			} else {
				entity.put("status", 400);
				entity.put("message", "No Data Found");
			}
		}
		return entity;
	}

}
