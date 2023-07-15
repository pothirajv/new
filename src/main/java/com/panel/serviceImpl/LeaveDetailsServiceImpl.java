package com.panel.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.panel.dto.LOPDaysDto;
import com.panel.dto.LeaveDetailsDto;
import com.panel.dto.LeaveDetailsListDto;
import com.panel.model.BatchProcess;
import com.panel.model.BusinessUnit;
import com.panel.model.EmployeeDetails;
import com.panel.model.LOPLeaveDetails;
import com.panel.model.LeaveDetails;
import com.panel.model.LeaveStatus;
import com.panel.model.LeaveTransactionHistory;
import com.panel.model.LeaveType;
import com.panel.model.MasterDepartment;
import com.panel.repository.BatchProcessRepository;
import com.panel.repository.BusinessUnitRepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.EmployeeRepository;
import com.panel.repository.LOPLeaveDetailsRepository;
import com.panel.repository.LeaveDetailsRepository;
import com.panel.repository.LeaveDurationRepository;
import com.panel.repository.LeaveStatusRepository;
import com.panel.repository.LeaveTransactionHistoryRepository;
import com.panel.repository.LeaveTypeRepository;
import com.panel.repository.MasterDepartmentRepository;
import com.panel.service.LeaveDetailsService;
import com.panel.support.constant.GeneralConstants;
import com.panel.support.util.Utility;

@Service
public class LeaveDetailsServiceImpl implements LeaveDetailsService {

	@Autowired
	LeaveDetailsRepository leaveDetailsRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	LeaveTypeRepository leaveTypeRepository;

	@Autowired
	LeaveDurationRepository leaveDurationRepository;

	@Autowired
	LeaveStatusRepository leaveStatusRepository;

	@Autowired
	BatchProcessRepository batchProcessRepository;

	@Autowired
	LeaveTransactionHistoryRepository leaveTransactionHistoryRepository;

	@Autowired
	LOPLeaveDetailsRepository lopLeaveDetailsRepository;
	
	@Autowired
	BusinessUnitRepository businessUnitRepository;
	
	@Autowired
	MasterDepartmentRepository masterDepartmentRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	Utility utility;

