package com.panel.serviceImpl;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.panel.dto.TimeLogListDto;
import com.panel.dto.TimeSheetDto;
import com.panel.dto.TimeSheetLogDto;
import com.panel.dto.TimeSheetMissingReportDto;
import com.panel.dto.TimeSheetPendingReportDto;
import com.panel.dto.TimeSheetResponseDto;
import com.panel.model.BusinessUnit;
import com.panel.model.ClientInformation;
import com.panel.model.EmployeeDetails;
import com.panel.model.MasterDepartment;
import com.panel.model.ProjectInformation;
import com.panel.model.TimeLogEntry;
import com.panel.model.TimeLogStatus;
import com.panel.model.TimeSheet;
import com.panel.model.TimeSheetStatus;
import com.panel.repository.BusinessUnitRepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.MasterDepartmentRepository;
import com.panel.repository.ProjectInformationRepository;
import com.panel.repository.TimeLogEntryRepository;
import com.panel.repository.TimeLogStatusRepository;
import com.panel.repository.TimeSheetRepository;
import com.panel.repository.TimeSheetStatusRepository;
import com.panel.service.TimeSheetService;
import com.panel.support.constant.GeneralConstants;

@Service
public class TimeSheetServiceImpl implements TimeSheetService {

	@Autowired
	TimeSheetRepository timeSheetRepository;

	@Autowired
	ProjectInformationRepository projectInformationRepository;

	@Autowired
	TimeSheetStatusRepository timeSheetStatusRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	TimeLogEntryRepository timeLogEntryRepository;

	@Autowired
	TimeLogStatusRepository timeLogStatusRepository;

	@Autowired
	BusinessUnitRepository businessUnitRepository;

	@Autowired
	MasterDepartmentRepository masterDepartmentRepository;

