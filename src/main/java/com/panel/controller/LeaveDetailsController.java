package com.panel.controller;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.panel.dto.LeaveDetailsDto;
import com.panel.service.LeaveDetailsService;

@RestController
@RequestMapping("/leaveDetails")
@CrossOrigin("*")
public class LeaveDetailsController {

	@Autowired
	LeaveDetailsService leaveDetailsService;

	@PostMapping("/saveUpdateLeave")
	public ResponseEntity<Object> saveAndUpdateLeaveDetails(@RequestBody LeaveDetailsDto leaveDetailsDto) {
		return new ResponseEntity<>(leaveDetailsService.saveAndUpdateLeaveDetails(leaveDetailsDto), HttpStatus.OK);
	}

	/*
	 * @GetMapping("/employeeByName") public ResponseEntity<Object>
	 * getEmployeesByEmployeeName(@RequestParam String employeeName) { return new
	 * ResponseEntity<>(leaveDetailsService.getEmployeesByEmployeeName(employeeName)
	 * ,HttpStatus.OK); }
	 */

	/*
	 * @GetMapping("/employeeByDepartment") public ResponseEntity<Object>
	 * getEmployeesByDepartment(@RequestParam String department) { return new
	 * ResponseEntity<>(leaveDetailsService.getEmployeesByDepartment(department),
	 * HttpStatus.OK); }
	 */

	/*
	 * @GetMapping("/employeeByRemainingLeave") public ResponseEntity<Object>
	 * getEmployeesByRemainingLeave(@RequestParam int remainingLeave ) { return new
	 * ResponseEntity<>(leaveDetailsService.getEmployeesByRemainingLeave(
	 * remainingLeave),HttpStatus.OK); }
	 */

	@GetMapping("/EmployeeById")
	public ResponseEntity<Object> getEmployeeById(@RequestParam String id) {
		return new ResponseEntity<>(leaveDetailsService.getEmployeeById(id), HttpStatus.OK);
	}

	@GetMapping("/leaveTypesList")
	public ResponseEntity<Object> getLeaveTypesList() {
		return new ResponseEntity<>(leaveDetailsService.getLeaveTypesList(), HttpStatus.OK);
	}

	@GetMapping("/leaveStatusList")
	public ResponseEntity<Object> getLeaveStatusList() {
		return new ResponseEntity<>(leaveDetailsService.getLeaveStatusList(), HttpStatus.OK);
	}

	@GetMapping("/leaveList")
	public ResponseEntity<Object> getLeaveDetailsList() {
		return new ResponseEntity<>(leaveDetailsService.getLeaveDetailsList(), HttpStatus.OK);
	}

	@GetMapping("/id")
	public ResponseEntity<Object> getLeaveApplicationById(@RequestParam String id) {
		return new ResponseEntity<>(leaveDetailsService.getLeaveApplicationById(id), HttpStatus.OK);
	}

	@GetMapping("/myleaves")
	public ResponseEntity<Object> getEmployeeLeaves(@RequestParam String id) {
		return new ResponseEntity<>(leaveDetailsService.getEmployeeLeaves(id), HttpStatus.OK);
	}

	@GetMapping("/approval")
	public ResponseEntity<Object> getAllEmployeeLeaves(String id) {
		return new ResponseEntity<>(leaveDetailsService.getAllEmployeeLeaves(id), HttpStatus.OK);
	}

	@GetMapping("/hrlist")
	public ResponseEntity<Object> getAllEmployeeLeavesList(String id) {
		return new ResponseEntity<>(leaveDetailsService.getAllEmployeeLeavesList(id), HttpStatus.OK);
	}

	@GetMapping("/status")
	public ResponseEntity<Object> getLeaveStatus() {
		return new ResponseEntity<>(leaveDetailsService.getLeaveStatus(), HttpStatus.OK);
	}

	@GetMapping("/date")
	public ResponseEntity<Object> getLeaveDetailsBetweenDates(
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
			@RequestParam String id) {
		return new ResponseEntity<>(leaveDetailsService.getLeaveDetailsBetweenDates(fromDate, toDate, id),
				HttpStatus.OK);
	}

	@GetMapping("/manager")
	public ResponseEntity<Object> getEmployeeLeavesByManager(@RequestParam String id) {
		return new ResponseEntity<>(leaveDetailsService.getEmployeeLeavesByManager(id), HttpStatus.OK);
	}

	@GetMapping("/lopEmployee")
	public ResponseEntity<Object> getLopLeaveDetailsForEmployee(@RequestParam String id) {
		return new ResponseEntity<>(leaveDetailsService.getLopLeaveDetailsForEmployee(id), HttpStatus.OK);
	}

	@GetMapping("/lopDetails")
	public ResponseEntity<Object> getLopDetailsForAllEmployees(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
		return new ResponseEntity<>(leaveDetailsService.getLopDetailsForAllEmployees(fromDate, toDate), HttpStatus.OK);
	}
	
	@GetMapping("/unitHead")
	public ResponseEntity<Object> getLeaveDetailsForBusinessUnit(@RequestParam String id) {
		return new ResponseEntity<>(leaveDetailsService.getLeaveDetailsForBusinessUnit(id), HttpStatus.OK);
	}
	
	@GetMapping("/deptHead")
	public ResponseEntity<Object> getLeaveDetailsForDepartmentUnit(@RequestParam String id) {
		return new ResponseEntity<>(leaveDetailsService.getLeaveDetailsForDepartmentUnit(id), HttpStatus.OK);
	}

	/*
	 * @GetMapping("/leaveFilter") public ResponseEntity<Object>
	 * getAllLeaveDetails(@RequestBody Optional<LeaveDetailsFilterdto>
	 * leaveDetailsFilterDtoOp) { LeaveDetailsFilterdto leaveDetailsFilterdto;
	 * if(leaveDetailsFilterDtoOp.isPresent()) { leaveDetailsFilterdto =
	 * leaveDetailsFilterDtoOp.get(); } else { leaveDetailsFilterdto = new
	 * LeaveDetailsFilterdto(); } return new
	 * ResponseEntity<>(leaveDetailsService.getAllLeaveDetails(leaveDetailsFilterdto
	 * ),HttpStatus.OK); }
	 */

}