	public LinkedHashMap<String, Object> saveAndUpdateLeaveDetails(LeaveDetailsDto leaveDetailsDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		if (leaveDetailsDto.getId() == null) {
			Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository
					.findByEmployeeCode(leaveDetailsDto.getEmpCode());
			if (employeeDetail.isPresent()) {
				Boolean status = false;
				String dt = LocalDate.now().getMonth().toString() + " " + LocalDate.now().getYear();
				List<BatchProcess> batchProcesses = batchProcessRepository.findByBatchType("Leave");
				for (BatchProcess batchProcess : batchProcesses) {
					if (batchProcess.getMonth().equalsIgnoreCase(dt)) {
						status = true;
					}
				}
				if (status == true) {
					LeaveDetails leaveDetail = new LeaveDetails();

					if (leaveDetailsDto.getFromDate() != null && !leaveDetailsDto.getFromDate().equals("")) {
						DateTimeFormatter In_formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						DateTimeFormatter Out_formatter1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
						String date2 = leaveDetailsDto.getFromDate();
						LocalDate date3 = LocalDate.parse(Out_formatter1.format(In_formatter1.parse(date2)).toString(),
								Out_formatter1);
						leaveDetail.setFromDate(date3);
					} else {
						leaveDetail.setFromDate(null);
					}

					if (leaveDetailsDto.getToDate() != null && !leaveDetailsDto.getToDate().equals("")) {
						DateTimeFormatter In_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
						DateTimeFormatter Out_formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
						String date = leaveDetailsDto.getToDate();
						LocalDate date1 = LocalDate.parse(Out_formatter.format(In_formatter.parse(date)).toString(),
								Out_formatter);
						leaveDetail.setToDate(date1);
					} else {
						leaveDetail.setToDate(null);
					}
					Optional<LeaveType> leaveType = null;
					if (leaveDetailsDto.getLeaveType() != null && !leaveDetailsDto.getLeaveType().equals("")) {
						leaveType = leaveTypeRepository.findById(leaveDetailsDto.getLeaveType());
						if (leaveType.isPresent()) {
							leaveDetail.setLeaveType(leaveType.get());
						}
					} else {
						leaveDetail.setLeaveType(null);
					}
					
					if (leaveType.get().getLeaveType().equalsIgnoreCase("Earned")
							&& employeeDetail.get().getBalance() == 0) {
						entity.put("Status", "400");
						entity.put("Message",
								"You have Exhausted your earned leave balance..., apply for LOP");
						return entity;
					}
					
					if (leaveType.get().getLeaveType().equalsIgnoreCase("Earned")
							&& employeeDetail.get().getBalance() < leaveDetailsDto.getNoOfDays()) {
						entity.put("Status", "400");
						entity.put("Message",
								"You do not have enough leave balance – Split your leave into Earned Leave and LOP");
						return entity;
					}
					
					leaveDetail.setEmpCode(employeeDetail.get().getEmployeeCode());
					leaveDetail.setEmpName(employeeDetail.get().getName());
					leaveDetail.setReason(leaveDetailsDto.getReason());
					leaveDetail.setReportingPerson(employeeDetail.get().getReportingManager());
					leaveDetail.setRequestedDate(LocalDate.now());
					if (leaveDetailsDto.getLeaveStatus() != null && !leaveDetailsDto.getLeaveStatus().isEmpty()) {
						Optional<LeaveStatus> leaveStatus = leaveStatusRepository
								.findById(leaveDetailsDto.getLeaveStatus());
						if (leaveStatus.isPresent()) {
							leaveDetail.setLeaveStatus(leaveStatus.get());
						}
					}
					LeaveTransactionHistory leaveTransactionHistory = new LeaveTransactionHistory();
					if (!leaveType.get().getLeaveType().equalsIgnoreCase("LOP")) {
						leaveDetail.setNoOfDays(leaveDetailsDto.getNoOfDays());
						leaveDetail.setStatus(true);
						if (!leaveType.get().getLeaveType().equalsIgnoreCase("Maternity")
								&& !leaveType.get().getLeaveType().equalsIgnoreCase("Paternity")) {
							employeeDetail.get()
									.setBalance(employeeDetail.get().getBalance() - leaveDetailsDto.getNoOfDays());
						}
						leaveTransactionHistory.setEmployeeId(employeeDetail.get().getId());
						leaveTransactionHistory.setLeaveStatus("Open");
						leaveTransactionHistory.setCreatedDate(LocalDate.now());
						leaveTransactionHistory.setNoOfDays(leaveDetailsDto.getNoOfDays());
						leaveTransactionHistory.setCreatedBy(employeeDetail.get().getId());
					} else {
						if (employeeDetail.get().getBalance() == 0) {
							leaveDetail.setNoOfDays(leaveDetailsDto.getNoOfDays());
							leaveDetail.setStatus(true);

							leaveTransactionHistory.setEmployeeId(employeeDetail.get().getId());
							leaveTransactionHistory.setLeaveStatus("Open");
							leaveTransactionHistory.setCreatedDate(LocalDate.now());
							leaveTransactionHistory.setNoOfDays(leaveDetailsDto.getNoOfDays());
							leaveTransactionHistory.setCreatedBy(employeeDetail.get().getId());
						} else {
							entity.put("Status", "400");
							entity.put("Message", "Available leave balance is " + employeeDetail.get().getBalance()
									+ " days... Exhaust this leave befor applying for LOP");
							return entity;
						}
					}
					leaveDetail.setLopDays(0);
					employeeDetailsRepository.save(employeeDetail.get());
					leaveDetailsRepository.save(leaveDetail);
					if (leaveTransactionHistory != null) {
						leaveTransactionHistory.setLeaveDetailsId(leaveDetail.getId());
						leaveTransactionHistoryRepository.save(leaveTransactionHistory);
					}
					entity.put("Status", "200");
					entity.put("Message", "LeaveDetails Applied Successfully");
					entity.put("Data", leaveDetail);
				} else {
					entity.put("Status", "400");
					entity.put("Message", "Apply Leave Request For Current Month");
				}
			} else {
				entity.put("Status", "400");
				entity.put("Message", "Invalid EmployeeCode");
			}
		} else {
			Optional<LeaveDetails> leaveDetail = leaveDetailsRepository.findById(leaveDetailsDto.getId());
			if (leaveDetail.isPresent()) {
				LeaveDetails leaveDetails = leaveDetail.get();
				float days = leaveDetails.getNoOfDays();

				BeanUtils.copyProperties(leaveDetailsDto, leaveDetails, getNullPropertyNames(leaveDetailsDto));
				leaveDetails.setLopDays(0);
				Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository
						.findByEmployeeCode(leaveDetailsDto.getEmpCode());

				Optional<LeaveType> leaveType = null;
				if (leaveDetailsDto.getLeaveType() != null && !leaveDetailsDto.getLeaveType().equals("")) {
					leaveType = leaveTypeRepository.findById(leaveDetailsDto.getLeaveType());
					if (leaveType.isPresent()) {
						leaveDetails.setLeaveType(leaveType.get());
					}
				} else {
					leaveDetails.setLeaveType(null);
				}

				if (leaveDetailsDto.getFromDate() != null && !leaveDetailsDto.getFromDate().equals("")) {
					DateTimeFormatter In_formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date2 = leaveDetailsDto.getFromDate();
					LocalDate date3 = LocalDate.parse(Out_formatter1.format(In_formatter1.parse(date2)).toString(),
							Out_formatter1);
					leaveDetails.setFromDate(date3);
				} else {
					leaveDetails.setFromDate(null);
				}

				if (leaveDetailsDto.getToDate() != null && !leaveDetailsDto.getToDate().equals("")) {
					DateTimeFormatter In_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date = leaveDetailsDto.getToDate();
					LocalDate date1 = LocalDate.parse(Out_formatter.format(In_formatter.parse(date)).toString(),
							Out_formatter);
					leaveDetails.setToDate(date1);
				} else {
					leaveDetails.setToDate(null);
				}
				LeaveDetails leaveDetailOp = new LeaveDetails();
				String str = "-" + days;
				LeaveTransactionHistory leaveTransactionHistory = new LeaveTransactionHistory();
				if (leaveDetailsDto.getLeaveStatus() != null && !leaveDetailsDto.getLeaveStatus().equals("")) {
					Optional<LeaveStatus> leaveStatus = leaveStatusRepository
							.findById(leaveDetailsDto.getLeaveStatus());
					if (leaveStatus.get().getStatus().equalsIgnoreCase("Approved")) {
						leaveDetails.setLeaveStatus(leaveStatus.get());
						employeeDetail.get().setBalance(employeeDetail.get().getBalance());
						employeeDetailsRepository.save(employeeDetail.get());

						leaveTransactionHistory.setEmployeeId(employeeDetail.get().getId());
						leaveTransactionHistory.setLeaveDetailsId(leaveDetails.getId());
						leaveTransactionHistory.setNoOfDays(leaveDetails.getNoOfDays());
						leaveTransactionHistory.setCreatedDate(LocalDate.now());
						leaveTransactionHistory.setCreatedBy(employeeDetail.get().getId());
						leaveTransactionHistory.setLeaveStatus("Approved");
						leaveTransactionHistoryRepository.save(leaveTransactionHistory);
					} else if (leaveStatus.get().getStatus().equalsIgnoreCase("Rejected")) {
						if (leaveDetails.getLeaveType().getLeaveType().equals("Maternity")
								|| leaveDetails.getLeaveType().getLeaveType().equals("Paternity")) {
							leaveDetails.setLeaveStatus(leaveStatus.get());
//							LOPLeaveDetails lopLeaveDetails = lopLeaveDetailsRepository
//									.findByLeaveDetailsId(leaveDetails.getId());
//							if (lopLeaveDetails != null) {
//								lopLeaveDetails.setStatus(false);
//								lopLeaveDetailsRepository.save(lopLeaveDetails);
//							}

							leaveTransactionHistory.setEmployeeId(employeeDetail.get().getId());
							leaveTransactionHistory.setLeaveDetailsId(leaveDetails.getId());
							leaveTransactionHistory.setNoOfDays(Float.parseFloat(str));
							leaveTransactionHistory.setCreatedDate(LocalDate.now());
							leaveTransactionHistory.setCreatedBy(employeeDetail.get().getId());
							leaveTransactionHistory.setLeaveStatus("Approved");
							leaveTransactionHistoryRepository.save(leaveTransactionHistory);
						} else {
							leaveDetails.setLeaveStatus(leaveStatus.get());
//							LOPLeaveDetails lopLeaveDetails = lopLeaveDetailsRepository
//									.findByLeaveDetailsId(leaveDetails.getId());
//
							if (!leaveType.get().getLeaveType().equalsIgnoreCase("LOP")) {
								employeeDetail.get()
										.setBalance(leaveDetails.getNoOfDays() + employeeDetail.get().getBalance());
							}

							leaveTransactionHistory.setEmployeeId(employeeDetail.get().getId());
							leaveTransactionHistory.setLeaveDetailsId(leaveDetails.getId());
							leaveTransactionHistory.setNoOfDays(Float.parseFloat(str));
							leaveTransactionHistory.setCreatedDate(LocalDate.now());
							leaveTransactionHistory.setCreatedBy(employeeDetail.get().getId());
							leaveTransactionHistory.setLeaveStatus("Rejected");
							leaveTransactionHistoryRepository.save(leaveTransactionHistory);
						}
					} else {
						float bal = 0;
						if (leaveDetails.isStatus()) {
							if (leaveDetails.getNoOfDays() != days) {
								leaveDetails.setLeaveStatus(leaveStatus.get());

								leaveTransactionHistory.setEmployeeId(employeeDetail.get().getId());
								leaveTransactionHistory.setLeaveDetailsId(leaveDetails.getId());
								leaveTransactionHistory.setNoOfDays(Float.parseFloat(str));
								leaveTransactionHistory.setCreatedDate(LocalDate.now());
								leaveTransactionHistory.setCreatedBy(employeeDetail.get().getId());
								leaveTransactionHistory.setLeaveStatus("Open");
								leaveTransactionHistoryRepository.save(leaveTransactionHistory);
								if (!leaveType.get().getLeaveType().equalsIgnoreCase("Maternity")
										&& !leaveType.get().getLeaveType().equalsIgnoreCase("Paternity")) {
									if (leaveDetail.get().isStatus()) {
										if (leaveDetailsDto.getNoOfDays() > days) {
//											LOPLeaveDetails lopLeaveDetails = lopLeaveDetailsRepository
//													.findByLeaveDetailsId(leaveDetail.get().getId());
											if (leaveType.get().getLeaveType().equalsIgnoreCase("LOP")) {
//												bal = (leaveDetailsDto.getNoOfDays() - days)
//														+ lopLeaveDetails.getNoOfDays();
//												lopLeaveDetails.setNoOfDays(bal);
//												lopLeaveDetailsRepository.save(lopLeaveDetails);
												leaveDetails.setNoOfDays(leaveDetailsDto.getNoOfDays());
												LeaveTransactionHistory leaveTransactionHistoryOp = new LeaveTransactionHistory();
												leaveTransactionHistoryOp.setEmployeeId(employeeDetail.get().getId());
												leaveTransactionHistoryOp.setLeaveDetailsId(leaveDetails.getId());
												leaveTransactionHistoryOp.setNoOfDays(leaveDetailsDto.getNoOfDays());
												leaveTransactionHistoryOp.setCreatedDate(LocalDate.now());
												leaveTransactionHistoryOp.setCreatedBy(employeeDetail.get().getId());
												leaveTransactionHistoryOp.setLeaveStatus("Open");
												leaveTransactionHistoryRepository.save(leaveTransactionHistoryOp);
											} else {
												float count = employeeDetail.get().getBalance() + days;
												if (count >= leaveDetailsDto.getNoOfDays()) {
													leaveDetails.setNoOfDays(leaveDetailsDto.getNoOfDays());
													employeeDetail.get().setBalance(employeeDetail.get().getBalance()
															- (leaveDetailsDto.getNoOfDays() - days));

													LeaveTransactionHistory leaveTransactionHistoryOp = new LeaveTransactionHistory();
													leaveTransactionHistoryOp
															.setEmployeeId(employeeDetail.get().getId());
													leaveTransactionHistoryOp.setLeaveDetailsId(leaveDetails.getId());
													leaveTransactionHistoryOp
															.setNoOfDays(leaveDetailsDto.getNoOfDays());
													leaveTransactionHistoryOp.setCreatedDate(LocalDate.now());
													leaveTransactionHistoryOp
															.setCreatedBy(employeeDetail.get().getId());
													leaveTransactionHistoryOp.setLeaveStatus("Open");
													leaveTransactionHistoryRepository.save(leaveTransactionHistoryOp);
												} else {
													entity.put("Status", "400");
													entity.put("Message",
															"You do not have enough leave balance – Split your leave into Earned Leave and LOP");
													return entity;
												}
											}
										} else {
//											LOPLeaveDetails lopLeaveDetails = lopLeaveDetailsRepository
//													.findByLeaveDetailsId(leaveDetail.get().getId());
											if (leaveType.get().getLeaveType().equalsIgnoreCase("LOP")) {
//												bal = employeeDetail.get().getBalance()
//														+ ((days - leaveDetailsDto.getNoOfDays())
//																- lopLeaveDetails.getNoOfDays());
//												if (bal >= 0) {
//													lopLeaveDetails.setStatus(false);
//													lopLeaveDetailsRepository.save(lopLeaveDetails);
//
//													employeeDetail.get().setBalance(bal);
//												} else {
//													lopLeaveDetails.setNoOfDays(leaveDetailsDto.getNoOfDays()
//															- lopLeaveDetails.getNoOfDays());
//													lopLeaveDetailsRepository.save(lopLeaveDetails);
//												}
												leaveDetails.setNoOfDays(leaveDetailsDto.getNoOfDays());
												LeaveTransactionHistory leaveTransactionHistoryOp = new LeaveTransactionHistory();
												leaveTransactionHistoryOp.setEmployeeId(employeeDetail.get().getId());
												leaveTransactionHistoryOp.setLeaveDetailsId(leaveDetails.getId());
												leaveTransactionHistoryOp.setNoOfDays(leaveDetailsDto.getNoOfDays());
												leaveTransactionHistoryOp.setCreatedDate(LocalDate.now());
												leaveTransactionHistoryOp.setCreatedBy(employeeDetail.get().getId());
												leaveTransactionHistoryOp.setLeaveStatus("Open");
												leaveTransactionHistoryRepository.save(leaveTransactionHistoryOp);
											} else {
												leaveDetails.setNoOfDays(leaveDetailsDto.getNoOfDays());
												bal = employeeDetail.get().getBalance()
														+ (days - leaveDetailsDto.getNoOfDays());
												employeeDetail.get().setBalance(bal);

												LeaveTransactionHistory leaveTransactionHistoryOp = new LeaveTransactionHistory();
												leaveTransactionHistoryOp.setEmployeeId(employeeDetail.get().getId());
												leaveTransactionHistoryOp.setLeaveDetailsId(leaveDetails.getId());
												leaveTransactionHistoryOp.setNoOfDays(leaveDetailsDto.getNoOfDays());
												leaveTransactionHistoryOp.setCreatedDate(LocalDate.now());
												leaveTransactionHistoryOp.setCreatedBy(employeeDetail.get().getId());
												leaveTransactionHistoryOp.setLeaveStatus("Open");
												leaveTransactionHistoryRepository.save(leaveTransactionHistoryOp);
											}
										}
									}
								} else {
									LeaveTransactionHistory leaveTransactionHistoryOp = new LeaveTransactionHistory();
									leaveTransactionHistoryOp.setEmployeeId(employeeDetail.get().getId());
									leaveTransactionHistoryOp.setLeaveDetailsId(leaveDetails.getId());
									leaveTransactionHistoryOp.setNoOfDays(leaveDetailsDto.getNoOfDays());
									leaveTransactionHistoryOp.setCreatedDate(LocalDate.now());
									leaveTransactionHistoryOp.setCreatedBy(employeeDetail.get().getId());
									leaveTransactionHistoryOp.setLeaveStatus("Open");
									leaveTransactionHistoryRepository.save(leaveTransactionHistoryOp);
								}
							}
						}
					}
				}
				leaveDetails.setReportingPerson(employeeDetail.get().getReportingManager());

				if (leaveDetailsDto.getEmpCode() != null && !leaveDetailsDto.getEmpCode().equals("")) {
					if (employeeDetail.isPresent()) {
						leaveDetails.setEmpCode(employeeDetail.get().getEmployeeCode());
					}
				} else {
					leaveDetails.setEmpCode(leaveDetails.getEmpCode());
				}

				if (!leaveDetailsDto.isStatus()) {
					leaveDetails.setStatus(false);
					LeaveTransactionHistory leaveTransactionHistoryOp = new LeaveTransactionHistory();
					leaveTransactionHistoryOp.setEmployeeId(employeeDetail.get().getId());
					leaveTransactionHistoryOp.setLeaveDetailsId(leaveDetails.getId());
					leaveTransactionHistoryOp.setNoOfDays(Float.parseFloat(str));
					leaveTransactionHistoryOp.setCreatedDate(LocalDate.now());
					leaveTransactionHistoryOp.setCreatedBy(employeeDetail.get().getId());
					leaveTransactionHistoryOp.setLeaveStatus("Cancelled");
					leaveTransactionHistoryRepository.save(leaveTransactionHistoryOp);
//					LOPLeaveDetails lopLeaveDetails = lopLeaveDetailsRepository
//							.findByLeaveDetailsId(leaveDetail.get().getId());
					if (!leaveType.get().getLeaveType().equalsIgnoreCase("LOP")) {
//						lopLeaveDetails.setStatus(false);
//						lopLeaveDetailsRepository.save(lopLeaveDetails);
						employeeDetail.get()
								.setBalance(employeeDetail.get().getBalance() + leaveDetail.get().getNoOfDays());
//						bal = leaveDetail.get().getNoOfDays() - lopLeaveDetails.getNoOfDays();
//
//						if (bal > 0) {
//							employeeDetail.get().setBalance(bal + employeeDetail.get().getBalance());
//						}
					}
				}
				employeeDetailsRepository.save(employeeDetail.get());
				leaveDetailsRepository.save(leaveDetails);
				entity.put("Status", "200");
				entity.put("Message", "LeaveDetails Updated Successfully");
				entity.put("Data", leaveDetails);
			}
		}
		return entity;
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null || srcValue.toString().equals("0") || srcValue.equals(""))
				emptyNames.add(pd.getName());
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public LinkedHashMap<String, Object> getLeaveTypesList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		// List<LeaveDetailsListDto> leaveDetailsListDtos = new ArrayList<>();

