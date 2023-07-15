package com.panel.config;

import static com.panel.support.constant.SecurityConstants.HEADER_STRING;
import static com.panel.support.constant.SecurityConstants.OTHER_HEADERS;
import static com.panel.support.constant.SecurityConstants.SECRET;
import static com.panel.support.constant.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.UserRepository;
import com.panel.model.EmployeeDetails;
import com.panel.model.Role;
import com.panel.model.UserModel;
import com.panel.support.constant.ErrorConstants;
import com.panel.support.constant.GeneralConstants;
import com.panel.support.util.ApiError;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

//	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//	UserRepository userRepository;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	EmployeeDetailsRepository employeeDetailsRepository;
	
	 @Value("${auth.header.strings}")
	 String authHeaderString;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, EmployeeDetailsRepository employeeDetailsRepository) {
		super(authenticationManager);
		this.employeeDetailsRepository= employeeDetailsRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException, UsernameNotFoundException {
		String header = req.getHeader(HEADER_STRING);
		String tokenPrefix="";
		if(header==null) {
			
			String [] authHeaderStrings=OTHER_HEADERS.split(",");
			for(String authHeader:authHeaderStrings) {
			LOGGER.debug("External system API -->"+authHeader);
			header = req.getHeader(authHeader);
			}
		}else {
			tokenPrefix=TOKEN_PREFIX;
		}
		if (header == null && req.getSession() != null && req.getSession().getAttribute(HEADER_STRING) != null) {
			header = req.getSession().getAttribute(HEADER_STRING).toString();
			tokenPrefix=TOKEN_PREFIX;
		}

		if (header == null) {
			chain.doFilter(req, res);
		} else {
			UsernamePasswordAuthenticationToken authentication = getAuthentication(header,tokenPrefix);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			if (authentication != null) {
				MDC.put("traceUser", authentication.getPrincipal().toString());
				MDC.put("traceID", UUID.randomUUID().toString().replace("-", ""));
				chain.doFilter(req, res);
			}else {
				throw new UsernameNotFoundException(ErrorConstants.TOKEN_VALIDATION_FAILED);
			}
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token,String tokenPrefix) {
		PrincipalDetails principalDetails = new PrincipalDetails();
		if (token != null) {
			Jws<Claims> jws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(tokenPrefix, ""));
			String emailId = jws.getBody().get("emailId") == null ? "" : jws.getBody().get("emailId").toString();
			String name = jws.getBody().get("name") == null ? "" : jws.getBody().get("name").toString();
			String employeeCode=jws.getBody().get("employeeCode") == null ? "" : jws.getBody().get("employeeCode").toString();
			String id = jws.getBody().get("id") == null ? "" : jws.getBody().get("id").toString();
			String role =jws.getBody().get("role") == null ? "" : jws.getBody().get("role").toString();
			
			
		/*	String mobileNo = jws.getBody().get("mobileNumber") == null ? ""
					: jws.getBody().get("mobileNumber").toString();		*/

			EmployeeDetails employeeDetails = checkUserAndTokenStatus(emailId,id, token.replace(tokenPrefix, ""));

			if (employeeDetails != null && employeeDetails.getEmployeeToken().equals(token.replace(tokenPrefix, ""))) {
				principalDetails.setEmailId(emailId);
				principalDetails.setName(name);
				principalDetails.setId(id);
				principalDetails.setEmployeeCode(employeeCode);
//				principalDetails.setRole((String) jws.getBody().get("role"));
				principalDetails.setRole(role);
//				principalDetails.setVendorName(userModel.getVendorName());
				String roles;
				roles = (String) jws.getBody().get("role");
				List<GrantedAuthority> grantedAuths = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
				LOGGER.debug("Employee ID -->"+id);
				LOGGER.debug("Employee Code -->"+employeeCode);
				return new UsernamePasswordAuthenticationToken(principalDetails, null, grantedAuths);
			} else {
				return null;
				// logger.error(ErrorConstants.INVALID_USER_STATUS);
				// throw new UsernameNotFoundException(ErrorConstants.INVALID_USER_STATUS);
			}
		} else {
			// logger.error(ErrorConstants.INVALID_USER_STATUS);
			// throw new UsernameNotFoundException(ErrorConstants.INVALID_USER_STATUS);
			return null;
		}
	}

	public EmployeeDetails checkUserAndTokenStatus(String emailId,String id, String token) {
		EmployeeDetails ed = null;
		try {
			Optional<EmployeeDetails> eid = employeeDetailsRepository.findById(id);
			if(eid.isPresent()) {
			if (emailId != null && !emailId.isEmpty()) {
				 ed = employeeDetailsRepository.findByEmailIdIgnoreCase(emailId);
			}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return ed;
	}

	public void setUnauthorizedResponse(HttpServletResponse response, Exception e) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
		try {
			PrintWriter out = response.getWriter();
			out.println(apiError.convertToJson());
		} catch (IOException ex) {
			logger.error("Error", ex);
		}
	}
}
