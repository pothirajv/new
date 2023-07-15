package com.panel.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.panel.dto.TimeLogEntryDto;
import com.panel.dto.TimeLogListDto;
import com.panel.dto.TimeLogReportDto;
import com.panel.dto.TimeLogResponseDto;
import com.panel.dto.TimeSheetLogDto;
import com.panel.model.EmployeeDetails;
import com.panel.model.ProjectEmployee;
import com.panel.model.ProjectInformation;
import com.panel.model.TimeLogEntry;
import com.panel.model.TimeLogStatus;
import com.panel.model.TimeSheet;
import com.panel.model.TimeSheetStatus;
import com.panel.repository.ActivityCodeRepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.ProjectEmployeesRepository;
import com.panel.repository.ProjectInformationRepository;
import com.panel.repository.TimeLogEntryRepository;
import com.panel.repository.TimeLogStatusRepository;
import com.panel.repository.TimeSheetRepository;
import com.panel.repository.TimeSheetStatusRepository;
import com.panel.service.TimeLogEntryServices;

@Service
public class TimeLogEntryServiceImpl implements TimeLogEntryServices {

	@Autowired

	TimeLogEntryRepository timeLogEntryRepository;

	@Autowired

	ProjectInformationRepository projectInformationRepository;

	@Autowired

	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	ActivityCodeRepository activityCodeRepository;

	@Autowired
	ProjectEmployeesRepository projectEmployeesRepository;

	@Autowired
	TimeLogStatusRepository timeLogStatusRepository;

	@Autowired
	TimeSheetRepository timeSheetRepository;

