package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Customer;

/**
 * Data Access Object for Bank App
 * @author Jake Geiser
 */
public class BankDao {
	// initialize logger
	private static final Logger logger = LogManager.getLogger(DbConnector.class);
	
	// insert customer method
	public boolean insertCustomer(Customer customer) throws Exception{
		int inserted = 0;
		try {
			
			logger.debug("Received customer to register: "+customer);
			
			Connection conn = DbConnector.getInstance().getConnection();
			String sql = "INSERT INTO bank.customer(first_name, last_name, email, password, phone, join_date)"
							+ "'?', '?', '?', '?', '?' , CURRENT_TIMESTAMP";
			logger.debug("using statement", sql);
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, customer.getFirstName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPassword());
			pstmt.setString(5, customer.getPhone());
			
			inserted = pstmt.executeUpdate();
			logger.debug("Inserted customer: "+inserted);
			
		} catch (SQLException e) {
			logger.error("Unable to insert customer into bank.customer", e);
		}
		logger.debug("Returning results", inserted!=0);
		return inserted != 0;
		
	}
	
	// get Customer information
	public Customer getCustomer(String email, String password) throws Exception{
		Customer loggedInCustomer = new Customer();
		try {
			logger.debug("getting customer id with email="+email+", password="+password);
			Connection conn = DbConnector.getInstance().getConnection();
			String sql = "SELECT id, first_name, last_name, email, password, phone, join_date " 
							+"FROM bank.customer WHERE (email='?' AND password='?')";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				loggedInCustomer.setId(rs.getInt("id")); 
				loggedInCustomer.setFirstName(rs.getString("first_name"));
				loggedInCustomer.setLastName(rs.getString("last_name"));
				loggedInCustomer.setEmail(rs.getString("email"));
				loggedInCustomer.setPassword(rs.getString("password"));
				loggedInCustomer.setPhone(rs.getString("phone"));
				loggedInCustomer.setJoinDate(rs.getDate("join_date"));
			}
		} catch (SQLException e) {
			logger.error("Unable to perform DB query", e);
			throw e;
		}
		logger.debug("Returning logged in customer", loggedInCustomer.toString());
		return loggedInCustomer;
	}
	
	
	//// user account options
	// verify if user account exists - user login method 
	
	
	// Display all accounts of user and their info - call method by default on homepage
	
	
	// Transfer amount between 2 accounts (done on UI level with Deposit and withdraw)
	
	
	// Select specific account to view (done on UI level)
	
	// Display all transactions of account
	
	// Deposit method
	
	// Withdraw method
	
	
	
	//// employee account options
	// verify employee account - employee login method
	
	// View all pending user registrations
	
	// Update pending registration - Approved
	
	// Update pending registration - Denied
	
	
	// Display all transactions - method
	
	// All registered Customers - method
	
	// All Accounts of customer x - method
	
	// All transactions of customer x - method
}