	@Override
	public LinkedHashMap<String, Object> SaveAndUpdateTimesheet(TimeSheetDto timeSheetDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		if (timeSheetDto.getId() == null) {
			TimeSheet timeSheet = new TimeSheet();

			if (timeSheetDto.getEmpId() != null) {

				Optional<EmployeeDetails> employeeDetail = employeeDetailsRepository.findById(timeSheetDto.getEmpId());
				timeSheet.setEmpId(employeeDetail.get().getId());
				timeSheet.setEmpName(employeeDetail.get().getName());
				timeSheet.setReportingPerson(employeeDetail.get().getReportingManager());

				timeSheet.setDescription(timeSheetDto.getDescription());
				timeSheet.setMonth(timeSheetDto.getMonth());
				timeSheet.setWeek(timeSheetDto.getWeek());
				TimeSheetStatus timeSheetStatus = timeSheetStatusRepository.findByStatus("Open");
				if (timeSheetStatus != null) {
					timeSheet.setStatus(timeSheetStatus);
				}

				DateTimeFormatter In_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter Out_formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
				String date = timeSheetDto.getFromDate();
				LocalDate date1 = LocalDate.parse(Out_formatter.format(In_formatter.parse(date)).toString(),
						Out_formatter);
				timeSheet.setFromDate(date1);

				DateTimeFormatter In_formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter Out_formatter1 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
				String date2 = timeSheetDto.getToDate();
				LocalDate date3 = LocalDate.parse(Out_formatter1.format(In_formatter1.parse(date2)).toString(),
						Out_formatter1);
				timeSheet.setToDate(date3);
				List<TimeSheet> timeSheetList = new ArrayList<>();
				List<TimeSheet> timeSheetFrom = timeSheetRepository.findByFromDateBetween(date1.minusDays(1),
						date3.plusDays(1));
				List<TimeSheet> timeSheetTo = timeSheetRepository.findByToDateBetween(date1.minusDays(1),
						date3.plusDays(1));
				if (!CollectionUtils.isEmpty(timeSheetFrom) && !CollectionUtils.isEmpty(timeSheetTo)) {
					for (TimeSheet time : timeSheetFrom) {
						for (TimeSheet timeSh : timeSheetTo) {
							if (time.getId().equals(timeSh.getId())) {
								if (time.getEmpId().equals(employeeDetail.get().getId())) {
									timeSheetList.add(time);
								}
							}
						}
					}
				}
				if (!CollectionUtils.isEmpty(timeSheetList)) {
					entity.put("Status", "400");
					entity.put("Message", "TimeSheet Already Exist For The Week");
					return entity;
				}
				List<TimeLogEntry> timeLogEntries = new ArrayList<>();
				int hour = 0;
				int mins = 0;
				if (timeSheetDto.getTimeLogs() != null) {
					for (String id : timeSheetDto.getTimeLogs()) {
						Optional<TimeLogEntry> timelog = timeLogEntryRepository.findById(id);
						if (timelog.isPresent()) {
							for (TimeLogListDto timeLogList : timelog.get().getTimeLogList()) {
								if (timeLogList != null) {
									hour = Integer.parseInt(timeLogList.getHours()) + hour;
									mins = Integer.parseInt(timeLogList.getMinutes()) + mins;
								}
							}
							TimeLogStatus timeLogStatus = timeLogStatusRepository.findByStatus("Submitted");
							if (timeLogStatus != null) {
								timelog.get().setStatus(timeLogStatus);
							}
							timeLogEntryRepository.save(timelog.get());
							timeLogEntries.add(timelog.get());
						}
					}
					if (mins >= 60) {
						hour = hour + mins / 60;
						mins = mins % 60;
					}
					String hours = String.valueOf(hour) + ":" + String.valueOf(mins);

					timeSheet.setTotalhours(hours);
					timeSheet.setRequestedDate(LocalDate.now());
					timeSheet.setTimeLogs(timeLogEntries);
					timeSheetRepository.save(timeSheet);
					entity.put("Status", "200");
					entity.put("Message", "Timesheet Saved Successfully");
					entity.put("Data", timeSheet);
				} else {
					entity.put("Status", "400");
					entity.put("Message", "timeLog is empty");
				}
			} else {
				entity.put("Status", "400");
				entity.put("Message", "Employee Code is invalid");
			}
		}

		else {
			Optional<TimeSheet> timeSheetOp = timeSheetRepository.findById(timeSheetDto.getId());
			if (timeSheetOp.isPresent()) {
				TimeSheet timeSheet = timeSheetOp.get();
				List<TimeLogEntry> timeLogEntries = timeSheet.getTimeLogs();
				BeanUtils.copyProperties(timeSheetDto, timeSheet, getNullPropertyNames(timeSheetDto));
				timeSheet.setTimeLogs(timeLogEntries);

				Optional<TimeSheetStatus> timeStatus = timeSheetStatusRepository.findById(timeSheetDto.getStatus());
				if (timeStatus.get().getStatus().equals("Approved")) {
					timeSheet.setStatus(timeStatus.get());
					timeSheet.setRespondedBy(timeSheetDto.getRespondedBy());
					for (TimeLogEntry timeLogEntry : timeSheet.getTimeLogs()) {
						TimeLogStatus timeLogStatus = timeLogStatusRepository.findByStatus("Approved");
						if (timeLogStatus != null) {
							timeLogEntry.setStatus(timeLogStatus);
						}
						timeLogEntryRepository.save(timeLogEntry);
					}
				} else {
					timeSheet.setStatus(timeStatus.get());
					timeSheet.setRespondedBy(timeSheetDto.getRespondedBy());
					for (TimeLogEntry timeLogEntry : timeSheet.getTimeLogs()) {
						TimeLogStatus timeLogStatus = timeLogStatusRepository.findByStatus("Rejected");
						if (timeLogStatus != null) {
							timeLogEntry.setStatus(timeLogStatus);
						}
						timeLogEntryRepository.save(timeLogEntry);
					}
				}

				timeSheet.setFeedback(timeSheetDto.getFeedback());

				timeSheetRepository.save(timeSheet);

				entity.put("Status", "200");
				entity.put("Message", "Timesheet Updated Successfully");
				entity.put("Data", timeSheet);
			} else {
				entity.put("Status", "400");
				entity.put("Message", "Invalid Timesheet Id");
			}
		}

		return entity;
	}

