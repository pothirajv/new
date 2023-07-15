package com.panel.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "leave_duration")
public class LeaveDuration {

	@Id
	@Indexed
	private String id;
	@Field("leave_duration")
	private String leaveDuration ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLeaveDuration() {
		return leaveDuration;
	}
	public void setLeaveDuration(String leaveDuration) {
		this.leaveDuration = leaveDuration;
	}
	
	
	
	
	
	
	
}