		List<LeaveType> leaveTypes = leaveTypeRepository.findAll();

		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", leaveTypes);

		return entity;
	}

	public LinkedHashMap<String, Object> getEmployeeLeaves(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<LeaveDetails> leaveDetailsOp = leaveDetailsRepository.findAll();
		Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository.findById(id);
		List<LeaveDetails> leaveDetails = new ArrayList<>();

		if (employeeDetail.isPresent()) {
			leaveDetailsOp.forEach(leaveDetail -> {
				if (leaveDetail.getEmpCode().equals(employeeDetail.get().getEmployeeCode())) {
					if (leaveDetail.isStatus()) {
						List<String> reportingPerson = new ArrayList<>();
						if (!CollectionUtils.isEmpty(employeeDetail.get().getReportingManager())) {
							for (String ids : employeeDetail.get().getReportingManager()) {
								Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(ids);
								if (employee.isPresent()) {
									reportingPerson
											.add(employee.get().getEmployeeCode() + "-" + employee.get().getName());
								}
							}
						}
						leaveDetail.setReportingPerson(reportingPerson);
//						LOPLeaveDetails lopLeaveDetails = lopLeaveDetailsRepository
//								.findByLeaveDetailsId(leaveDetail.getId());
//						if (lopLeaveDetails != null) {
//							if (lopLeaveDetails.isStatus()) {
//								leaveDetail.setLopDays(lopLeaveDetails.getNoOfDays());
//							}
//						} else {
//							leaveDetail.setLopDays(0);
//						}
						leaveDetails.add(leaveDetail);
					}
				}
			});
		}
		LeaveDetails leave = new LeaveDetails();
		leave.setTotalLeave(employeeDetail.get().getTotalLeave());
		leave.setBalance(employeeDetail.get().getBalance());
		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", leaveDetails);
		entity.put("Leaves", leave);

		return entity;
	}

	public LinkedHashMap<String, Object> getAllEmployeeLeaves(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<LeaveDetails> leaveDetails = leaveDetailsRepository.findAll();
		List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findAll();
		Optional<EmployeeDetails> employeeDetailOp = employeeDetailsRepository.findById(id);
		List<LeaveDetails> leaveDetailsManage = new ArrayList<>();
		if (employeeDetailOp.isPresent()) {
//			String reportingPerson = employeeDetailOp.get().getEmployeeCode()+"-"+employeeDetailOp.get().getName();

//			employeeDetails.forEach(employeeDetail -> {
//				if(!CollectionUtils.isEmpty(employeeDetail.getReportingManager())) {
//				employeeDetail.getReportingManager().forEach(rm -> {
//					if(rm.equals(employeeDetailOp.get().getId())) {
//						leaveDetails.forEach(leaveDetail -> {
//							if(employeeDetail.getEmployeeCode().equals(leaveDetail.getEmpCode())) {
//								leaveDetailsManage.add(leaveDetail);
//							}
//						});
//					}
//				});
//				}
//			});
			List<EmployeeDetails> employees = getEmployeeByReportingPerson(id);
			if (!CollectionUtils.isEmpty(employees)) {
				for (EmployeeDetails employee : employees) {
					for (LeaveDetails leaveDetail : leaveDetails) {
						if (leaveDetail.getEmpCode().equals(employee.getEmployeeCode())) {
							if (leaveDetail.isStatus()) {
								if (leaveDetail.getLeaveStatus().getStatus().equalsIgnoreCase("Open")) {
//									LOPLeaveDetails lopLeaveDetails = lopLeaveDetailsRepository
//											.findByLeaveDetailsId(leaveDetail.getId());
//									if (lopLeaveDetails != null) {
//										if (lopLeaveDetails.isStatus()) {
//											leaveDetail.setLopDays(lopLeaveDetails.getNoOfDays());
//										}
//									} else {
//										leaveDetail.setLopDays(0);
//									}
									leaveDetailsManage.add(leaveDetail);
								}
							}
						}
					}
				}
			}
		}

		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", leaveDetailsManage);

		return entity;
	}

	public LinkedHashMap<String, Object> getAllEmployeeLeavesList(String id) {

		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		/*
		 * List<String> string =new ArrayList<>(); string.add("Team member");
		 * string.add("Team leader");
		 * 
		 * List<EmployeeDetails> employeeDetails =
		 * employeeDetailsRepository.findByRoleIn(string);
		 * 
		 * List<LeaveDetails> leaveDetails = leaveDetailsRepository.findAll();
		 * 
		 * List<LeaveDetails> leaveDetail = new ArrayList<>();
		 * 
		 * employeeDetails.forEach(employeeDetail -> {
		 * leaveDetails.forEach(leaveDetailList -> {
		 * if(employeeDetail.getEmployeeCode().equals(leaveDetailList.getEmpCode())) {
		 * leaveDetail.add(leaveDetailList); } }); });
		 */

		List<LeaveDetails> leaveDetails = leaveDetailsRepository.findAll();
		if (leaveDetails.size() > 0) {
			for (LeaveDetails leaveDetail : leaveDetails) {
				if (leaveDetail.isStatus()) {
					List<String> reportingPerson = new ArrayList<>();
					Optional<EmployeeDetails> emp = employeeDetailsRepository
							.findByEmployeeCode(leaveDetail.getEmpCode());
					if (!CollectionUtils.isEmpty(emp.get().getReportingManager())) {
						for (String ids : emp.get().getReportingManager()) {
							Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(ids);
							if (employee.isPresent()) {
								reportingPerson.add(employee.get().getEmployeeCode() + "-" + employee.get().getName());
							}
						}
					}
					leaveDetail.setReportingPerson(reportingPerson);
//					LOPLeaveDetails lopLeaveDetails = lopLeaveDetailsRepository
//							.findByLeaveDetailsId(leaveDetail.getId());
//					if (lopLeaveDetails != null) {
//						if (lopLeaveDetails.isStatus()) {
//							leaveDetail.setLopDays(lopLeaveDetails.getNoOfDays());
//						}
//					} else {
//						leaveDetail.setLopDays(0);
//					}
				}
			}
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", leaveDetails);
		} else {

			entity.put("Status", "400");
			entity.put("Message", "No Data is present");

		}

		return entity;
	}

	public LinkedHashMap<String, Object> getLeaveApplicationById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<LeaveDetails> leaveDetail = leaveDetailsRepository.findById(id);
		if (leaveDetail.isPresent()) {
			List<String> reportingPerson = new ArrayList<>();
			Optional<EmployeeDetails> employeeOP = employeeDetailsRepository
					.findByEmployeeCode(leaveDetail.get().getEmpCode());
			if (!CollectionUtils.isEmpty(employeeOP.get().getReportingManager())) {
				for (String rp : employeeOP.get().getReportingManager()) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(rp);
					if (employee.isPresent()) {
						reportingPerson.add(employee.get().getEmployeeCode() + "-" + employee.get().getName());
					}
				}
			}
			leaveDetail.get().setReportingPerson(reportingPerson);
