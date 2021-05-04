package model;

import java.sql.Date;

/**
 * Customer POJO
 * @author Jake Geiser
 */
public class Customer {
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phone;
	private Date joinDate;
	
	// define default constructor
	public Customer(){
		this.id = -1;
	}
	
	// getter setter methods
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	
	@Override
	public String toString() {
		return "Customer [id="+id+", firstName="+firstName+", lastName="+lastName+", email="+email
				+", password="+password+", phone="+phone+", joinDate="+joinDate+"]";
	}
	
}
