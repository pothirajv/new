package com.panel.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.panel.dto.ClientInformationDto;
import com.panel.dto.ClientInformationFilterDto;
import com.panel.dto.ClientNameDto;
import com.panel.model.ClientInformation;
import com.panel.model.EmployeeDetails;
import com.panel.model.ProjectInformation;
import com.panel.model.UpdateClient;
import com.panel.repository.ClientInformationrepository;
import com.panel.repository.EmployeeDetailsRepository;
import com.panel.repository.ProjectInformationRepository;
import com.panel.repository.UpdateClientRepository;
import com.panel.service.ClientInformationservice;

@Service
public class ClientInformationserviceimpl implements ClientInformationservice {

	@Autowired
	ClientInformationrepository clientInformationrepository;

	@Autowired
	ProjectInformationRepository projectInformationRepository;

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	UpdateClientRepository updateClientRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	public LinkedHashMap<String, Object> SaveAndUpdateclientinformation(ClientInformationDto clientInformationDto) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		if (clientInformationDto.getId() == null) {
			ClientInformation clientinformationOp = new ClientInformation();

			clientinformationOp.setClientName(clientInformationDto.getClientName());
			clientinformationOp.setContactNo(clientInformationDto.getContactNo());
			clientinformationOp.setContactName(clientInformationDto.getContactName());
			clientinformationOp.setContactEmail(clientInformationDto.getContactEmail());
			clientinformationOp.setGst(clientInformationDto.getGst());
			clientinformationOp.setCompany_Address(clientInformationDto.getCompanyAddress());
//			Optional<EmployeeDetails> employee = employeeDetailsRepository
//					.findById(clientInformationDto.getCreatedBy());
//			if (employee.isPresent()) {
			clientinformationOp.setCreatedBy(clientInformationDto.getCreatedBy());
//			}
			clientinformationOp.setCreatedDate(LocalDate.now());
			clientinformationOp.setUpdatedDate(LocalDate.now());

			if (clientinformationOp.getUpdatedBy() != null) {
//				Optional<EmployeeDetails> employeeOp = employeeDetailsRepository
//						.findById(clientInformationDto.getUpdatedBy());
//				if (employeeOp.isPresent()) {
				clientinformationOp.setUpdatedBy(clientInformationDto.getUpdatedBy());
//				}
			}
			clientInformationrepository.save(clientinformationOp);
			entity.put("status", "200");
			entity.put("message", "Client Saved Successfully");
			entity.put("Data", clientinformationOp);

		} else {
			if (clientInformationDto.getId() != null) {
				Optional<ClientInformation> clientinformation = clientInformationrepository
						.findById(clientInformationDto.getId());
				ClientInformation clientinformationOp = clientinformation.get();
				if (!clientinformationOp.equals(null)) {
					BeanUtils.copyProperties(clientInformationDto, clientinformationOp,
							getNullPropertyNames(clientInformationDto));
					clientinformationOp.setClientName(clientInformationDto.getClientName());
					clientinformationOp.setContactNo(clientInformationDto.getContactNo());
					clientinformationOp.setContactEmail(clientInformationDto.getContactEmail());
					clientinformationOp.setContactName(clientInformationDto.getContactName());
					clientinformationOp.setGst(clientInformationDto.getGst());
					clientinformationOp.setCompany_Address(clientInformationDto.getCompanyAddress());
//					clientinformationOp.setCreatedBy(clientInformationDto.getCreatedBy());
					clientinformationOp.setUpdatedDate(LocalDate.now());

					if (clientinformationOp.getUpdatedBy() != null) {
//						Optional<EmployeeDetails> employeeOp = employeeDetailsRepository
//								.findById(clientInformationDto.getUpdatedBy());
//						if (employeeOp.isPresent()) {
						clientinformationOp.setUpdatedBy(clientInformationDto.getUpdatedBy());
//						}
					}
					UpdateClient client = new UpdateClient();
					client.setUpdatedDate(clientinformationOp.getUpdatedDate());
					client.setUpdatedBy(clientinformationOp.getUpdatedBy());
					client.setClient(clientinformationOp);

					updateClientRepository.save(client);
					clientInformationrepository.save(clientinformationOp);
					List<ProjectInformation> projects = projectInformationRepository.findAll();
					for (ProjectInformation project : projects) {
						if (project.getClient().getId().equals(clientinformationOp.getId())) {
							project.setClient(clientinformationOp);
							projectInformationRepository.save(project);
						}
					}
					entity.put("status", "200");
					entity.put("message", "client updated Successfully");
					entity.put("Data", clientinformationOp);
				}
				entity.put("status", "400");
				entity.put("message", "No Data is present");
			}
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
	public List<ClientInformation> findByCustomerName(String customerName) {

//		List<ClientInformation> clientInformations = clientInformationrepository.findByCustomerName(customerName);

		return null;
	}

	@Override
	public LinkedHashMap<String, Object> getAllClientDetails() {

		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		List<ClientInformation> clietInformations = clientInformationrepository.findAll();

		List<ProjectInformation> projectInformations = projectInformationRepository.findAll();

		List<ClientInformation> clientInformations = new ArrayList<>();

		for (ClientInformation clientInformation : clietInformations) {
			int count = 0;
//			int resources = 0;
			Set<String> resources = new HashSet<>();
			clientInformation.setNoOfProjects(0);
			clientInformation.setNoOfResources(0);
			for (ProjectInformation projectInformation : projectInformations) {
				if (projectInformation.getClient() != null) {
					if (projectInformation.getClient().getClientName().equals(clientInformation.getClientName())) {
						count++;
						clientInformation.setNoOfProjects(count);
						clientInformation.setNoOfResources(0);
						if (projectInformation.getAssignedResources() != null) {
							resources.addAll(projectInformation.getAssignedResources());
							clientInformation.setNoOfResources(resources.size());
						}
					}
				}
			}
			if (clientInformation.getCreatedBy() != null) {
				Optional<EmployeeDetails> employee = employeeDetailsRepository
						.findById(clientInformation.getCreatedBy());
				if (employee.isPresent()) {
					clientInformation.setCreatedBy(employee.get().getName());
				}
			}
			if (clientInformation.getUpdatedBy() != null) {
				Optional<EmployeeDetails> employee = employeeDetailsRepository
						.findById(clientInformation.getUpdatedBy());
				if (employee.isPresent()) {
					clientInformation.setUpdatedBy(employee.get().getName());
				}
			}
			clientInformations.add(clientInformation);
		}

		if (clietInformations.size() > 0) {
			entity.put("Status", "200");
			entity.put("Message", "Data is present");
			entity.put("Data", clientInformations);
		} else {
			entity.put("Status", "400");
			entity.put("Message", "No Data is present");
		}

		return entity;

	}

	public LinkedHashMap<String, Object> getClientInformationById(String id) {
		// TODO Auto-generated method stub

		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();

		Optional<ClientInformation> clienOptional = clientInformationrepository.findById(id);
		List<ProjectInformation> projectInformations = projectInformationRepository.findAll();
		if (clienOptional.isPresent()) {
			List<String> projects = new ArrayList<>();
			List<String> resources = new ArrayList<>();
			for (ProjectInformation projectInformation : projectInformations) {
				if (projectInformation.getClient().getId().equals(clienOptional.get().getId())) {
//					resources.addAll(projectInformation.getAssignedResources());
					if (!CollectionUtils.isEmpty(projectInformation.getAssignedResources())) {
						for (String ids : projectInformation.getAssignedResources()) {
							Optional<EmployeeDetails> employee = employeeDetailsRepository.findById(ids);
							if (employee.isPresent()) {
								resources.add(employee.get().getName());
							}
						}
					}
					projects.add(projectInformation.getProjectName());
				}
			}

			Set<String> res = resources.stream().collect(Collectors.toSet());

			entity.put("status", "200");
			entity.put("message", "data is found");
			entity.put("data", clienOptional);
			entity.put("projects", projects);
			entity.put("Resources", res);
		} else {

			entity.put("Status", "405");
			entity.put("Message", "Data is not present");
		}

		return entity;
	}

	@Override
	public List<ClientInformation> getAllClientInformation(ClientInformationFilterDto clientInformationFilterDto) {
		Query query = new Query();

		if (clientInformationFilterDto.getClientName() != null
				&& clientInformationFilterDto.getClientName().equals("")) {

			query.addCriteria(Criteria.where("customername").is(clientInformationFilterDto.getClientName()));

		}
		if (clientInformationFilterDto.getCreatedBy() != null && clientInformationFilterDto.getCreatedBy().equals("")) {

			query.addCriteria(Criteria.where("createdBy").is(clientInformationFilterDto.getCreatedBy()));

		}
		List<ClientInformation> clientInformations3 = mongoTemplate.find(query, ClientInformation.class);

		return clientInformations3;

	}

	@Override
	public LinkedHashMap<String, Object> getClientUpdateDetails(String id) {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		Optional<ClientInformation> clientInfo = clientInformationrepository.findById(id);
		List<UpdateClient> clientUpdates = updateClientRepository.findAll();
		List<UpdateClient> updates = new ArrayList<>();
		if (clientInfo.isPresent()) {
			for (UpdateClient clientUpdate : clientUpdates) {
				if (clientUpdate.getClient().getId().equals(clientInfo.get().getId())) {
					Optional<EmployeeDetails> employee = employeeDetailsRepository
							.findById(clientUpdate.getUpdatedBy());
					if (employee.isPresent()) {
						clientUpdate.setUpdatedBy(employee.get().getName());
					}
					updates.add(clientUpdate);
				}
			}
		}
		if (updates.size() > 0) {
			entity.put("status", "200");
			entity.put("message", "data is present");
			entity.put("data", updates);
		} else {
			entity.put("status", "400");
			entity.put("message", "data is not present");
		}
		return entity;
	}

	@Override
	public LinkedHashMap<String, Object> getAllClientNames() {
		LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		List<ClientInformation> clients = clientInformationrepository.findAll();
		List<ClientNameDto> clientNames = new ArrayList<>();
		for (ClientInformation client : clients) {
			ClientNameDto clientInfo = new ClientNameDto();
			clientInfo.setId(client.getId());
			clientInfo.setName(client.getClientName());
			clientNames.add(clientInfo);
		}
		if (clientNames.size() > 0) {
			entity.put("status", "200");
			entity.put("message", "data is present");
			entity.put("data", clientNames);
		} else {
			entity.put("status", "400");
			entity.put("message", "data is not present");
		}
		return entity;
	}
}