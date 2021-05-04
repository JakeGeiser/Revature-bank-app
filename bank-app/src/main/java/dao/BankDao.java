package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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
			return inserted != 0;
		} catch (SQLException e) {
			logger.error("Unable to insert customer into bank.customer", e);
			return false;
		}
		
		
	}
	
	// get Customer information
	public ArrayList<Object> getCustomer() throws Exception{
		Connection conn = DbConnector.getInstance().getConnection();
		
		return null;
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
