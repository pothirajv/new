package com.panel.serviceImpl;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panel.dto.BatchProcessDto;
import com.panel.model.BatchProcess;
import com.panel.model.EmployeeDetails;
import com.panel.model.LeaveTransactionHistory;
import com.panel.repository.BatchProcessRepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.LeaveTransactionHistoryRepository;
import com.panel.service.BatchProcessService;

@Service
public class BatchProcessServiceImpl implements BatchProcessService {

	@Autowired
	BatchProcessRepository batchProcessRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	LeaveTransactionHistoryRepository leaveTransactionHistoryRepository;

	private final ModelMapper modelMapper = new ModelMapper();

	@Override
	public LinkedHashMap<String, Object> assignLeaveToEmployees(BatchProcessDto batchProcessDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		BatchProcess batchProcessOp = batchProcessRepository.findByMonth(batchProcessDto.getMonth());
		BatchProcessDto batchProcessDtos = null;
		if (batchProcessOp == null) {
			String dt = LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear();
			if (batchProcessDto.getMonth().equalsIgnoreCase(dt)) {
				int i = 0;
				BatchProcess batchProcess = new BatchProcess();
				batchProcess.setBatchType("Leave");
				batchProcess.setMonth(batchProcessDto.getMonth());
				batchProcess.setCreatedDate(LocalDate.now());
				batchProcess.setCreatedBy(batchProcessDto.getCreatedBy());

				List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findAll();
				List<EmployeeDetails> permanent = employeeDetails.stream()
						.filter(e -> e.getEmployeeCategory().getEmploymentTypesName().equals("Permanent"))
						.collect(Collectors.toList());
				for (EmployeeDetails emp : permanent) {
					LeaveTransactionHistory leaveTransactionHistory = new LeaveTransactionHistory();
					emp.setTotalLeave((float) 1.5 + emp.getBalance());
					emp.setBalance((float) 1.5 + emp.getBalance());
					employeeDetailsRepository.save(emp);
					i++;
					leaveTransactionHistory.setEmployeeId(emp.getId());
					leaveTransactionHistory.setNoOfDays((float) 1.5 + emp.getBalance());
					leaveTransactionHistory.setCreatedDate(LocalDate.now());
					leaveTransactionHistory.setCreatedBy(batchProcessDto.getCreatedBy());
					leaveTransactionHistoryRepository.save(leaveTransactionHistory);
				}

				List<EmployeeDetails> contract = employeeDetails.stream()
						.filter(e -> e.getEmployeeCategory().getEmploymentTypesName().equals("Contract"))
						.collect(Collectors.toList());
				for (EmployeeDetails emp : contract) {
					LeaveTransactionHistory leaveTransactionHistory = new LeaveTransactionHistory();
					emp.setTotalLeave(1 + emp.getBalance());
					emp.setBalance(1 + emp.getBalance());
					employeeDetailsRepository.save(emp);
					i++;
					leaveTransactionHistory.setEmployeeId(emp.getId());
					leaveTransactionHistory.setNoOfDays(1 + emp.getBalance());
					leaveTransactionHistory.setCreatedDate(LocalDate.now());
					leaveTransactionHistory.setCreatedBy(batchProcessDto.getCreatedBy());
					leaveTransactionHistoryRepository.save(leaveTransactionHistory);
				}
				batchProcess.setNoOfEmployee(i);
				batchProcessRepository.save(batchProcess);

				batchProcessDtos = modelMapper.map(batchProcess, BatchProcessDto.class);

				entity.put("status", 200);
				entity.put("Message", "Batch Process saved for leave");
				entity.put("Date", batchProcessDtos);
			} else {
				entity.put("status", 400);
				entity.put("Message", "Please Select Current Month");
			}
		} else {
			entity.put("status", 400);
			entity.put("Message", "Leave Already Updated For This Month");
		}
		return entity;
	}

}
