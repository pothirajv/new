package com.panel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.panel.dto.EmployeeDto;
import com.panel.model.Role;
import com.panel.service.EmployeeService;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping("/signup")
	public ResponseEntity<Object> signUp(@RequestBody EmployeeDto employeeDto)  {
		return new ResponseEntity<>(employeeService.signUp(employeeDto),HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestParam String employeeName, @RequestParam String password)  {
		return new ResponseEntity<>(employeeService.login(employeeName, password),HttpStatus.OK);
	}
	
	@GetMapping("/allrole")
	public ResponseEntity<Object> getRoleList()  {
		return new ResponseEntity<>(employeeService.getRoleList(),HttpStatus.OK);
	}

	@GetMapping("/allemployee")
	public ResponseEntity<Object> getEmployeeList()  {
		return new ResponseEntity<>(employeeService.getEmployeeList(),HttpStatus.OK);
	}

	@PostMapping("/saveRole")
	public ResponseEntity<Object> saveOrUpdateRole(@RequestBody Role role)  {
		return new ResponseEntity<>(employeeService.saveOrUpdateRole(role),HttpStatus.OK);
	}
}
