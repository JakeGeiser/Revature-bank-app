package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Account;
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
			String sql = "INSERT INTO bank.customer(first_name, last_name, email, password, phone, join_date) "
							+ "VALUES ('?', '?', '?', '?', '?' , CURRENT_TIMESTAMP)";
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
	
	// request a new account as customer
	public boolean requestAccount(int customerId, String accountName, double balance) {
		int inserted = 0;
		try {
			
			logger.debug("Customer to register new account: "+customerId);
			
			Connection conn = DbConnector.getInstance().getConnection();
			String sql = "INSERT INTO bank.account_requests(customer_id, name, balance, time_requested) "
							+ "VALUES ('?', '?', '?', CURRENT_TIMESTAMP)";
			logger.debug("using statement", sql);
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, customerId);
			pstmt.setString(2, accountName);
			pstmt.setDouble(3, balance);
			
			inserted = pstmt.executeUpdate();
			logger.debug("Inserted customer: "+inserted);
			
		} catch (SQLException e) {
			logger.error("Unable to insert customer into bank.customer", e);
		} catch (Exception e1) {
			logger.error("Unable to insert customer into bank.customer", e1);
		}
		logger.debug("Returning results", inserted!=0);
		
		return inserted!=0;
	}
	
	
	// get all accounts of customer id
	public ArrayList<Account> allAccounts(int customerId) throws Exception{
		ArrayList<Account> accounts = new ArrayList<Account>();
		
		try {
			logger.debug("Customer to register new account: "+customerId);
			
			Connection conn = DbConnector.getInstance().getConnection();
			String sql = "SELECT id, customer_id, name, balance, date_created FROM bank.accounts "
							+"WHERE customer_id = ?";
			
			logger.debug("using statement", sql);
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, customerId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Account tempAccount = new Account();
				tempAccount.setId(rs.getInt("id"));
				tempAccount.setCustomerId(rs.getInt("customer_id"));
				tempAccount.setName(rs.getString("name"));
				tempAccount.setBalance(rs.getDouble("balance"));
				tempAccount.setDateCreated(rs.getDate("date_created"));
				
				accounts.add(tempAccount);
			}
		} catch (SQLException e) {
			logger.error("Unable to perform DB query", e);
			throw e;
		}
		
		logger.debug("Returning results: ", accounts);
		return accounts;
	}
	
	// Deposit method
	public boolean deposit(int customerId, int accountId, double amount) {
		int deposited = 0;
		int inserted = 0;
		try {
			
			logger.debug("Customer to deposit amount $"+amount+" into account "+accountId);
			
			Connection conn = DbConnector.getInstance().getConnection();
			conn.setAutoCommit(false);
			
			// first deposit amount into account balance
			String sql1 = "UPDATE bank.accounts SET balance = balance + ? WHERE (id=? AND customer_id=?)";
			logger.debug("using statement", sql1);
			
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			
			pstmt1.setDouble(1, amount);
			pstmt1.setInt(2, accountId);
			pstmt1.setInt(3, customerId);
			
			deposited = pstmt1.executeUpdate();
			logger.debug("Deposited records: "+deposited);
			
			// then create an appropriate transaction
			String sql2 = "INSERT INTO bank.transactions(account_id, customer_id, type, amount, time) "
							+"VALUES (?, ?, '?', ?, CURRENT_TIMESTAMP)";
			logger.debug("using statement", sql2);
			
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			
			pstmt2.setInt(1, accountId);
			pstmt2.setInt(2, customerId);
			pstmt2.setString(3, "D");
			pstmt2.setDouble(4, amount);
			
			
			inserted = pstmt2.executeUpdate();
			logger.debug("Inserted transaction: "+inserted);
			conn.commit();
		} catch (SQLException e) {
			logger.error("Unable to deposit into bank.account", e);
		} catch (Exception e1) {
			logger.error("Unable to deposit into bank.account", e1);
		}
		logger.debug("Returning results", inserted!=0 && deposited!=0);
		
		return (inserted!=0 && deposited!=0);
	}
	
	// Withdraw method
	
	// Transfer amount between 2 accounts (done on UI level with Deposit and withdraw)
	
	
	
	// Display all transactions of account
	
	
	
	
	
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
