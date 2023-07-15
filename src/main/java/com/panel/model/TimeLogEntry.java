package com.panel.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import com.panel.dto.TimeLogListDto;

@Document(collection = "timelog")
public class TimeLogEntry {

	@Id
	@Indexed

	private String id;

	private LocalDate date;

	private String employeeId;

	private String employee;

	private TimeLogStatus status;

	private List<TimeLogListDto> timeLogList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public TimeLogStatus getStatus() {
		return status;
	}

	public void setStatus(TimeLogStatus status) {
		this.status = status;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public List<TimeLogListDto> getTimeLogList() {
		return timeLogList;
	}

	public void setTimeLogList(List<TimeLogListDto> timeLogList) {
		this.timeLogList = timeLogList;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

}
