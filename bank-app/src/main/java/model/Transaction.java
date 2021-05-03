package model;

import java.sql.Timestamp;

public class Transaction {
	private int id;
	private int accountID;
	private int customerID;
	private String type;
	private double amount;
	private Timestamp time;
	
	// getter setter methods
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	@Override
	public String toString() {
		return "Transaction [id="+id+", accountID="+accountID+", customerID="+customerID+", type="+type+", amount="+amount+", time="+time+"]";
	}

}
