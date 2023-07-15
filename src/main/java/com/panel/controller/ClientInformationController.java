package com.panel.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

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

import com.panel.dto.ClientInformationDto;
import com.panel.dto.ClientInformationFilterDto;
import com.panel.model.ClientInformation;
import com.panel.service.ClientInformationservice;

@RestController
@RequestMapping("/clientinfo")
@CrossOrigin("*")
public class ClientInformationController {
	
	
	@Autowired
	ClientInformationservice clientInformationservice;
	
	@PostMapping("/hr/saveupdate")
	public ResponseEntity<Object> SaveAndUpdateclientinformation(@RequestBody ClientInformationDto clientInformationDto) {
		
		
		return new ResponseEntity<>(clientInformationservice.SaveAndUpdateclientinformation(clientInformationDto), HttpStatus.OK);
	}
	
	
	
	@GetMapping("/hr/clientname")
	public List<ClientInformation> findByCustomerName(String customerName){
		
		
		return clientInformationservice.findByCustomerName(customerName);
	}

	
	
	@GetMapping("hr/filter")
	public List<ClientInformation> getAllClientInformationDetails(@RequestBody Optional<ClientInformationFilterDto> clienOptional){
	
		ClientInformationFilterDto clientInformationFilterDto;
		
		if(clienOptional.isPresent()) {
			
			clientInformationFilterDto = clienOptional.get();
			
			
		}
		else {
			
			clientInformationFilterDto = new  ClientInformationFilterDto();
			
		}
		return clientInformationservice.getAllClientInformation(clientInformationFilterDto);
	}
	
	
	@GetMapping("/hr/getall")
	public ResponseEntity<Object> getallClientDetails(){
		
		
		return new ResponseEntity<>( clientInformationservice.getAllClientDetails(),HttpStatus.OK);
		
		
	}
	
	@GetMapping("/hr/getclientbyid")
	public LinkedHashMap<String, Object> getClientInformationById(String id) {
		
	
	
		return clientInformationservice.getClientInformationById(id);
	}
	
	@GetMapping("/hr/updatedetails")
	public ResponseEntity<Object> getClientUpdateDetails(String id) {
		return new ResponseEntity<>( clientInformationservice.getClientUpdateDetails(id),HttpStatus.OK);
	}

	@GetMapping("/hr/names")
	public ResponseEntity<Object> getAllClientNames() {
		return new ResponseEntity<>( clientInformationservice.getAllClientNames(),HttpStatus.OK);
	}
}

	