package com.panel.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.record.chart.LinkedDataRecord;

import com.panel.dto.ClientInformationDto;
import com.panel.dto.ClientInformationFilterDto;
import com.panel.model.ClientInformation;


public interface ClientInformationservice {

	public LinkedHashMap<String, Object> SaveAndUpdateclientinformation(ClientInformationDto clientInformationDto);
	
	public List<ClientInformation> findByCustomerName(String customerName);
	
	public List<ClientInformation> getAllClientInformation(ClientInformationFilterDto clientInformationFilterDto);
	
	public LinkedHashMap<String, Object> getAllClientDetails();
	
	public LinkedHashMap<String, Object> getClientInformationById(String id);
	
	public LinkedHashMap<String, Object> getClientUpdateDetails(String id);
	
	public LinkedHashMap<String, Object> getAllClientNames();	
}
