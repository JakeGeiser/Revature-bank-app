package model;

import java.sql.*;

/**
 * Account POJO
 * @author Jake Geiser
 */
public class Account {
	private int id;
	private int customerId;
	private String name;
	private double balance;
	private Date dateCreated;
	
	// getter setter methods
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	@Override
	public String toString() {
		return "Account [id="+id+", name="+name+", balance="+balance+", dateCreated="+dateCreated+"]";
	}
}