//			LOPLeaveDetails lopLeaveDetails = lopLeaveDetailsRepository.findByLeaveDetailsId(leaveDetail.get().getId());
//			if (lopLeaveDetails != null) {
//				if (lopLeaveDetails.isStatus()) {
//					leaveDetail.get().setLopDays(lopLeaveDetails.getNoOfDays());
//				}
//			} else {
//				leaveDetail.get().setLopDays(0);
//			}
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", leaveDetail.get());
		}

		return entity;
	}

	public List<EmployeeDetails> getEmployeeByReportingPerson(String id) {
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		List<EmployeeDetails> empList = new ArrayList<>();
		if (employee.isPresent()) {
			List<EmployeeDetails> employees = employeeDetailsRepository.findAll();
			for (EmployeeDetails emp : employees) {
				if (!CollectionUtils.isEmpty(emp.getReportingManager())) {
					if (emp.getReportingManager().get(emp.getReportingManager().size() - 1).equals(id)) {
						empList.add(emp);
					}
				}
			}
		}
		return empList;
	}

	public LinkedHashMap<String, Object> getLeaveStatusList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<LeaveStatus> leaveStatus = leaveStatusRepository.findAll();

		entity.put("Status", "200");
		entity.put("Message", "Data is present");
		entity.put("Data", leaveStatus);

		return entity;
	}

	/*
	 * public LinkedHashMap<String, Object> getEmployeesByEmployeeName(String
	 * employeeName) { LinkedHashMap<String, Object> entity = new
	 * LinkedHashMap<String, Object>();
	 * 
	 * List<LeaveDetailsListDto> leaveDetailsListDtos = new ArrayList<>();
	 * 
	 * List<LeaveDetails> leaveDetails =
	 * leaveDetailsRepository.findByEmployeeName(employeeName);
	 * 
	 * for (LeaveDetails leaveDetail : leaveDetails) { LeaveDetailsListDto
	 * leaveDetailListDto = new LeaveDetailsListDto();
	 * 
	 * leaveDetailListDto.setEmployeeName(leaveDetail.getEmployeeName());
	 * leaveDetailListDto.setDepartment(leaveDetail.getDepartment());
	 * leaveDetailListDto.setTotalLeave(leaveDetail.getTotalLeave());
	 * leaveDetailListDto.setRemainingLeave(leaveDetail.getRemainingLeave());
	 * leaveDetailListDto.setBalanceCasualLeave(leaveDetail.getBalanceCasualLeave())
	 * ;
	 * leaveDetailListDto.setBookedCasualLeave(leaveDetail.getBookedCasualLeave());
	 * leaveDetailListDto.setBalanceSickLeave(leaveDetail.getBalanceSickLeave());
	 * leaveDetailListDto.setBookedSickLeave(leaveDetail.getBookedSickLeave());
	 * leaveDetailsListDtos.add(leaveDetailListDto);
	 * 
	 * } entity.put("Status", "200"); entity.put("Message", "Data is present");
	 * entity.put("Data", leaveDetailsListDtos);
	 * 
	 * return entity; }
	 */

	/*
	 * public LinkedHashMap<String, Object> getEmployeesByDepartment(String
	 * department) { LinkedHashMap<String, Object> entity = new
	 * LinkedHashMap<String, Object>();
	 * 
	 * List<LeaveDetailsListDto> leaveDetailsListDtos = new ArrayList<>();
	 * 
	 * List<LeaveDetails> leaveDetails =
	 * leaveDetailsRepository.findByDepartment(department);
	 * 
	 * for (LeaveDetails leaveDetail : leaveDetails) { LeaveDetailsListDto
	 * leaveDetailListDto = new LeaveDetailsListDto();
	 * 
	 * leaveDetailListDto.setEmployeeName(leaveDetail.getEmployeeName());
	 * leaveDetailListDto.setDepartment(leaveDetail.getDepartment());
	 * leaveDetailListDto.setTotalLeave(leaveDetail.getTotalLeave());
	 * leaveDetailListDto.setRemainingLeave(leaveDetail.getRemainingLeave());
	 * leaveDetailListDto.setBalanceCasualLeave(leaveDetail.getBalanceCasualLeave())
	 * ;
	 * leaveDetailListDto.setBookedCasualLeave(leaveDetail.getBookedCasualLeave());
	 * leaveDetailListDto.setBalanceSickLeave(leaveDetail.getBalanceSickLeave());
	 * leaveDetailListDto.setBookedSickLeave(leaveDetail.getBookedSickLeave());
	 * leaveDetailsListDtos.add(leaveDetailListDto);
	 * 
	 * } entity.put("Status", "200"); entity.put("Message", "Data is present");
	 * entity.put("Data", leaveDetailsListDtos);
	 * 
	 * return entity; }
	 */
	/*
	 * public LinkedHashMap<String, Object> getEmployeesByRemainingLeave(int
	 * remainingLeave) { LinkedHashMap<String, Object> entity = new
	 * LinkedHashMap<String, Object>();
	 * 
	 * List<LeaveDetailsListDto> leaveDetailsListDtos = new ArrayList<>();
	 * 
	 * List<LeaveDetails> leaveDetails =
	 * leaveDetailsRepository.findByRemainingLeave(remainingLeave);
	 * 
	 * for (LeaveDetails leaveDetail : leaveDetails) { LeaveDetailsListDto
	 * leaveDetailListDto = new LeaveDetailsListDto();
	 * 
	 * leaveDetailListDto.setEmployeeName(leaveDetail.getEmployeeName());
	 * leaveDetailListDto.setDepartment(leaveDetail.getDepartment());
	 * leaveDetailListDto.setTotalLeave(leaveDetail.getTotalLeave());
	 * leaveDetailListDto.setRemainingLeave(leaveDetail.getRemainingLeave());
	 * leaveDetailListDto.setBalanceCasualLeave(leaveDetail.getBalanceCasualLeave())
	 * ;
	 * leaveDetailListDto.setBookedCasualLeave(leaveDetail.getBookedCasualLeave());
	 * leaveDetailListDto.setBalanceSickLeave(leaveDetail.getBalanceSickLeave());
	 * leaveDetailListDto.setBookedSickLeave(leaveDetail.getBookedSickLeave());
	 * leaveDetailsListDtos.add(leaveDetailListDto);
	 * 
	 * } entity.put("Status", "200"); entity.put("Message", "Data is present");
	 * entity.put("Data", leaveDetailsListDtos);
	 * 
	 * return entity; }
	 */

	public LinkedHashMap<String, Object> getEmployeeById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<LeaveDetails> leaveDetails = leaveDetailsRepository.findById(id);
		if (leaveDetails.isPresent()) {

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", leaveDetails);
		} else {
			entity.put("Status", "405");
			entity.put("Message", "Data is not present");

		}

		return entity;
	}

	public LinkedHashMap<String, Object> getLeaveDetailsList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<LeaveDetailsListDto> leaveDetailsListDtos = new ArrayList<>();

		List<LeaveDetails> leaveDetails = leaveDetailsRepository.findAll();

		if (leaveDetails.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", leaveDetails);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getLeaveStatus() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		List<LeaveStatus> leaveStatus = leaveStatusRepository.findAll();
		List<LeaveStatus> statusList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(leaveStatus)) {
			for (LeaveStatus status : leaveStatus) {
				if (status.getStatus().equalsIgnoreCase("Approved")
						|| status.getStatus().equalsIgnoreCase("Rejected")) {
					statusList.add(status);
				}
			}
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", statusList);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getLeaveDetailsBetweenDates(LocalDate fromDate, LocalDate toDate, String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		List<LeaveDetails> leaveDetails = null;
		if ((fromDate != null && !StringUtils.isEmpty(fromDate)) && (toDate != null && !StringUtils.isEmpty(toDate))) {
			leaveDetails = leaveDetailsRepository.findByFromDateBetween(fromDate.minusDays(1), toDate.plusDays(1));
		} else {
			leaveDetails = leaveDetailsRepository.findAll();
		}
		List<EmployeeDetails> employees = new ArrayList<>();
		Optional<EmployeeDetails> employeeDetailOp = employeeDetailsRepository.findById(id);
		if (employeeDetailOp.get().getRole().equals(GeneralConstants.ADMIN)) {
			employees = employeeDetailsRepository.findAll();
		} else if (employeeDetailOp.get().getRole().equals(GeneralConstants.BUSINESS_UNIT_HEAD)) {
			employees = getEmployeesByBusinessUnitHead(id);
		} else if (employeeDetailOp.get().getRole().equals(GeneralConstants.DEPARTMENT_HEAD)) {
			employees = getEmployeesByDepartmentHead(id);
		} else if (employeeDetailOp.get().getRole().equals(GeneralConstants.MANAGER)) {
			employees = getEmployeeByReportingPerson(id);
		}
		List<LeaveDetails> leaveDetailsManage = new ArrayList<>();
		if (!CollectionUtils.isEmpty(leaveDetails)) {
			if (!CollectionUtils.isEmpty(employees)) {
				for (EmployeeDetails employee : employees) {
					for (LeaveDetails leaveDetail : leaveDetails) {
						if (leaveDetail.getEmpCode().equals(employee.getEmployeeCode())) {
							if (leaveDetail.isStatus()) {
								leaveDetailsManage.add(leaveDetail);
							}
						}
//						LOPLeaveDetails lopLeaveDetails = lopLeaveDetailsRepository
//								.findByLeaveDetailsId(leaveDetail.getId());
//						if (lopLeaveDetails != null) {
//							if (lopLeaveDetails.isStatus()) {
//								leaveDetail.setLopDays(lopLeaveDetails.getNoOfDays());
//							}
//						} else {
//							leaveDetail.setLopDays(0);
//						}
					}
				}
			}
			entity.put("status", 200);
			entity.put("message", "Data is Present");
			entity.put("data", leaveDetailsManage);
		} else {
			entity.put("status", 400);
			entity.put("message", "No data found");
		}
		return entity;
	}
	
	public List<EmployeeDetails> getEmployeesByBusinessUnitHead(String id) {
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		List<EmployeeDetails> employeeDetails = new ArrayList<>();
		if (employee.isPresent()) {
			List<BusinessUnit> businessUnits = businessUnitRepository.findByBusinessUnitHead(id);
			if (!CollectionUtils.isEmpty(businessUnits)) {
				for (BusinessUnit businessUnit : businessUnits) {
					if (businessUnit.getBusinessUnitHead() != null && !businessUnit.getBusinessUnitHead().isEmpty()) {
					List<EmployeeDetails> employeeDetail = employeeDetailsRepository.findByBusinessUnitId(businessUnit.getId());
					if (!CollectionUtils.isEmpty(employeeDetail)) {
						employeeDetails.addAll(employeeDetail);
					}
				}
				}
			}
		}
		return employeeDetails;
	}
	
	public List<EmployeeDetails> getEmployeesByDepartmentHead(String id) {
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		List<EmployeeDetails> employeeDetails = new ArrayList<>();
		if (employee.isPresent()) {
			List<MasterDepartment> masterDepartments = masterDepartmentRepository.findByDepartmentHead(id);
			if (!CollectionUtils.isEmpty(masterDepartments)) {
				for (MasterDepartment masterDepartment : masterDepartments) {
					List<EmployeeDetails> employeeDetail = employeeDetailsRepository.findByMasterDepartmentId(masterDepartment.getId());
					if (!CollectionUtils.isEmpty(employeeDetail)) {
						employeeDetails.addAll(employeeDetail);
					}
				}
			}
		}
		return employeeDetails;
	}

	@Override
	public LinkedHashMap<String, Object> getEmployeeLeavesByManager(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		List<LeaveDetails> leaveDetails = leaveDetailsRepository.findAll();
		Optional<EmployeeDetails> employeeDetailOp = employeeDetailsRepository.findById(id);
		List<LeaveDetails> leaveDetailsManage = new ArrayList<>();
		if (!CollectionUtils.isEmpty(leaveDetails)) {
			List<EmployeeDetails> employees = getEmployeeByReportingPerson(id);
			if (!CollectionUtils.isEmpty(employees)) {
				for (EmployeeDetails employee : employees) {
					for (LeaveDetails leaveDetail : leaveDetails) {
						if (leaveDetail.getEmpCode().equals(employee.getEmployeeCode())) {
							if (leaveDetail.isStatus()) {
								leaveDetailsManage.add(leaveDetail);
							}
						}
					}
				}
			}
			if (!CollectionUtils.isEmpty(leaveDetailsManage)) {
				entity.put("status", 200);
				entity.put("message", "Data is Present");
				entity.put("data", leaveDetailsManage);
			} else {
				entity.put("status", 400);
				entity.put("message", "No data found");
			}
		} else {
			entity.put("status", 400);
			entity.put("message", "No data found");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getLopLeaveDetailsForEmployee(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository.findById(id);
		if (employeeDetail.isPresent()) {
			List<LOPLeaveDetails> lopLeaveDetails = lopLeaveDetailsRepository.findByEmployeeAndStatus(id, true);
			if (!CollectionUtils.isEmpty(lopLeaveDetails)) {
				for (LOPLeaveDetails lopLeaveDetail : lopLeaveDetails) {
					if (lopLeaveDetail.getEmployee() != null && !lopLeaveDetail.getEmployee().isEmpty()) {
						Optional<EmployeeDetails> employee = employeeDetailsRepository
								.findById(lopLeaveDetail.getEmployee());
						if (employee.isPresent()) {
							lopLeaveDetail
									.setEmployee(employee.get().getEmployeeCode() + "-" + employee.get().getName());
						}
					}
				}
				entity.put("status", 200);
				entity.put("message", "Data is Present");
				entity.put("data", lopLeaveDetails);
			} else {
				entity.put("status", 400);
				entity.put("message", "No data found");
			}
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getLopDetailsForAllEmployees(LocalDate fromDate, LocalDate todate) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		List<LOPDaysDto> lopDaysList = new ArrayList<>();
		List<LeaveDetails> leaveFrom = leaveDetailsRepository.findByFromDateBetween(fromDate.minusDays(1),
				todate.plusDays(1));
		List<LeaveDetails> leaveTo = leaveDetailsRepository.findByToDateBetween(fromDate.minusDays(1),
				todate.plusDays(1));
		if (!CollectionUtils.isEmpty(leaveFrom) && !CollectionUtils.isEmpty(leaveTo)) {
			List<LeaveDetails> leaveDetails = new ArrayList<>();
			for (LeaveDetails leaveDetail : leaveFrom) {
				for (LeaveDetails leave : leaveTo) {
					if (leaveDetail.getId().equals(leave.getId())) {
						leaveDetails.add(leaveDetail);
					}
				}
			}
			List<EmployeeDetails> employees = employeeDetailsRepository.findAll();
			for (EmployeeDetails employee : employees) {
				float days = 0;
				for (LeaveDetails leaveDetail : leaveDetails) {
					if (employee.getEmployeeCode().equals(leaveDetail.getEmpCode())) {
						LOPLeaveDetails lopDetails = lopLeaveDetailsRepository
								.findByLeaveDetailsId(leaveDetail.getId());
						if (lopDetails != null && lopDetails.isStatus()) {
							days = days + lopDetails.getNoOfDays();
						}
					}
				}
				if (days > 0) {
					LOPDaysDto lopDaysDto = new LOPDaysDto();
					lopDaysDto.setEmployee(employee.getEmployeeCode() + "-" + employee.getName());
					lopDaysDto.setNoOfDays(days);
					lopDaysList.add(lopDaysDto);
				}
			}
			if (lopDaysList.size() > 0) {
				entity.put("status", 200);
				entity.put("message", "Data is Present");
				entity.put("data", lopDaysList);
			} else {
				entity.put("status", 400);
				entity.put("message", "No Lop Records found");
			}
		} else {
			entity.put("status", 400);
			entity.put("message", "No data found");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getLeaveDetailsForBusinessUnit(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		List<LeaveDetails> leaveDetails = new ArrayList<>();
		if (employee.isPresent()) {
			List<BusinessUnit> businessUnits = businessUnitRepository.findByBusinessUnitHead(id);
			if (!CollectionUtils.isEmpty(businessUnits)) {
				for (BusinessUnit businessUnit : businessUnits) {
					List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findByBusinessUnitId(businessUnit.getId());
					if (!CollectionUtils.isEmpty(employeeDetails)) {
						for (EmployeeDetails emp : employeeDetails) {
							List<LeaveDetails> leaveDetail = leaveDetailsRepository.findByEmpCodeAndStatus(emp.getEmployeeCode(), true);
							leaveDetails.addAll(leaveDetail);
						}
					}
				}
			}
		}
		if (leaveDetails.size() > 0) {
			entity.put("status", 200);
			entity.put("message", "Data is Present");
			entity.put("data", leaveDetails);
		} else {
			entity.put("status", 400);
			entity.put("message", "No data found");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getLeaveDetailsForDepartmentUnit(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<>();
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(id);
		List<LeaveDetails> leaveDetails = new ArrayList<>();
		if (employee.isPresent()) {
			List<MasterDepartment> masterDepartments = masterDepartmentRepository.findByDepartmentHead(id);
			if (!CollectionUtils.isEmpty(masterDepartments)) {
				for (MasterDepartment masterDepartment : masterDepartments) {
					List<EmployeeDetails> employeeDetails = employeeDetailsRepository.findByMasterDepartmentId(masterDepartment.getId());
					if (!CollectionUtils.isEmpty(employeeDetails)) {
						for (EmployeeDetails emp : employeeDetails) {
							List<LeaveDetails> leaveDetail = leaveDetailsRepository.findByEmpCodeAndStatus(emp.getEmployeeCode(), true);
							leaveDetails.addAll(leaveDetail);
						}
					}
				}
			}
		}
		if (leaveDetails.size() > 0) {
			entity.put("status", 200);
			entity.put("message", "Data is Present");
			entity.put("data", leaveDetails);
		} else {
			entity.put("status", 400);
			entity.put("message", "No data found");
		}
		return entity;
	}

	/*
	 * public LinkedHashMap<String, Object> getAllLeaveDetails(LeaveDetailsFilterdto
	 * leaveDetailsFilterdto) { LinkedHashMap<String, Object> entity = new
	 * LinkedHashMap<String, Object>();
	 * 
	 * Query query=new Query();
	 * 
	 * if(leaveDetailsFilterdto.getEmployeeName()!=null &&
	 * !leaveDetailsFilterdto.getEmployeeName().equals("")) {
	 * query.addCriteria(Criteria.where("employeeName").is(leaveDetailsFilterdto.
	 * getEmployeeName())); }
	 * 
	 * if(leaveDetailsFilterdto.getDepartment()!=null &&
	 * !leaveDetailsFilterdto.getDepartment().equals("")) {
	 * query.addCriteria(Criteria.where("department").is(leaveDetailsFilterdto.
	 * getDepartment())); }
	 * 
	 * List<LeaveDetails> leaveDetails = mongoTemplate.find(query,
	 * LeaveDetails.class); entity.put("Status", "200"); entity.put("Message",
	 * "Data is present"); entity.put("Data", leaveDetails);
	 * 
	 * return entity; }
	 */

}