	public String[] getNullPropertyNames(Object source) {

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

	@Override
	public LinkedHashMap<String, Object> getAllTimesheet() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<TimeSheet> timeSheets = timeSheetRepository.findAll();
		if (!CollectionUtils.isEmpty(timeSheets)) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", timeSheets);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getAllEmployeeTimesheet(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		List<TimeSheet> timesheets = timeSheetRepository.findAll();
		Optional<EmployeeDetails> empOptional = employeeDetailsRepository.findById(id);
		List<TimeSheet> timeSheet = new ArrayList<>();

		if (empOptional.isPresent()) {
			for (TimeSheet time : timesheets) {
				if (time.getEmpId().equals(empOptional.get().getId())) {
					time.setEmpName(empOptional.get().getEmployeeCode() + "-" + empOptional.get().getName());
					List<String> reportingPerson = new ArrayList<>();
					if (!CollectionUtils.isEmpty(empOptional.get().getReportingManager())) {
						for (String ids : empOptional.get().getReportingManager()) {
							Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(ids);
							if (employee.isPresent()) {
								reportingPerson.add(employee.get().getEmployeeCode() + "-" + employee.get().getName());
							}
						}
					}
					time.setReportingPerson(reportingPerson);

					if (time.getRespondedBy() != null && !time.getRespondedBy().isEmpty()) {
						Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(time.getRespondedBy());
						if (employee.isPresent()) {
							time.setRespondedBy(employee.get().getEmployeeCode() + "-" + employee.get().getName());
						}
					}
					timeSheet.add(time);
				}
			}
			if (timeSheet.size() > 0) {
				entity.put("Status", "200");
				entity.put("Message", "Data is present");
				entity.put("Data", timeSheet);
			} else {
				entity.put("Status", "400");
				entity.put("Message", "No Data is present");
			}
		} else {
			entity.put("Status", "400");
			entity.put("Message", "Invalid Employee Id");
		}

		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getTimeSheetById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<TimeSheet> timeSheet = timeSheetRepository.findById(id);
		List<String> reportingPerson = new ArrayList<>();
		if (timeSheet.isPresent()) {
			Optional<EmployeeDetails> emp = employeeDetailsRepository.findById(timeSheet.get().getEmpId());
			timeSheet.get().setEmpName(emp.get().getEmployeeCode() + "-" + emp.get().getName());
			if (!CollectionUtils.isEmpty(emp.get().getReportingManager())) {
				for (String ids : emp.get().getReportingManager()) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(ids);
					if (employee.isPresent()) {
						reportingPerson.add(employee.get().getEmployeeCode() + "-" + employee.get().getName());
					}
				}
			}
			timeSheet.get().setReportingPerson(reportingPerson);

			if (timeSheet.get().getRespondedBy() != null && !timeSheet.get().getRespondedBy().isEmpty()) {
				Optional<EmployeeDetails> employee = employeeDetailsRepository
						.findById(timeSheet.get().getRespondedBy());
				if (employee.isPresent()) {
					timeSheet.get().setRespondedBy(employee.get().getEmployeeCode() + "-" + employee.get().getName());
				}
			}

			List<TimeSheetLogDto> timeSheetLogDtos = new ArrayList<>();
			for (TimeLogEntry timeLogEntry : timeSheet.get().getTimeLogs()) {
				for (TimeLogListDto timeLogListDto : timeLogEntry.getTimeLogList()) {
					TimeSheetLogDto timeSheetLogDto = new TimeSheetLogDto();
					timeSheetLogDto.setDate(timeLogEntry.getDate());
					timeSheetLogDto.setActivity(timeLogListDto.getActivity());
					Optional<ProjectInformation> project = projectInformationRepository
							.findById(timeLogListDto.getProjectId());
					timeSheetLogDto.setProject(project.get());
					timeSheetLogDto.setProjectId(timeLogListDto.getProjectId());
					timeSheetLogDto.setWorkdescription(timeLogListDto.getWorkdescription());
					timeSheetLogDto.setHours(timeLogListDto.getHours());
					timeSheetLogDto.setMinutes(timeLogListDto.getMinutes());
					timeSheetLogDtos.add(timeSheetLogDto);
				}
			}
			timeSheetLogDtos = timeSheetLogDtos.stream().sorted(Comparator.comparing(TimeSheetLogDto::getDate))
					.collect(Collectors.toList());
			timeSheet.get().setTimeSheetLog(timeSheetLogDtos);

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", timeSheet);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getTimeSheetApproval(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<TimeSheet> timesheetList = timeSheetRepository.findAll();
		Optional<EmployeeDetails> empOptional = employeeDetailsRepository.findById(id);
		List<TimeSheet> timeSheets = new ArrayList<>();
		if (!CollectionUtils.isEmpty(timesheetList)) {
//			String reportingPerson = empOptional.get().getEmployeeCode() + "-" + empOptional.get().getName();
			List<EmployeeDetails> employees = getEmployeeByReportingPerson(id);
			if (!CollectionUtils.isEmpty(employees)) {
				for (EmployeeDetails employee : employees) {
					for (TimeSheet timeSheet : timesheetList) {
						if (employee.getId().equals(timeSheet.getEmpId())) {
							timeSheet.setEmpName(employee.getEmployeeCode() + "-" + employee.getName());
							if (timeSheet.getStatus().getStatus().equalsIgnoreCase("Open")) {
								timeSheets.add(timeSheet);
							}
						}
					}
				}
			}
		}
		if (timeSheets.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", timeSheets);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
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

	@Override
	public LinkedHashMap<String, Object> getClientByProject(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<ProjectInformation> project = projectInformationRepository.findById(id);
		if (project.isPresent()) {
			ClientInformation clientInformation = new ClientInformation();
			clientInformation = project.get().getClient();

			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", clientInformation);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getAllTimeSheetStatus() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		List<TimeSheetStatus> status = timeSheetStatusRepository.findAll();
		if (status.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", status);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getTimeSheetStatus() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		List<TimeSheetStatus> timeStatus = timeSheetStatusRepository.findAll();
		List<TimeSheetStatus> statusList = new ArrayList<>();
		if (!CollectionUtils.isEmpty(timeStatus)) {
			for (TimeSheetStatus status : timeStatus) {
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
	public LinkedHashMap<String, Object> reSubmitTimeSheet(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<TimeSheet> timeSheetOp = timeSheetRepository.findById(id);
		if (timeSheetOp.isPresent()) {
			if (timeSheetOp.get().getStatus().getStatus().equalsIgnoreCase("Approved")) {
				entity.put("Status", "400");
				entity.put("Message", "TimeSheet Already Approved");
				return entity;
			} else if (timeSheetOp.get().getStatus().getStatus().equalsIgnoreCase("Rejected")) {
				TimeSheetResponseDto timeSheetResponseDto = new TimeSheetResponseDto();
				if (!CollectionUtils.isEmpty(timeSheetOp.get().getTimeLogs())) {
					for (TimeLogEntry timeLogEntry : timeSheetOp.get().getTimeLogs()) {
						TimeLogStatus timeLogStatus = timeLogStatusRepository.findByStatus("Submitted");
						timeLogEntry.setStatus(timeLogStatus);
						timeLogEntryRepository.save(timeLogEntry);
					}

					TimeSheetStatus timeSheetStatus = timeSheetStatusRepository.findByStatus("Open");
					timeSheetOp.get().setStatus(timeSheetStatus);
					timeSheetRepository.save(timeSheetOp.get());

					BeanUtils.copyProperties(timeSheetOp.get(), timeSheetResponseDto);

					entity.put("Status", "200");
					entity.put("Message", "TimeSheet Re-Submitted");
					entity.put("Data", timeSheetResponseDto);
				}
			}
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getTimeSheetBetweenDatesByEmployee(String id, String month) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<EmployeeDetails> employeeOp = employeeDetailsRepository.findById(id);
		List<TimeSheet> timeSheets = null;
		if (employeeOp.isPresent()) {
			if (month != null && !month.isEmpty()) {
				timeSheets = timeSheetRepository.findByEmpIdAndMonth(id, month);
				timeSheets = timeSheets.stream().filter(t -> t.getEmpId().equals(id)).collect(Collectors.toList());
			} else {
				timeSheets = timeSheetRepository.findByEmpId(id);
			}

			if (CollectionUtils.isEmpty(timeSheets)) {
				return null;
			}
			List<TimeSheetResponseDto> timeSheetResponse = new ArrayList<>();
			for (TimeSheet time : timeSheets) {
				if (time.getRespondedBy() != null && !time.getRespondedBy().isEmpty()) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(time.getRespondedBy());
					if (employee.isPresent()) {
						time.setRespondedBy(employee.get().getEmployeeCode() + "-" + employee.get().getName());
					}
				}
				TimeSheetResponseDto timeSheetResponseDto = new TimeSheetResponseDto();
				BeanUtils.copyProperties(time, timeSheetResponseDto);
				timeSheetResponse.add(timeSheetResponseDto);
			}

			entity.put("Status", "200");
			entity.put("Message", "TimeSheet is present");
			entity.put("Data", timeSheetResponse);
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getEmployeeMissingTimeSheetWeek(String id, LocalDate fromDate,
			LocalDate toDate) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<EmployeeDetails> employeeOp = employeeDetailsRepository.findById(id);
		if (employeeOp.isPresent()) {
			List<TimeSheetMissingReportDto> timeSheetMissingReports = new ArrayList<>();
			List<EmployeeDetails> employees = getEmployeesByRole(id);
			if (!CollectionUtils.isEmpty(employees)) {
				for (EmployeeDetails employeeDetail : employees) {
					TimeSheetMissingReportDto timeSheetMissingReport = new TimeSheetMissingReportDto();
					timeSheetMissingReport.setEmployeeId(employeeDetail.getEmployeeCode());
					timeSheetMissingReport.setEmployeeName(employeeDetail.getName());
					timeSheetMissingReport.setBusinessUnit(employeeDetail.getBusinessUnit());
					timeSheetMissingReport.setMasterDepartment(employeeDetail.getMasterDepartment());
					List<String> timeWeeks = new ArrayList<>();
					List<String> weeks = getWeeksForMonths(fromDate, toDate);
					if (!CollectionUtils.isEmpty(weeks)) {
						for (String week : weeks) {
							TimeSheet timeSheet = timeSheetRepository.findByWeek(week);
							if (timeSheet == null) {
								timeWeeks.add(week);
							}
						}
						timeSheetMissingReport.setTimeSheets(timeWeeks);
						timeSheetMissingReport.setWeek(timeWeeks.size());

						timeSheetMissingReports.add(timeSheetMissingReport);
					}
				}
				entity.put("Status", "200");
				entity.put("Message", "TimeSheet is present");
				entity.put("Data", timeSheetMissingReports);
			}
		}
		return entity;
	}

	@Override
	public List<String> getWeeksForMonths(LocalDate fromDate, LocalDate toDate) {
		List<String> weeks = new ArrayList<>();
		LocalDate fromd = null;
		LocalDate tod = null;
		if (fromDate == null || fromDate.toString().isEmpty() && toDate == null) {
			fromd = LocalDate.now().withDayOfMonth(1);
			tod = LocalDate.now();
		} else if (fromDate != null && !fromDate.toString().isEmpty() && toDate == null) {
			fromd = fromDate;
			tod = LocalDate.now();
		} else {
			fromd = fromDate;
			tod = toDate;
		}
		List<LocalDate> dateList = new ArrayList<>();
		for (LocalDate d = fromd; !d.isAfter(tod); d = d.plusDays(1)) {
			if (d.getDayOfMonth() == 1) {
				dateList.add(d);
			}
		}
		for (LocalDate date1 : dateList) {
			List<LocalDate> dates = new ArrayList<>();
			if (fromDate == null || fromDate.toString().isEmpty() && toDate == null) {
				for (LocalDate d = date1; !d.isAfter(LocalDate.now()); d = d.plusDays(1)) {
					dates.add(d);
				}
			} else if (fromDate != null && !fromDate.toString().isEmpty() && toDate == null) {
				if (date1.getMonth().toString().equals(LocalDate.now().getMonth().toString())) {
					for (LocalDate d = date1; !d.isAfter(LocalDate.now()); d = d.plusDays(1)) {
						dates.add(d);
					}
				} else {
					LocalDate to = date1.withDayOfMonth(date1.getMonth().length(date1.isLeapYear()));
					for (LocalDate d = date1; !d.isAfter(to); d = d.plusDays(1)) {
						dates.add(d);
					}
				}
			} else {
				LocalDate to = date1.withDayOfMonth(date1.getMonth().length(date1.isLeapYear()));
				for (LocalDate d = date1; !d.isAfter(to); d = d.plusDays(1)) {
					dates.add(d);
				}
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate sunday = null;
			LocalDate saturday = null;
			LocalDate previous = null;
			LocalDate fin = null;
			boolean status = true;
			int k = 1;
			for (LocalDate date : dates) {
				if (!date.getDayOfWeek().equals(DayOfWeek.SUNDAY) && !date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
					if (status == true) {
						previous = date;
						status = false;
					}
				}
				if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
					date.format(formatter);
					sunday = date;
				} else if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
					saturday = date;
					if ((sunday == null || sunday.toString().isEmpty())
							&& (previous != null && !previous.toString().isEmpty())) {
						weeks.add("Week " + k + " (" + previous.format(formatter) + " to " + saturday.format(formatter)
								+ ")");
						previous = null;
						status = true;
						k++;
					} else if (sunday == null || sunday.toString().isEmpty()) {
						weeks.add("Week " + k + " (" + date.format(formatter) + " to " + saturday.format(formatter)
								+ ")");
						previous = null;
						status = true;
						k++;
					} else {
						weeks.add("Week " + k + " (" + sunday.format(formatter) + " to " + saturday.format(formatter)
								+ ")");
						k++;
					}
				}
				saturday = null;
				fin = date;
			}
			if (previous != null && !previous.toString().isEmpty()) {
				if (sunday != null && !sunday.toString().isEmpty()) {
					if (fromDate == null && toDate == null) {
						if (LocalDate.now().lengthOfMonth() == LocalDate.now().getDayOfMonth()) {
							weeks.add("Week " + k + " (" + sunday.format(formatter) + " to " + fin.format(formatter)
									+ ")");
							k++;
						}
					} else {
						weeks.add("Week " + k + " (" + sunday.format(formatter) + " to " + fin.format(formatter) + ")");
						k++;
					}
				} else {
					weeks.add("Week " + k + " (" + previous.format(formatter) + " to " + fin.format(formatter) + ")");
					k++;
				}
			}
		}
		return weeks;
	}

	@Override
	public LinkedHashMap<String, Object> getTimeSheetPendingApproval(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<EmployeeDetails> employeeOp = employeeDetailsRepository.findById(id);
		if (employeeOp.isPresent()) {
			TimeSheetStatus timeSheetStatus = timeSheetStatusRepository.findByStatus("Open");
			List<EmployeeDetails> employees = getEmployeesByRole(id);
			List<TimeSheetPendingReportDto> timeSheetPendingReports = new ArrayList<>();
			if (!CollectionUtils.isEmpty(employees)) {
				for (EmployeeDetails employeeDetails : employees) {
					List<TimeSheet> timeSheets = timeSheetRepository.findByEmpId(employeeDetails.getId());
					List<String> weeks = new ArrayList<>();
					if (!CollectionUtils.isEmpty(timeSheets)) {
						TimeSheetPendingReportDto timeSheetPendingReport = new TimeSheetPendingReportDto();
						timeSheetPendingReport.setEmployeeId(employeeDetails.getEmployeeCode());
						timeSheetPendingReport.setEmployeeName(employeeDetails.getName());
						if (employeeDetails.getBusinessUnit() != null) {
							timeSheetPendingReport.setBusinessUnit(employeeDetails.getBusinessUnit());
						}
						if (employeeDetails.getMasterDepartment() != null) {
							timeSheetPendingReport.setMasterDepartment(employeeDetails.getMasterDepartment());
						}
						timeSheetPendingReport.setManagerId(employeeOp.get().getEmployeeCode());
						timeSheetPendingReport.setManagerName(employeeOp.get().getName());

						for (TimeSheet timeSheet : timeSheets) {
							if (timeSheet.getStatus().getId().equals(timeSheetStatus.getId())) {
								weeks.add(timeSheet.getWeek());
							}
						}
						if (!CollectionUtils.isEmpty(weeks)) {
							timeSheetPendingReport.setTimeSheets(weeks);
							timeSheetPendingReport.setWeek(weeks.size());

							timeSheetPendingReports.add(timeSheetPendingReport);
						}
					}
				}
				entity.put("Status", "200");
				entity.put("Message", "TimeSheet is present");
				entity.put("Data", timeSheetPendingReports);
			} else {
				return null;
			}
		}
		return entity;
	}

	public List<EmployeeDetails> getEmployeesByRole(String id) {
		Optional<EmployeeDetails> employeeOp = employeeDetailsRepository.findById(id);
		if (employeeOp.isPresent()) {
			List<EmployeeDetails> employees = null;
			if (employeeOp.get().getRole().equals(GeneralConstants.ADMIN)) {
				employees = employeeDetailsRepository.findAll();
				employees = employees.stream().sorted(Comparator.comparing(EmployeeDetails::getEmployeeCode))
						.collect(Collectors.toList());
			} else if (employeeOp.get().getRole().equals(GeneralConstants.BUSINESS_UNIT_HEAD)) {
				List<BusinessUnit> businessUnits = businessUnitRepository.findByBusinessUnitHead(id);
				Set<EmployeeDetails> empSet = new HashSet<>();
				if (!CollectionUtils.isEmpty(businessUnits)) {
					for (BusinessUnit businessUnit : businessUnits) {
						List<EmployeeDetails> employeeDetails = employeeDetailsRepository
								.findByBusinessUnitId(businessUnit.getId());
						if (!CollectionUtils.isEmpty(employeeDetails)) {
							empSet.addAll(employeeDetails);
						}
					}
				}
				employees = empSet.stream().sorted(Comparator.comparing(EmployeeDetails::getEmployeeCode))
						.collect(Collectors.toList());
			} else if (employeeOp.get().getRole().equals(GeneralConstants.DEPARTMENT_HEAD)) {
				List<MasterDepartment> masterDepartments = masterDepartmentRepository.findByDepartmentHead(id);
				if (!CollectionUtils.isEmpty(masterDepartments)) {
					for (MasterDepartment masterDepartment : masterDepartments) {
						List<EmployeeDetails> employeeDetails = employeeDetailsRepository
								.findByMasterDepartmentId(masterDepartment.getId());
						if (!CollectionUtils.isEmpty(employeeDetails)) {
							employees.addAll(employeeDetails);
						}
					}
					employees = employees.stream().sorted(Comparator.comparing(EmployeeDetails::getEmployeeCode))
							.collect(Collectors.toList());
				}
			}
			return employees;
		} else {
			return null;
		}
	}
}