	@Autowired
	TimeSheetStatusRepository timeSheetStatusRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public LinkedHashMap<String, Object> SaveAndUpdate(TimeLogEntryDto timeLogEntryDto) {

		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		if (timeLogEntryDto.getId() == null) {

			TimeLogEntry timeLogEntry1 = new TimeLogEntry();

			DateTimeFormatter In_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter Out_formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			String date = timeLogEntryDto.getDate();
			LocalDate date1 = LocalDate.parse(Out_formatter.format(In_formatter.parse(date)).toString(), Out_formatter);
			timeLogEntry1.setDate(date1);

			List<TimeLogListDto> timeLogs = new ArrayList<>();

			if (timeLogEntryDto.getEmployeeId() != null) {

				Optional<EmployeeDetails> employeeop = employeeDetailsRepository
						.findById(timeLogEntryDto.getEmployeeId());

				if (employeeop.isPresent()) {
					timeLogEntry1.setEmployeeId(employeeop.get().getId());
					timeLogEntry1.setEmployee(employeeop.get().getEmployeeCode());
				}

				List<TimeLogEntry> timeLogDate = timeLogEntryRepository.findByDateBetweenAndEmployeeId(
						timeLogEntry1.getDate().minusDays(1), timeLogEntry1.getDate().plusDays(1),
						employeeop.get().getId());
				if (CollectionUtils.isEmpty(timeLogDate)) {
					long hr = 0;
					long mins = 0;
					for (TimeLogListDto timeLog : timeLogEntryDto.getTimeLogList()) {
						TimeLogListDto timeLogList = new TimeLogListDto();

						if (timeLog.getProjectId() != null) {

							Optional<ProjectInformation> projeOptional = projectInformationRepository
									.findById(timeLog.getProjectId());

							if (projeOptional.isPresent()) {

								timeLogList.setProject(projeOptional.get());
								timeLogList.setProjectId(projeOptional.get().getId());
							}

						}

						timeLogList.setActivity(timeLog.getActivity());

						timeLogList.setWorkdescription(timeLog.getWorkdescription());
						timeLogList.setHours(timeLog.getHours());
						timeLogList.setMinutes(timeLog.getMinutes());
						timeLogList.setEffort(timeLog.getEffort());
						timeLogs.add(timeLogList);
						hr = Integer.parseInt(timeLog.getHours()) + hr;
						mins = Integer.parseInt(timeLog.getMinutes()) + mins;
					}

					timeLogEntry1.setTimeLogList(timeLogs);

					if (mins >= 60) {
						hr = hr + mins / 60;
						mins = mins % 60;
					}
					if (timeLogEntryDto.getStatus() != null && !timeLogEntryDto.getStatus().isEmpty()) {
						Optional<TimeLogStatus> timeLogStatus = timeLogStatusRepository
								.findById(timeLogEntryDto.getStatus());
						if (timeLogStatus.isPresent()) {
							timeLogEntry1.setStatus(timeLogStatus.get());
						}
					}

					Optional<TimeSheet> timeSheet = null;

					List<TimeSheet> timeSheets = timeSheetRepository.findByFromDateAndToDate(date1, date1);
					if (!CollectionUtils.isEmpty(timeSheets)) {
						timeSheet = timeSheets.stream().filter(e -> e.getEmpId().equals(employeeop.get().getId()))
								.findFirst();
						if (timeSheet.isPresent()) {
							if (timeSheet.get().getStatus().getStatus().equals("Approved")) {
								entity.put("status", "400");
								entity.put("message",
										"Timelog is not created... Because TimeSheet is already Approved for the given date");
								return entity;
							} else {
								TimeLogStatus timeLogStatus = timeLogStatusRepository.findByStatus("Submitted");
								timeLogEntry1.setStatus(timeLogStatus);
								timeLogEntryRepository.save(timeLogEntry1);
								String[] tot = null;
								long totHr = 0;
								long totmins = 0;
								boolean flag = true;
								timeSheet.get().getTimeLogs().add(timeLogEntry1);
								tot = timeSheet.get().getTotalhours().split(":");
								for (String tm : tot) {
									if (flag == true) {
										totHr = Integer.parseInt(tm);
										flag = false;
									} else {
										totmins = Integer.parseInt(tm);
									}
								}
								totHr = totHr + hr;
								totmins = totmins + mins;
								if (totmins >= 60) {
									totHr = totHr + totmins / 60;
									totmins = totmins % 60;
								}
								timeSheet.get().setTotalhours(String.valueOf(totHr) + ":" + String.valueOf(totmins));
//								TimeSheetStatus timeSheetStatus = timeSheetStatusRepository.findByStatus("Open");
//								timeSheet.get().setStatus(timeSheetStatus);
								timeSheetRepository.save(timeSheet.get());
							}
						} else {
							timeLogEntryRepository.save(timeLogEntry1);
						}
					} else {
						timeLogEntryRepository.save(timeLogEntry1);
					}
					entity.put("status", "200");
					entity.put("message", "Timelog Saved Successfully");
					entity.put("Data", timeLogEntry1);
				} else {
					entity.put("status", "400");
					entity.put("message", "TimeLog already exist for the given date");
				}

			}
		} else {
			Optional<TimeLogEntry> timeOptional = timeLogEntryRepository.findById(timeLogEntryDto.getId());

			if (timeOptional.isPresent()) {

				TimeLogEntry timeEntry = timeOptional.get();

				Optional<EmployeeDetails> empOptional = employeeDetailsRepository
						.findById(timeLogEntryDto.getEmployeeId());
				if (empOptional.isPresent()) {
					timeEntry.setEmployeeId(empOptional.get().getId());
					timeEntry.setEmployee(empOptional.get().getEmployeeCode());
				}

				if (timeLogEntryDto.getDate() != null && !timeLogEntryDto.getDate().equals("")) {
					DateTimeFormatter In_formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					DateTimeFormatter Out_formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					String date = timeLogEntryDto.getDate();
					LocalDate date1 = LocalDate.parse(Out_formatter.format(In_formatter.parse(date)).toString(),
							Out_formatter);
					timeEntry.setDate(date1);
				} else {
					timeEntry.setDate(timeEntry.getDate());
				}

				List<TimeLogListDto> timeLogs = new ArrayList<>();
				if (!CollectionUtils.isEmpty(timeLogEntryDto.getTimeLogList())) {
					for (TimeLogListDto timeLog : timeLogEntryDto.getTimeLogList()) {
						TimeLogListDto timeLogList = new TimeLogListDto();

						if (timeLog.getProjectId() != null) {

							Optional<ProjectInformation> projeOptional = projectInformationRepository
									.findById(timeLog.getProjectId());

							if (projeOptional.isPresent()) {

								timeLogList.setProject(projeOptional.get());
								timeLogList.setProjectId(projeOptional.get().getId());
							}

						}
						timeLogList.setActivityId(timeLog.getActivityId());
						timeLogList.setActivity(timeLog.getActivity());
						timeLogList.setWorkdescription(timeLog.getWorkdescription());
						timeLogList.setHours(timeLog.getHours());
						timeLogList.setMinutes(timeLog.getMinutes());

						timeLogList.setEffort(timeLog.getEffort());
						timeLogs.add(timeLogList);
					}
					timeEntry.setTimeLogList(timeLogs);
				} else {
					timeEntry.setTimeLogList(timeEntry.getTimeLogList());
				}

				if (timeEntry.getStatus().getStatus().equalsIgnoreCase("Open")) {
					timeLogEntryRepository.save(timeEntry);
				} else if (timeEntry.getStatus().getStatus().equalsIgnoreCase("Submitted")) {
					List<TimeSheet> timeSheets = timeSheetRepository.findByEmpId(timeEntry.getEmployeeId());
					if (!CollectionUtils.isEmpty(timeSheets)) {
						for (TimeSheet timeSheet : timeSheets) {
							if (!timeSheet.getStatus().getStatus().equalsIgnoreCase("Approved")) {
								if (!CollectionUtils.isEmpty(timeSheet.getTimeLogs())) {
									for (TimeLogEntry timeLogEntry : timeSheet.getTimeLogs()) {
										if (timeLogEntry.getId().equals(timeEntry.getId())) {
											BeanUtils.copyProperties(timeEntry, timeLogEntry);
											timeLogEntryRepository.save(timeEntry);
											timeSheetRepository.save(timeSheet);
											int hour = 0;
											int mins = 0;
											for (TimeLogEntry logEntry : timeSheet.getTimeLogs()) {
												for (TimeLogListDto logDto : logEntry.getTimeLogList()) {
													hour = Integer.parseInt(logDto.getHours()) + hour;
													mins = Integer.parseInt(logDto.getMinutes()) + mins;
												}
											}
											if (mins >= 60) {
												hour = hour + mins / 60;
												mins = mins % 60;
											}
											String hours = String.valueOf(hour) + ":" + String.valueOf(mins);
											timeSheet.setTotalhours(hours);
											timeSheetRepository.save(timeSheet);
										}
									}
								}
							}
						}
					}
				} else if (timeEntry.getStatus().getStatus().equalsIgnoreCase("Rejected")) {
					List<TimeSheet> timeSheets = timeSheetRepository.findByEmpId(timeEntry.getEmployeeId());
					if (!CollectionUtils.isEmpty(timeSheets)) {
						for (TimeSheet timeSheet : timeSheets) {
							if (!timeSheet.getStatus().getStatus().equalsIgnoreCase("Approved")) {
								if (!CollectionUtils.isEmpty(timeSheet.getTimeLogs())) {
									for (TimeLogEntry timeLogEntry : timeSheet.getTimeLogs()) {
										if (timeLogEntry.getId().equals(timeEntry.getId())) {
											BeanUtils.copyProperties(timeEntry, timeLogEntry);
											TimeLogStatus timeLogStatus = timeLogStatusRepository
													.findByStatus("Submitted");
											timeEntry.setStatus(timeLogStatus);
											timeLogEntryRepository.save(timeEntry);
//											TimeSheetStatus timeSheetStatus = timeSheetStatusRepository
//													.findByStatus("Open");
//											timeSheet.setStatus(timeSheetStatus);
											timeSheetRepository.save(timeSheet);
											int hour = 0;
											int mins = 0;
											for (TimeLogEntry logEntry : timeSheet.getTimeLogs()) {
												for (TimeLogListDto logDto : logEntry.getTimeLogList()) {
													hour = Integer.parseInt(logDto.getHours()) + hour;
													mins = Integer.parseInt(logDto.getMinutes()) + mins;
												}
											}
											if (mins >= 60) {
												hour = hour + mins / 60;
												mins = mins % 60;
											}
											String hours = String.valueOf(hour) + ":" + String.valueOf(mins);
											timeSheet.setTotalhours(hours);
											timeSheetRepository.save(timeSheet);
										}
									}
								}
							}
						}
					}
				}
				entity.put("status", "200");
				entity.put("message", "Timelog Updated Successfully");
				entity.put("Data", timeEntry);

			} else {
				entity.put("status", "400");
				entity.put("message", "No Data is present");
			}

		}

		return entity;

	}

