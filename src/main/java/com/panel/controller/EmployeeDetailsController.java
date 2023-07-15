package com.panel.controller;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.panel.dto.ChangePassword;
import com.panel.dto.EmployeeDetailsDto;
import com.panel.model.EmployeeDetails;
import com.panel.model.Role;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.service.EmployeeDetailsService;
import com.panel.support.constant.GeneralConstants;


@RestController
@RequestMapping("/employeeDetails")
@CrossOrigin("*")
public class EmployeeDetailsController {

	@Autowired
	EmployeeDetailsService employeeDetailsService;
	
	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;
	
	@PostMapping("/saveED")
	public ResponseEntity<Object> saveAndUpdateEmployeeDetails(@RequestBody EmployeeDetailsDto employeeDetailsDto)  {
		return new ResponseEntity<>(employeeDetailsService.saveAndUpdateEmployeeDetails(employeeDetailsDto),HttpStatus.OK);
	}
	@GetMapping("/byid")
	public ResponseEntity<Object> getEmployeeById(@RequestParam String id)  {
		return new ResponseEntity<>(employeeDetailsService.getEmployeeById(id),HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object>  login(@RequestParam String employeeName, @RequestParam String password) {
		return new ResponseEntity<>(employeeDetailsService.login(employeeName, password),HttpStatus.OK);
	}
	/*
	@GetMapping("/getEmployeeList")
	public ResponseEntity<Object> getEmployeeList()  {
		return new ResponseEntity<>(employeeDetailsService.getEmployeeList(),HttpStatus.OK);
	}*/
	/*
	@GetMapping("/getEmployeesByDepartment")
	public ResponseEntity<Object> getEmployeesByDepartment(@RequestParam String department)  {
		return new ResponseEntity<>(employeeDetailsService.getEmployeesByDepartment(department),HttpStatus.OK);
	}
	@GetMapping("/getEmployeesByEmployeeStatus")
	public ResponseEntity<Object> getEmployeesByEmployeeStatus(@RequestParam String employeeStatus)  {
		return new ResponseEntity<>(employeeDetailsService.getEmployeesByEmployeeStatus(employeeStatus),HttpStatus.OK);
	}*/
	/*
	@GetMapping("/getEmployeesByEmployeeRole")
	public ResponseEntity<Object> getEmployeesByEmployeeRole(@RequestParam String employeeRole)  {
		return new ResponseEntity<>(employeeDetailsService.getEmployeesByEmployeeRole(employeeRole),HttpStatus.OK);
	}
	*/
	/*
	@GetMapping("/filter")
	public ResponseEntity<Object> getAllEmployeeDetails(@RequestBody Optional<EmployeeDetailsFilterDto> employeeDetailsFilterDtoOp) {
		EmployeeDetailsFilterDto employeeDetailsFilterDto;
		if(employeeDetailsFilterDtoOp.isPresent()) {
			employeeDetailsFilterDto = employeeDetailsFilterDtoOp.get();
		} else {
			employeeDetailsFilterDto = new EmployeeDetailsFilterDto();
		}
		return new ResponseEntity<>(employeeDetailsService.getAllEmployeeDetails(employeeDetailsFilterDto),HttpStatus.OK);
	}
	*/
	
	@GetMapping("/getAllEmployDetailsList")
	public ResponseEntity<Object> getAllEmployDetailsList()  {
		return new ResponseEntity<>(employeeDetailsService.getAllEmployDetailsList(),HttpStatus.OK);
	}
	@GetMapping("/allrole")
	public ResponseEntity<Object> getRoleList()  {
		return new ResponseEntity<>(employeeDetailsService.getRoleList(),HttpStatus.OK);
	}
	@GetMapping("/allgender")
	public ResponseEntity<Object> getAllGenderList(){
		return new ResponseEntity<>(employeeDetailsService.getAllGenderList(),HttpStatus.OK);
	}
	
	@GetMapping("/allMartialStatus")
	public ResponseEntity<Object>  getAllMartialStatusList(){
		return new ResponseEntity<>(employeeDetailsService.getAllMartialStatusList(),HttpStatus.OK);
	}
	
	@GetMapping("/allReportingPerson")
	public ResponseEntity<Object> getAllReportingPersonList(@RequestParam String id) {
		return new ResponseEntity<>(employeeDetailsService.getAllReportingPersonList(id),HttpStatus.OK);
	}
	
	@GetMapping("/allEmployeeStatus")
	public ResponseEntity<Object> getAllEmployeeStatusList(){
		return new ResponseEntity<>(employeeDetailsService.getAllEmployeeStatusList(),HttpStatus.OK);
	}
	
	@GetMapping("/allEmployeecategory")
	public ResponseEntity<Object> getAllEmployeeCategoryList() {
		return new ResponseEntity<>(employeeDetailsService.getAllEmployeeCategoryList(),HttpStatus.OK);
	}
	
	@PostMapping("/saveRole")
	public ResponseEntity<Object> saveOrUpdateRole(@RequestBody Role role){
		return new ResponseEntity<>(employeeDetailsService.saveOrUpdateRole(role),HttpStatus.OK);
	}
	
	@GetMapping("/names")
	public ResponseEntity<Object> getAllEmployeeNameList(){
		return new ResponseEntity<>(employeeDetailsService.getAllEmployeeNameList(),HttpStatus.OK);
	}
	
	@GetMapping("/managers")
	public ResponseEntity<Object> getAllManagersList(){
		return new ResponseEntity<>(employeeDetailsService.getAllManagersList(),HttpStatus.OK);
		
	}
	
	@GetMapping("/bloodgroup")
	public ResponseEntity<Object> getAllBloodGroup() {
		return new ResponseEntity<>(employeeDetailsService.getAllBloodGroup(),HttpStatus.OK);
	}
	
	@GetMapping("/proficiency")
	public ResponseEntity<Object> getAllProficiency() {
		return new ResponseEntity<>(employeeDetailsService.getAllProficiency(),HttpStatus.OK);
	}
	
	@GetMapping("/board")
	public ResponseEntity<Object> getAllBoards() {
		return new ResponseEntity<>(employeeDetailsService.getAllBoards(),HttpStatus.OK);
	}
	
	@PostMapping("/changePassword")
	public ResponseEntity<Object> changePassword(@RequestBody ChangePassword changePassword){
		return new ResponseEntity<>(employeeDetailsService.changePassword(changePassword),HttpStatus.OK);
	}
	
	 @GetMapping("/manager")
		public ResponseEntity<Object> getEmployeesByManager(@RequestParam String id, @RequestParam(required = false) String businessUnit, @RequestParam(required = false) String department) {
			return new ResponseEntity<>(employeeDetailsService.getEmployeesByManager(id, businessUnit, department),HttpStatus.OK);
		}
	 
	 /*---- jwt login validation ----*/
	 
	 @PostMapping("/loginValidate")
	  ResponseEntity<Object> validateLogin(@RequestParam String emailId, @RequestParam String password,HttpServletRequest response,HttpSession session){
		  
		  String responseMsg ="";
		 EmployeeDetails ed = employeeDetailsRepository.findByEmailId(emailId);
		 try {
			 
			 final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = employeeDetailsService.validateLogin(emailId, password);
			 session.setAttribute("authorization", "Bearer" + usernamePasswordAuthenticationToken.getName());
			 User user = (User) usernamePasswordAuthenticationToken.getPrincipal();
			 if(user.getAuthorities() != null && user.getAuthorities().size()>0) {
				 
				 String roleName = ed.getRole();
				 roleName= user.getAuthorities().toArray()[0].toString();
				 LoginResponse loginResponse = new LoginResponse();
				 
				 if(roleName.equals(GeneralConstants.HR)) {
					loginResponse.setResponseText("Success");
					loginResponse.setEmployeeToken(usernamePasswordAuthenticationToken.getName());
					loginResponse.setEmployeeDetails(ed);
					return ResponseEntity.ok(loginResponse);
				 }
				 if(roleName.equals(GeneralConstants.MANAGER)) {
						loginResponse.setResponseText("Success");
						loginResponse.setEmployeeToken(usernamePasswordAuthenticationToken.getName());
						loginResponse.setEmployeeDetails(ed);
						return ResponseEntity.ok(loginResponse);
					 }
				 if(roleName.equals(GeneralConstants.TEAM_LEADER)) {
						loginResponse.setResponseText("Success");
						loginResponse.setEmployeeToken(usernamePasswordAuthenticationToken.getName());
						loginResponse.setEmployeeDetails(ed);
						return ResponseEntity.ok(loginResponse);
					 }
				 if(roleName.equals(GeneralConstants.TEAM_MEMBER)) {
						loginResponse.setResponseText("Success");
						loginResponse.setEmployeeToken(usernamePasswordAuthenticationToken.getName());
						loginResponse.setEmployeeDetails(ed);
						return ResponseEntity.ok(loginResponse);
					 }
			 }
			 
		 }catch(UsernameNotFoundException e){
			 
			 responseMsg = "Error:" + e.getMessage();
			 LoginResponse logResponse = new LoginResponse();
			 logResponse.setResponseText(responseMsg);
			 logResponse.setEmployeeToken(null);
			 logResponse.setEmployeeDetails(null);
			 return ResponseEntity.ok(logResponse);
			 
		 }
		 
		 return ResponseEntity.ok(responseMsg);
		  
	  }
	 
	 @PostMapping("/excel")
	 public ResponseEntity<Object> excelUpload(@RequestParam MultipartFile file) throws IOException {
		 return new ResponseEntity<>(employeeDetailsService.excelUpload(file),HttpStatus.OK);
	 }
	 
	 @GetMapping("/unitHead")
	 public ResponseEntity<Object> getAllEmployeesByBusinessUnit(@RequestParam String id) {
		 return new ResponseEntity<>(employeeDetailsService.getAllEmployeesByBusinessUnit(id),HttpStatus.OK);
	 }
	 
	 @GetMapping("/deptHead")
	 public ResponseEntity<Object> getAllEmployeesByMasterDepartment(@RequestParam String id) {
		 return new ResponseEntity<>(employeeDetailsService.getAllEmployeesByMasterDepartment(id),HttpStatus.OK);
	 }
	 
	 @GetMapping("/unitOrDept")
	 public ResponseEntity<Object> getEmployeesByBusinessUnitAndDepartment(@RequestParam String businessUnit, @RequestParam(required = false) String department) {
		 return new ResponseEntity<>(employeeDetailsService.getEmployeesByBusinessUnitAndDepartment(businessUnit, department),HttpStatus.OK);
	 }
	 
	 @GetMapping("/inUnit")
	 public ResponseEntity<Object> getEmployeesNameByBusinessUnit(@RequestParam String id, @RequestParam String projectId) {
		 return new ResponseEntity<>(employeeDetailsService.getEmployeesNameByBusinessUnit(id, projectId),HttpStatus.OK);
	 }
}
