package model;

import java.sql.Timestamp;

public class AccountRequest {
	private int id;
	private int customerID;
	private String name; // account name
	private double balance;
	private String status;
	private Timestamp timeRequested;
	private Timestamp timeUpdated;
	private int employeeID;
	
	// getter setter methods
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getTimeRequested() {
		return timeRequested;
	}
	public void setTimeRequested(Timestamp timeRequested) {
		this.timeRequested = timeRequested;
	}
	public Timestamp getTimeUpdated() {
		return timeUpdated;
	}
	public void setTimeUpdated(Timestamp timeUpdated) {
		this.timeUpdated = timeUpdated;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	
	public String toString() {
		return "AccountRequest [id="+id+", customerID="+customerID+", name="+name+", balance="+balance+", ="
				+", status="+status+", timeRequested="+timeRequested+", timeUpdated="+timeUpdated+", employeeID="+employeeID+"]";
	}
}
