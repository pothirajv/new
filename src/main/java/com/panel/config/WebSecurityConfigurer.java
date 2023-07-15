package com.panel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.panel.support.constant.GeneralConstants;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.UserRepository;

/**
 * <b>Security configurations</b><br>
 *
 * You can configure your application security features by this class when .yml
 * file configurations can not meet your requirements.
 *
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private EmployeeDetailsRepository employeeDetailsRepository;
	
    @Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/pdresources/**").antMatchers("/resources/**").antMatchers("/css/**").antMatchers("/images/**")
				.antMatchers("/js/**").antMatchers("/vedaresources/**").antMatchers("/assets/**").antMatchers("/static/**");
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/a/sendotp", "/a/login", "/a/c", "/a/cc", "/a/r", "/a/rs","/a/d","/a/u","/", "/index", "/**/css/**", "/**/js/**", "/**/images/**", "/**/*.html/", "/pay/**", "/index", "/about", "/contact", "/partners", "/apisup","/awbtrack", "/privacypolicy","/tnc", "/services", "/f/appv","/tracklist","/a/q","/a/q1","/a/t","/register","/user/loginValidate","/user/loginValidate1","/user/update","/faq","/login","/adm/getRate","/user/register","/user/myProfile","/adm/showAllUsers","/u/**","/user/**", "/u/showAllDeity", "/u/showDeityInfo", "/tempadm/viewTemple", "/tempadm/viewTemple/**","/adm/showAllDonations",
                		"/employee/signup","/employee/login","/employee/allrole","/employeeDetails/allrole","/employee/allemployee","/employee/saveRole","/employeeDetails/saveED","/employeeDetails/byid","/employeeDetails/getEmployeeList","/employeeDetails/getEmployeesByDepartment","/employeeDetails/getEmployeesByEmployeeStatus","/employeeDetails/getEmployeesByEmployeeRole","/employeeDetails/getAllEmployDetailsList","/leaveDetails/saveUpdateLeave","/leaveDetails/employeeByName","/leaveDetails/employeeByDepartment","/leaveDetails/employeeByRemainingLeave","/employeeDetails/filter","/leaveDetails/EmployeeById","/leaveDetails/leaveList","/leaveDetails/leaveFilter","/leaveDetails/leaveTypesList","/leaveDetails/leaveStatusList","/Department/saveUpdateDepartment","/Designations/saveUpdateDesignation","/EmploymentTypes/saveUpdateEmploymentType",
                		"/Department/departmentList","/Department/UpdateddepartmentList","/Department/getAllDepartmentsList","/Designations/designationList","/Designations/UpdateddesignationList","/Designations/getAllDesignationsList","/EmploymentTypes/emplymentTypeList","/EmploymentTypes/UpdatedEmploymentTypeList","/EmploymentTypes/getAllEmploymentTypesList","/EmploymentTypes/employmentTypesFilter","/Designations/designationById","/Designations/designationsFilter","/Department/departmentById","/Department/departmentFilter","/EmploymentTypes/employmentTypesById","/businessUnit/saveUpdateBusinessUnit","/businessUnit/getAllBusinessUnits","/businessUnit/businessUnitById","/businessUnit/businessUnitFilter","/masterDepartment/saveUpdateMasterDepartment","/masterDepartment/getAllMasterDepartments","/masterDepartment/masterDepartmentById","/masterDepartment/masterDepartmentFilter",
                		"/clientinfo/hr/saveupdate","/clientinfo/hr/clientname","/clientinfo/hr/filter","/projectinfo/hr/saveupdate","/projectinfo/hr/findbyname","/projectinfo/hr/filter","/activecode/saveandupdate","/assignactive/saveupdate","/assignemptopro/saveupdate","/timelog/saveupdate","/weeklyeffort/saveupdate","/weeklyeffort/getall","/projectinfo/hr/getall","/leaveDetails/id","/leaveDetails/myleaves","/leaveDetails/approval","/clientinfo/hr/getall","/clientinfo/hr/getclientbyid","/employeeDetails/login","/projectinfo/hr/getprojectbyid","/leaveDetails/hrlist","/employeeDetails/allgender","/employeeDetails/allMartialStatus","/employeeDetails/allReportingPerson","/employeeDetails/saveRole","/employeeDetails/allEmployeeStatus","/EmploymentTypes/assignResource","/EmploymentTypes/allAssignResources","/Designations/assignResourceDesig","/Designations/allAssignResourceDesig","/masterDepartment/deptByBU",
                		"/employeeDetails/allEmployeecategory","/EmploymentTypes/allUpdate","/EmploymentTypes/updateById","/EmploymentTypes/allAssignByET","/Designations/updateDesig","/Designations/updateDesigById","/Designations/resourceByDesig","/projectinfo/hr/assignemp","/projectinfo/hr/updatelist","/projectinfo/getbyEmp","/timesheet/saveupdate","/timesheet/getall","/timesheet/getbyid","/HrDashboard/employee","/HrDashboard/client","/HrDashboard/project","/HrDashboard/leave","/employeeDetails/names","/timesheet/employee","/timesheet/client","/timesheet/status","/projectinfo/hr/names","/clientinfo/hr/names","/employeeDetails/managers","/timesheet/approval","/employeeDetails/bloodgroup","/employeeDetails/proficiency","/employeeDetails/board","/employeeDetails/changePassword","/employeeDetails/manager","/timelog/savetimelog","/timelog/getall","/timelog/getbyid","/employeeDetails/loginValidate","/timelog/employee",
                		"/timelog/deletebyid","/timelog/emptimelog","/activecode/getall","/activecode/id","/activecode/byproject","/activecode/name","/activitymeta/save","/activitymeta/id","/activitymeta/all","/timelog/month","/timelog/pdf","/leaveDetails/status","/timelog/getpdf","/projectinfo/unmappedEmp","/batchProcess/leave","/leaveDetails/date","/leaveDetails/manager","/leaveHistory/employee","/leaveDetails/lopEmployee","/timelog/status","/timesheet/timeStatus","/leaveDetails/lopDetails","/employeeDetails/excel","/employeeDetails/unitHead","/employeeDetails/deptHead","/leaveDetails/unitHead","/leaveDetails/deptHead","/projectinfo/unitHead","/projectinfo/deptHead","/projectinfo/manager","/businessUnit/list","/masterDepartment/unitAndDept","/parentActivity/save","/parentActivity/id","/parentActivity/all","/activitymeta/parent","/masterDepartment/project","/employeeDetails/unitOrDept",
                		"/masterDepartment/byUnitHead","/employeeDetails/inUnit","/projectinfo/removeEmp","/timesheet/resubmit","/timesheet/report","/timesheet/pending","/timesheet/month").permitAll()
                .antMatchers("/b/**","/c/**", "/d/**", "/e/**","/api/v1/**","/o/**").hasAnyAuthority("",GeneralConstants.ROLE_USER, GeneralConstants.ROLE_ADMIN, GeneralConstants.ROLE_VENDOR, GeneralConstants.ROLE_DELIVER)
                .antMatchers("/f/**", "/adm/**").hasAuthority(GeneralConstants.ROLE_ADMIN)
                .antMatchers("/ven/**").hasAuthority(GeneralConstants.ROLE_VENDOR)
                .antMatchers("/projectadm/**").hasAuthority(GeneralConstants.ROLE_PROJECTADMIN)
                .antMatchers("/tempadm/**").hasAuthority(GeneralConstants.ROLE_TEMPADMIN)
                .antMatchers("/v/**").hasAnyAuthority(GeneralConstants.ROLE_VENDOR, GeneralConstants.ROLE_ADMIN)
                .antMatchers("/HrDashboard/hr/**","/businessUnit/hr/**","/masterDepartment/hr/**","/EmploymentTypes/hr/**","/Designations/hr/**","/employeeDetails/hr/**","/leaveDetails/hr/**").hasAuthority(GeneralConstants.HR)
                .antMatchers("/leaveDetails/manager/**","/employeeDetails/manager/**").hasAuthority(GeneralConstants.MANAGER)
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager(), employeeDetailsRepository),BasicAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new UnauthenticatedRequestHandler());
    }
    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	public EmployeeDetailsRepository getEmployeeDetailsRepository() {
		return employeeDetailsRepository;
	}

	public void setEmployeeDetailsRepository(EmployeeDetailsRepository employeeDetailsRepository) {
		this.employeeDetailsRepository = employeeDetailsRepository;
	}

    
    

}