	@Override
	public LinkedHashMap<String, Object> getAllTimeLogEntries() {

		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<TimeLogEntry> timeLogEntries = timeLogEntryRepository.findAll();

		if (timeLogEntries.size() > 0) {

			entity.put("status", "200");
			entity.put("message", "Data is present");
			entity.put("Data", timeLogEntries);
		} else {

			entity.put("status", "400");
			entity.put("message", "No Data is present");

		}

		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getTimeLogEntryById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<TimeLogEntry> timeOptional = timeLogEntryRepository.findById(id);
		if (timeOptional.isPresent()) {
			TimeLogResponseDto timeLogResponseDto = new TimeLogResponseDto();
			BeanUtils.copyProperties(timeOptional.get(), timeLogResponseDto);
			Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(timeOptional.get().getEmployeeId());
			timeLogResponseDto.setEmployee(employee.get().getEmployeeCode());

			entity.put("status", "200");
			entity.put("message", "Data is present");
			entity.put("Data", timeLogResponseDto);
		} else {
			entity.put("status", "400");
			entity.put("message", "No Data is present");

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
	public LinkedHashMap<String, Object> getTimeLogEntryByEmployee(LocalDate fromDate, LocalDate toDate,
			String empCode) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(empCode);
		if (employee.isPresent()) {

			List<TimeLogEntry> timeLogEntries = timeLogEntryRepository
					.findByDateBetweenAndEmployeeId(fromDate.minusDays(1), toDate.plusDays(1), empCode);
			if (timeLogEntries.size() > 0) {
				List<TimeSheetLogDto> timeSheetLogs = new ArrayList<>();
				for (TimeLogEntry timeLogEntry : timeLogEntries) {
					if (!CollectionUtils.isEmpty(timeLogEntry.getTimeLogList())) {
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
							timeSheetLogs.add(timeSheetLogDto);
						}
					}
				}
				timeSheetLogs = timeSheetLogs.stream().sorted(Comparator.comparing(TimeSheetLogDto::getDate))
						.collect(Collectors.toList());
				entity.put("status", "200");
				entity.put("message", "Data is present");
				entity.put("Data", timeLogEntries);
				entity.put("Logs", timeSheetLogs);
			} else {
				entity.put("status", "400");
				entity.put("message", "No Data is present");
			}
		} else {
			entity.put("status", "400");
			entity.put("message", "Invalid Employee Code");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> deleteTimeLogEntryById(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		timeLogEntryRepository.deleteById(id);

		entity.put("status", "200");
		entity.put("message", "Data is Deleted");

		return entity;

	}

	@Override
	public LinkedHashMap<String, Object> getAllEmployeeTimelog(String id) {

		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		List<TimeLogEntry> timeloglist = timeLogEntryRepository.findAll();
		Optional<EmployeeDetails> emptimelog = employeeDetailsRepository.findByEmployeeCode(id);
		List<TimeLogEntry> timeEntries = new ArrayList<>();
		if (emptimelog.isPresent()) {

			for (TimeLogEntry timelog : timeloglist) {

				if (timelog.getEmployeeId().equals(emptimelog.get().getId())) {
					timeEntries.add(timelog);
				}
			}
		}
		if (timeEntries.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", timeEntries);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No data is present");
		}

		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getMonthlyTimeLogEntries(LocalDate fromDate, LocalDate toDate) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		List<TimeLogEntry> timeLogEntries = timeLogEntryRepository.findByDateBetween(fromDate.minusDays(1),
				toDate.plusDays(1));
		if (!CollectionUtils.isEmpty(timeLogEntries)) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", timeLogEntries);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No data is present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getAllTimeLogStatus() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		List<TimeLogStatus> timeLogStatus = timeLogStatusRepository.findAll();
		if (!CollectionUtils.isEmpty(timeLogStatus)) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", timeLogStatus);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No data is present");
		}
		return entity;
	}

	@Override
	public byte[] getTimeLogEntriesByProjectAndMonth(LocalDate fromDate, LocalDate toDate, String projectId) {

		byte[] array = null;
		List<TimeLogEntry> timeLogEntries = timeLogEntryRepository
				.findByDateBetween(fromDate.minusDays(1), toDate.plusDays(1));
		Optional<ProjectInformation> project = projectInformationRepository.findById(projectId);
		String month = fromDate.getMonth().toString();
		if (!CollectionUtils.isEmpty(timeLogEntries)) {
//			List<LocalDate> dates = fromDate.datesUntil(toDate.plusDays(1)).collect(Collectors.toList());
			List<LocalDate> dates = new ArrayList<>();
			for (LocalDate d = fromDate; !d.isAfter(toDate); d = d.plusDays(1)) {
				dates.add(d);
			}
			List<ProjectEmployee> proEmployees = projectEmployeesRepository.findByProjectId(projectId);
			List<TimeLogReportDto> timeLogReport = new ArrayList<>();
			if (!CollectionUtils.isEmpty(proEmployees)) {
				for (ProjectEmployee projectEmployee : proEmployees) {

					long totEmpHr = 0;
					long totEmpMins = 0;
					TimeLogReportDto logReport = new TimeLogReportDto();
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(projectEmployee.getEmployeeId());
					logReport.setEmpCode(employee.get().getEmployeeCode());
					logReport.setEmpName(employee.get().getName());
					logReport.setEmailId(employee.get().getEmailId());
					logReport.setClient(project.get().getClient().getClientName());
					logReport.setProject(project.get().getProjectName());
					LinkedHashMap<LocalDate, String> datas = new LinkedHashMap<>();
					for (LocalDate date : dates) {
						List<TimeLogEntry> logEntries = timeLogEntryRepository.findByDateBetweenAndEmployeeId(
								date.minusDays(1), date.plusDays(1), employee.get().getId());
						if (!CollectionUtils.isEmpty(logEntries)) {
							for (TimeLogEntry logEntry : logEntries) {
//								List<TimeLogListDto> logs = logEntry.getTimeLogList().stream()
//										.filter(e -> e.getProject().getId().equals(projectId)).collect(Collectors.toList());
								List<TimeLogListDto> logs = new ArrayList<>();
								for (TimeLogListDto log : logEntry.getTimeLogList()) {
									if (log.getProject().getId().equals(projectId)) {
										logs.add(log);
									}
								}
								if (!CollectionUtils.isEmpty(logs)) {
									long hr = 0;
									long mins = 0;
									for (TimeLogListDto log : logs) {
										logReport.setJobName(log.getWorkdescription());
										hr = Integer.parseInt(log.getHours()) + hr;
										mins = Integer.parseInt(log.getMinutes()) + mins;
										totEmpHr = Integer.parseInt(log.getHours()) + totEmpHr;
										totEmpMins = Integer.parseInt(log.getMinutes()) + totEmpMins;
									}
									if (mins >= 60) {
										hr = hr + mins / 60;
										mins = mins % 60;
									}
									datas.put(logEntry.getDate(),
											String.format("%02d", hr) + ":" + String.format("%02d", mins));
								} else {
									logReport.setJobName("---");
									datas.put(logEntry.getDate(), "");
								}
							}
						} else {
							logReport.setJobName("---");
							datas.put(date, "");
						}
					}
					if (totEmpMins >= 60) {
						totEmpHr = totEmpHr + totEmpMins / 60;
						totEmpMins = totEmpMins % 60;
					}
					if (totEmpHr > 0 || totEmpMins > 0) {
						logReport.setTotalEmp(String.valueOf(String.format("%02d", totEmpHr)) + ":"
								+ String.valueOf(String.format("%02d", totEmpMins)));
					} else {
						logReport.setTotalEmp("");
					}
					logReport.setData(datas);
					timeLogReport.add(logReport);
				}
			}
			long i = 0;
			long j = 0;
			LinkedHashMap<LocalDate, String> hrs = new LinkedHashMap<>();
			if (!CollectionUtils.isEmpty(timeLogReport)) {
				for (LocalDate date : dates) {
					long totHr = 0;
					long totMins = 0;
					for (TimeLogReportDto logReport : timeLogReport) {
						for (Map.Entry entry : logReport.getData().entrySet()) {
							Object key = entry.getKey();
							Object value = entry.getValue();
							if (date.equals(key)) {
								String totHrs = value.toString();
								if (totHrs != null && !totHrs.isEmpty()) {
									totHr = Integer.parseInt(totHrs.substring(0, 2)) + totHr;
									totMins = Integer.parseInt(totHrs.substring(3)) + totMins;
									i = Integer.parseInt(totHrs.substring(0, 2)) + i;
									j = Integer.parseInt(totHrs.substring(3)) + j;
								}
							}
						}
					}
					if (totMins >= 60) {
						totHr = totHr + totMins / 60;
						totMins = totMins % 60;
					}
					if (totHr > 0 || totMins > 0) {
						hrs.put(date, String.valueOf(String.format("%02d", totHr)) + ":"
								+ String.valueOf(String.format("%02d", totMins)));
					} else {
						hrs.put(date, "");
					}
				}
			}

			if (j >= 60) {
				i = i + j / 60;
				j = j % 60;
			}
			String totTime = "";
			if (i > 0 || j > 0) {
				totTime = String.valueOf(i) + ":" + String.valueOf(j);
			}

			if (!CollectionUtils.isEmpty(timeLogReport) && !hrs.isEmpty()) {
				array = generateMonthlyTimeLogPdf(timeLogReport, hrs, month, project.get().getProjectName(), fromDate,
						toDate, totTime);
			}
		}
		return array;
	}

	public byte[] generateMonthlyTimeLogPdf(List<TimeLogReportDto> timeLogReports,
			LinkedHashMap<LocalDate, String> totHrs, String month, String projectId, LocalDate fromDate,
			LocalDate toDate, String totTime) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (!CollectionUtils.isEmpty(timeLogReports) && !totHrs.isEmpty()) {
//			List<LocalDate> dates = fromDate.datesUntil(toDate.plusDays(1)).collect(Collectors.toList());
			List<LocalDate> dates = new ArrayList<>();
			for (LocalDate d = fromDate; !d.isAfter(toDate); d = d.plusDays(1)) {
				dates.add(d);
			}
			Rectangle envelope = new Rectangle(1400, 1000);

			Document document = new Document(envelope);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-YYYY");

			DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("E");

			try {
				Font SUBFONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD | Font.UNDERLINE);
				Font fontTot = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
				Font fontHr = new Font(Font.FontFamily.TIMES_ROMAN, 8);
				Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
				Paragraph p1 = new Paragraph();
				leaveEmptyLine(p1, 1);
				p1.add(new Paragraph("Time Log Report", SUBFONT));
				p1.setAlignment(Element.ALIGN_CENTER);
				leaveEmptyLine(p1, 1);
				Paragraph p2 = new Paragraph();
				p2.add(new Paragraph(
						"From Date : " + formatter.format(fromDate) + "  " + "To Date : " + formatter.format(toDate)));
				p2.setAlignment(Element.ALIGN_LEFT);
				leaveEmptyLine(p2, 1);

				Paragraph paragraph = new Paragraph();
				leaveEmptyLine(paragraph, 1);

				float[] widths = new float[] {};
				if (dates.size() == 28) {
					float[] width = { (float) 0.3, (float) 0.3, (float) 0.5, (float) 0.3, (float) 0.3, (float) 0.3,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2 };
					widths = width;
				} else if (dates.size() == 29) {
					float[] width = { (float) 0.3, (float) 0.3, (float) 0.5, (float) 0.3, (float) 0.3, (float) 0.3,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2 };
					widths = width;
				} else if (dates.size() == 30) {
					float[] width = { (float) 0.3, (float) 0.3, (float) 0.5, (float) 0.3, (float) 0.3, (float) 0.3,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2 };
					widths = width;
				} else {
					float[] width = { (float) 0.3, (float) 0.3, (float) 0.5, (float) 0.3, (float) 0.3, (float) 0.3,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2,
							(float) 0.2, (float) 0.2, (float) 0.2, (float) 0.2 };
					widths = width;
				}
				PdfPTable table = new PdfPTable(widths);
				table.setWidthPercentage(100);

				table.setHeaderRows(1);
				// Add header details
				PdfPCell cell1 = new PdfPCell(new Phrase("Employee Id", font));
				cell1.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell1);
				PdfPCell cell2 = new PdfPCell(new Phrase("Employee Name", font));
				cell2.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell2);
				PdfPCell cell3 = new PdfPCell(new Phrase("Email Id", font));
				cell3.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell3);
				PdfPCell cell4 = new PdfPCell(new Phrase("Client", font));
				cell4.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell4);
				PdfPCell cell5 = new PdfPCell(new Phrase("Project", font));
				cell5.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell5);
				PdfPCell cell6 = new PdfPCell(new Phrase("Job Name", font));
				cell6.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell6);

				for (LocalDate date : dates) {
					String dt = formatter.format(date);
					String text = date.format(formatter1);
					PdfPCell cell7 = new PdfPCell(new Phrase(dt.substring(0, 2) + "," + text, fontHr));
					cell7.setBackgroundColor(BaseColor.GRAY);
					table.addCell(cell7);
				}
				PdfPCell cell8 = new PdfPCell(new Phrase("Total", fontTot));
				cell8.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell8);

				for (TimeLogReportDto timeLogReport : timeLogReports) {
					table.addCell(new Phrase(timeLogReport.getEmpCode(), font));
					table.addCell(new Phrase(timeLogReport.getEmpName(), font));
					table.addCell(new Phrase(timeLogReport.getEmailId(), font));
					table.addCell(new Phrase(timeLogReport.getClient(), font));
					table.addCell(new Phrase(timeLogReport.getProject(), font));
					table.addCell(new Phrase(timeLogReport.getJobName(), font));

					for (Map.Entry entry : timeLogReport.getData().entrySet()) {
						table.addCell(new Phrase(entry.getValue().toString(), font));
					}
					PdfPCell cell9 = new PdfPCell(new Phrase(timeLogReport.getTotalEmp(), fontTot));
					cell9.setBackgroundColor(BaseColor.GRAY);
					table.addCell(cell9);
				}

				PdfPCell cell = new PdfPCell(new Phrase("Total", fontTot));
				cell.setColspan(6);
				cell.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell);
				for (Map.Entry entry : totHrs.entrySet()) {
					PdfPCell cell10 = new PdfPCell(new Phrase(entry.getValue().toString(), fontTot));
					cell10.setBackgroundColor(BaseColor.GRAY);
					table.addCell(cell10);
				}
				PdfPCell cell11 = new PdfPCell(new Phrase(totTime, fontTot));
				cell11.setBackgroundColor(BaseColor.GRAY);
				table.addCell(cell11);

				PdfWriter.getInstance(document, out);
				document.open();
				document.add(p1);
				document.add(p2);
				document.add(paragraph);
				document.add(table);

				document.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return out.toByteArray();
	}

	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}