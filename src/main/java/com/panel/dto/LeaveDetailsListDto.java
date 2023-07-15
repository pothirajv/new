package com.panel.dto;

public class LeaveDetailsListDto {

	private String employeeName;
	private String department;
	private int totalLeave;
	private int remainingLeave;
	private int bookedCasualLeave;
	private int balanceCasualLeave;
	private int bookedSickLeave;
	private int balanceSickLeave;
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getTotalLeave() {
		return totalLeave;
	}
	public void setTotalLeave(int totalLeave) {
		this.totalLeave = totalLeave;
	}
	public int getRemainingLeave() {
		return remainingLeave;
	}
	public void setRemainingLeave(int remainingLeave) {
		this.remainingLeave = remainingLeave;
	}
	public int getBookedCasualLeave() {
		return bookedCasualLeave;
	}
	public void setBookedCasualLeave(int bookedCasualLeave) {
		this.bookedCasualLeave = bookedCasualLeave;
	}
	public int getBalanceCasualLeave() {
		return balanceCasualLeave;
	}
	public void setBalanceCasualLeave(int balanceCasualLeave) {
		this.balanceCasualLeave = balanceCasualLeave;
	}
	public int getBookedSickLeave() {
		return bookedSickLeave;
	}
	public void setBookedSickLeave(int bookedSickLeave) {
		this.bookedSickLeave = bookedSickLeave;
	}
	public int getBalanceSickLeave() {
		return balanceSickLeave;
	}
	public void setBalanceSickLeave(int balanceSickLeave) {
		this.balanceSickLeave = balanceSickLeave;
	}
	
	
	
}
