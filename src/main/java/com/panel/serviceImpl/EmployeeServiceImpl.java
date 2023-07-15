package com.panel.serviceImpl;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.panel.dto.EmployeeDto;
import com.panel.dto.EmployeesListDto;
import com.panel.model.Employee;
import com.panel.model.EmployeeDetails;
import com.panel.model.Role;
import com.panel.repository.EmployeeRepository;
import com.panel.repository.RoleRepository;
import com.panel.service.EmployeeService;


@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	public LinkedHashMap<String, Object> signUp(EmployeeDto employeeDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		List<Employee> employeeNames = employeeRepository.findByEmployeeName(employeeDto.getEmployeeName());
		if (employeeNames.size() == 0) {
			List<Employee> phone = employeeRepository.findByPhone(employeeDto.getPhone());
			if (phone.size() == 0) {
					List<Employee> email = employeeRepository.findByEmail(employeeDto.getEmail());
					if (email.size() == 0) {
						
						Employee employee=new Employee();
						BeanUtils.copyProperties(employeeDto, employee);
						
						Optional<Role> role=roleRepository.findById(employeeDto.getRole());
						if(role.isPresent()) {
							employee.setRole(role.get());
						}
						
						employee.setPassword(this.encode(employeeDto.getPassword()));
						employeeRepository.save(employee);
						entity.put("Status", "200");
						entity.put("Message", "success");
						entity.put("Data", employee);
					} else {
						entity.put("status", "405");
						entity.put("message", "Email AlreadyExist!..");
					}
				}  else {

				entity.put("Status", "400");
				entity.put("Message", "Phone number already exists");
			}
		}else {
			entity.put("Status", "400");
			entity.put("Message", "Username already exists");

		}
		return entity;
	}
	
	
	public String encode(String text) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
		return bCryptPasswordEncoder.encode(text);

	}

	public boolean decode(String text, String text2) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.matches(text, text2);

	}
	
	
	public LinkedHashMap<String, Object>  login(String employeeName, String password) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<Employee> employee = employeeRepository.findByEmployeeNameOrEmail(employeeName,employeeName);
		
				 if (employee.size() > 0) {
			for (Employee employees : employee) {
				// System.out.println(user[2].toString());
				// System.out.println(this.encode(password.trim()));
				if (this.decode(password, employees.getPassword())) {
					entity.put("status", "200");
					entity.put("message", "Successfully Login");
					entity.put("signupEmployeeId", employees.getId());
					entity.put("role", employees.getRole().getRole());
					entity.put("roleId", employees.getRole().getId());
				} else {
					entity.put("status", "400");
					entity.put("message", "Please Check Password");
				}
			}

		} else {
			entity.put("status", "400");
			entity.put("message", "No User Found");
		}
		return entity;
	}
	
	public LinkedHashMap<String, Object> getRoleList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<Role> role=roleRepository.findAll();
		
		if(role.size()>0) {
		entity.put("Status:", "200");
		entity.put("Message:", "Data is present");
		entity.put("Data:",role);
		}else {
			entity.put("Status:", "400");
			entity.put("Message:", " No Data is present");
		}

		return entity;
	}
	
	public LinkedHashMap<String, Object> getEmployeeList() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<Employee> employees=employeeRepository.findAll();
		
		if(employees.size()>0) {
		entity.put("Status:", "200");
		entity.put("Message:", "Data is present");
		entity.put("Data:",employees);
		}else {
			entity.put("Status:", "400");
			entity.put("Message:", " No Data is present");
		}
		

		return entity;
	}
	
	public LinkedHashMap<String, Object> saveOrUpdateRole(Role role) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		
		if(role.getId()==null) {
			Role roles =new Role();
			roles.setRole(role.getRole());
			roleRepository.save(roles);
			
			entity.put("Status:", "200");
			entity.put("Message:", "Data is Saved");
			entity.put("Data:",roles);
		}else {
			Optional<Role> roles=roleRepository.findById(role.getId());
			if(roles.get().getId()!=null) {
				Role Role=new Role();
				Role.setRole(role.getRole());
				roleRepository.save(Role);
				
				entity.put("Status:", "200");
				entity.put("Message:", "Data is Updated");
				entity.put("Data:",Role);
				
			}else {
				entity.put("Status:", "400");
				entity.put("Message:", "couldn't Updated");
			}
			
		}
	return entity;	
	}
}
