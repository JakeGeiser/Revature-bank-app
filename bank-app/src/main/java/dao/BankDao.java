package dao;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Data Access Object for Bank App
 * @author Jake Geiser
 */
public class BankDao {
	// create connection
	
	//// register user
	
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
