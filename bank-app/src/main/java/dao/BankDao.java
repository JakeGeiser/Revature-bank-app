package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Account;
import model.AccountRequest;
import model.Customer;
import model.Employee;
import model.Transaction;

/**
 * Data Access Object for Bank App
 * @author Jake Geiser
 */
public class BankDao { // Persistence Layer
	// initialize logger
	private static final Logger logger = LogManager.getLogger(BankDao.class);
	
	// Turn BankDao into a singleton
	private static BankDao instance;
	
	public static BankDao getInstance() throws Exception {
		if(instance == null) {
			instance = new BankDao();
		}
		return instance;
	}
	
	// insert customer method
	public boolean insertCustomer(Customer customer) throws Exception {
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
			
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
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
	public boolean requestAccount(int customerId, String accountName, double balance) throws Exception{
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
			logger.debug("Customer to view accounts: "+customerId);
			
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
	public boolean deposit(int customerId, int accountId, double amount) throws Exception{
		int deposited = 0;
		int inserted = 0;
		Connection conn = null;
		try {
			
			logger.debug("Customer to deposit amount $"+amount+" into account "+accountId);
			
			conn = DbConnector.getInstance().getConnection();
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
		} catch (SQLException e1) {
			logger.error("Unable to deposit into bank.account", e1);
			try {
				if (conn!=null) {
				conn.rollback();
				}
			} catch (SQLException e11) {
				logger.error("Unable to rollback commit");
			}
		} catch (Exception e2) {
			logger.error("Unable to deposit into bank.account", e2);
			try {
				if (conn!=null) {
				conn.rollback();
				}
			} catch (SQLException e22) {
				logger.error("Unable to rollback commit");
			}
		}
		logger.debug("Returning results", inserted!=0 && deposited!=0);
		
		return (inserted!=0 && deposited!=0);
	}
	
	// Withdraw method
	public boolean withdraw(int customerId, int accountId, double amount) throws Exception{
		int withdrawn = 0;
		int inserted = 0;
		Connection conn = null;
		try {
			
			logger.debug("Customer to withdraw amount $"+amount+" into account "+accountId);
			
			conn = DbConnector.getInstance().getConnection();
			conn.setAutoCommit(false);
			
			// first deposit amount into account balance
			String sql1 = "UPDATE bank.accounts SET balance = balance - ? WHERE (id=? AND customer_id=?)";
			logger.debug("using statement", sql1);
			
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			
			pstmt1.setDouble(1, amount);
			pstmt1.setInt(2, accountId);
			pstmt1.setInt(3, customerId);
			
			withdrawn = pstmt1.executeUpdate();
			logger.debug("Deposited records: "+withdrawn);
			
			// then create an appropriate transaction
			String sql2 = "INSERT INTO bank.transactions(account_id, customer_id, type, amount, time) "
							+"VALUES (?, ?, '?', ?, CURRENT_TIMESTAMP)";
			logger.debug("using statement", sql2);
			
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			
			pstmt2.setInt(1, accountId);
			pstmt2.setInt(2, customerId);
			pstmt2.setString(3, "W");
			pstmt2.setDouble(4, amount);
			
			inserted = pstmt2.executeUpdate();
			logger.debug("Inserted transaction: "+inserted);
			
			conn.commit();
		} catch (SQLException e1) {
			logger.error("Unable to withdraw from bank.account", e1);
			try {
				if (conn!=null) {
				conn.rollback();
				}
			} catch (SQLException e11) {
				logger.error("Unable to rollback commit");
			}
		} catch (Exception e2) {
			logger.error("Unable to withdraw from bank.account", e2);
			try {
				if (conn!=null) {
				conn.rollback();
				}
			} catch (SQLException e22) {
				logger.error("Unable to rollback commit");
			}
		}
		logger.debug("Returning results", inserted!=0 && withdrawn!=0);
		
		return (inserted!=0 && withdrawn!=0);
	}
	
	// Transfer amount between 2 accounts (done on UI level with Deposit and withdraw)
	public boolean transfer(int customerId, int accountId1, int accountId2, double amount) throws Exception{
		int withdrawn = 0;
		int insertedW = 0;
		int deposited = 0;
		int insertedD = 0;
		Connection conn = null;
		try {
			
			//// withdraw section
			logger.debug("Customer "+customerId+" to transfer $"+amount+" from account "+accountId1+" --> "+accountId2);
			
			conn = DbConnector.getInstance().getConnection();
			conn.setAutoCommit(false);
			
			// first deposit amount into account balance
			String sql1 = "UPDATE bank.accounts SET balance = balance - ? WHERE (id=? AND customer_id=?)";
			logger.debug("using statement", sql1);
			
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			
			pstmt1.setDouble(1, amount);
			pstmt1.setInt(2, accountId1);
			pstmt1.setInt(3, customerId);
			
			withdrawn = pstmt1.executeUpdate();
			logger.debug("Deposited records: "+withdrawn);
			
			// then create an appropriate transaction
			String sql2 = "INSERT INTO bank.transactions(account_id, customer_id, type, amount, time) "
							+"VALUES (?, ?, '?', ?, CURRENT_TIMESTAMP)";
			logger.debug("using statement", sql2);
			
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			
			pstmt2.setInt(1, accountId1);
			pstmt2.setInt(2, customerId);
			pstmt2.setString(3, "W");
			pstmt2.setDouble(4, amount);
			
			insertedW = pstmt2.executeUpdate();
			logger.debug("Inserted transaction: "+insertedW);
			
			
			//// deposit section 
			logger.debug("Customer to deposit amount $"+amount+" into account "+accountId2);
			
			// first deposit amount into account balance
			String sql3 = "UPDATE bank.accounts SET balance = balance + ? WHERE (id=? AND customer_id=?)";
			logger.debug("using statement", sql3);
			
			PreparedStatement pstmt3 = conn.prepareStatement(sql3);
			
			pstmt3.setDouble(1, amount);
			pstmt3.setInt(2, accountId2);
			pstmt3.setInt(3, customerId);
			
			deposited = pstmt3.executeUpdate();
			logger.debug("Deposited records: "+deposited);
			
			// then create an appropriate transaction
			String sql4 = "INSERT INTO bank.transactions(account_id, customer_id, type, amount, time) "
							+"VALUES (?, ?, '?', ?, CURRENT_TIMESTAMP)";
			logger.debug("using statement", sql4);
			
			PreparedStatement pstmt4 = conn.prepareStatement(sql4);
			
			pstmt4.setInt(1, accountId2);
			pstmt4.setInt(2, customerId);
			pstmt4.setString(3, "D");
			pstmt4.setDouble(4, amount);
			
			
			insertedD = pstmt4.executeUpdate();
			logger.debug("Inserted transaction: "+insertedD);
			
			// commit transactions
			conn.commit();
		} catch (SQLException e1) {
			logger.error("Unable to transfer between accounts", e1);
			try {
				if (conn!=null) {
				conn.rollback();
				}
			} catch (SQLException e11) {
				logger.error("Unable to rollback commit");
			}
		} catch (Exception e2) {
			logger.error("Unable to transfer between accounts", e2);
			try {
				if (conn!=null) {
				conn.rollback();
				}
			} catch (SQLException e22) {
				logger.error("Unable to rollback commit");
			}
		}
		logger.debug("Returning results", (insertedW!=0 && withdrawn!=0)&&(insertedD!=0 && deposited!=0));
		
		return ((insertedW!=0 && withdrawn!=0)&&(insertedD!=0 && deposited!=0));
	}	
	
	// Display all transactions of customer accounts
	public ArrayList<Transaction> allTransactions(int customerId) throws Exception{
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		
		try {
			logger.debug("Customer to view all transactions: "+customerId);
			
			Connection conn = DbConnector.getInstance().getConnection();
			String sql = "SELECT id, account_id, customer_id, type, amount, time FROM bank.transactions "
							+"WHERE customer_id = ?";
			
			logger.debug("using statement", sql);
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, customerId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Transaction tempTransaction = new Transaction();
				tempTransaction.setId(rs.getInt("id"));
				tempTransaction.setAccountID(rs.getInt("account_id"));
				tempTransaction.setCustomerID(rs.getInt("customer_id"));
				tempTransaction.setType(rs.getString("type"));
				tempTransaction.setAmount(rs.getDouble("amount"));
				tempTransaction.setTime(rs.getTimestamp("time"));
				
				transactions.add(tempTransaction);
			}
		} catch (SQLException e) {
			logger.error("Unable to perform DB query", e);
			throw e;
		}
		
		logger.debug("Returning transaction results: ", transactions);
		return transactions;
	}
	
	
	
	
	//// employee account options
	// getEmployee info
	public Employee getEmployee(String email, String password) throws Exception{
		Employee loggedInEmployee = new Employee();
		try {
			logger.debug("getting employee with email="+email+", password="+password);
			Connection conn = DbConnector.getInstance().getConnection();
			String sql = "SELECT id, first_name, last_name, email, password " 
							+"FROM bank.employee WHERE (email='?' AND password='?')";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,email);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				loggedInEmployee.setId(rs.getInt("id")); 
				loggedInEmployee.setFirstName(rs.getString("first_name"));
				loggedInEmployee.setLastName(rs.getString("last_name"));
				loggedInEmployee.setEmail(rs.getString("email"));
				loggedInEmployee.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			logger.error("Unable to perform DB query", e);
			throw e;
		}
		logger.debug("Returning logged in employee", loggedInEmployee.toString());
		return loggedInEmployee;
	}
	
	// View all pending user registrations
	public ArrayList<AccountRequest> allAccountRequests() throws Exception{
		ArrayList<AccountRequest> accountRequests = new ArrayList<AccountRequest>();
		
		try {
			logger.debug("View all account requests");
			
			Connection conn = DbConnector.getInstance().getConnection();
			String sql = "SELECT id, customer_id, name, balance, status, time_requested, time_updated, employee_id FROM bank.account_requests";
			
			logger.debug("using statement", sql);
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				AccountRequest tempAccountRequest = new AccountRequest();
				tempAccountRequest.setId(rs.getInt("id"));
				tempAccountRequest.setCustomerID(rs.getInt("customer_id"));
				tempAccountRequest.setName(rs.getString("name"));
				tempAccountRequest.setBalance(rs.getDouble("balance"));
				tempAccountRequest.setStatus(rs.getString("status"));
				tempAccountRequest.setTimeRequested(rs.getTimestamp("time_requested"));
				tempAccountRequest.setTimeUpdated(rs.getTimestamp("time_updated"));
				tempAccountRequest.setEmployeeID(rs.getInt("employee_id"));
				
				accountRequests.add(tempAccountRequest);
			}
		} catch (SQLException e) {
			logger.error("Unable to perform DB query", e);
			throw e;
		}
		
		logger.debug("Returning account requests query: ", accountRequests);
		return accountRequests;
	}
	
	// Update pending registration - Approved
	public boolean requestApproved(int requestId, int employeeId) throws Exception{
		int approved = 0;
		int inserted = 0;
		Connection conn = null;
		try {
			
			logger.debug("Employee "+employeeId+" to approve account "+requestId);
			
			conn = DbConnector.getInstance().getConnection();
			conn.setAutoCommit(false);
			
			// first - query starting balance amount
			String sql1 = "SELECT customer_id, name, balance FROM bank.account_requests WHERE id=?";
			logger.debug("using statement", sql1);
			
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			
			pstmt1.setInt(1, requestId);
			
			ResultSet rs = pstmt1.executeQuery();
			conn.commit(); // execute the query as the balance is needed later
			
			Account newAccount = new Account();
			if(rs.next()) {
				
				newAccount.setCustomerId(rs.getInt("customer_id"));
				newAccount.setName(rs.getString("name"));
				newAccount.setBalance(rs.getDouble("balance"));
			}
			// second - change status to approved and hold off on commit
			String sql2 = "UPDATE bank.account_requests SET status = 'Approved', time_updated = CURRENT_TIMESTAMP, employee_id = ? WHERE id=?";
			logger.debug("using statement", sql2);
			
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			
			pstmt2.setInt(1, employeeId);
			pstmt2.setInt(2, requestId);
			
			approved = pstmt2.executeUpdate();
			logger.debug("Deposited records: "+approved);
			
			// lastly create an appropriate account
			String sql3 = "INSERT INTO bank.accounts(customer_id, name, balance, date_created) "
							+"VALUES (?, '?', ?, CURRENT_DATE)";
			logger.debug("using statement", sql2);
			
			PreparedStatement pstmt3 = conn.prepareStatement(sql3);
			
			pstmt3.setInt(1, newAccount.getCustomerId());
			pstmt3.setString(2, newAccount.getName());
			pstmt3.setDouble(3, newAccount.getBalance());
			
			
			inserted = pstmt3.executeUpdate();
			logger.debug("Inserted transaction: "+inserted);
			conn.commit();
		} catch (SQLException e1) {
			logger.error("Unable to approve account from bank.account_requests into bank.account", e1);
			try {
				if (conn!=null) {
				conn.rollback();
				}
			} catch (SQLException e11) {
				logger.error("Unable to rollback commit");
			}
		} catch (Exception e2) {
			logger.error("Unable to approve account from bank.account_requests into bank.account", e2);
			try {
				if (conn!=null) {
				conn.rollback();
				}
			} catch (SQLException e22) {
				logger.error("Unable to rollback commit");
			}
		}
		logger.debug("Returning results", inserted!=0 && approved!=0);
		
		return (inserted!=0 && approved!=0);
	}
	// Update pending registration - Denied
	public boolean requestDenied(int requestId, int employeeId) throws Exception{
		int denied = 0;
		try {
			
			logger.debug("Employee "+employeeId+" to deny account "+requestId);
			Connection conn = DbConnector.getInstance().getConnection();
			
			String sql = "UPDATE bank.account_requests SET status = 'Denied', time_updated = CURRENT_TIMESTAMP, employee_id = ? WHERE id=?";
			logger.debug("using statement", sql);
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, employeeId);
			pstmt.setInt(2, requestId);
			
			denied = pstmt.executeUpdate();
			
			logger.debug("Total request denied: "+denied);
		} catch (SQLException e) {
			logger.error("Unable to deposit into bank.account", e);
		} catch (Exception e1) {
			logger.error("Unable to deposit into bank.account", e1);
		}
		logger.debug("Returning results", denied!=0);
		
		return (denied!=0);
	}
	
	// Display all transactions - method
	public ArrayList<Transaction> allTransactions() throws Exception{
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		
		try {
			logger.debug("Employee view all transactions");
			
			Connection conn = DbConnector.getInstance().getConnection();
			String sql = "SELECT id, account_id, customer_id, type, amount, time FROM bank.transactions";
			
			logger.debug("using statement", sql);
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Transaction tempTransaction = new Transaction();
				tempTransaction.setId(rs.getInt("id"));
				tempTransaction.setAccountID(rs.getInt("account_id"));
				tempTransaction.setCustomerID(rs.getInt("customer_id"));
				tempTransaction.setType(rs.getString("type"));
				tempTransaction.setAmount(rs.getDouble("amount"));
				tempTransaction.setTime(rs.getTimestamp("time"));
				
				transactions.add(tempTransaction);
			}
		} catch (SQLException e) {
			logger.error("Unable to perform DB query", e);
			throw e;
		}
		
		logger.debug("Returning transaction results: ", transactions);
		return transactions;
	}
	
	// All registered Customers - method
	public ArrayList<Customer> allCustomers() throws Exception{
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		try {
			logger.debug("Employee view all transactions");
			
			Connection conn = DbConnector.getInstance().getConnection();
			String sql = "SELECT id, first_name, last_name, email, password, phone, join_date FROM bank.transactions";
			
			logger.debug("using statement", sql);
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Customer tempCustomer = new Customer();
				tempCustomer.setId(rs.getInt("id"));
				tempCustomer.setFirstName(rs.getString("first_name"));
				tempCustomer.setLastName(rs.getString("last_name"));
				tempCustomer.setEmail(rs.getString("email"));
				tempCustomer.setPassword(rs.getString("password"));
				tempCustomer.setPhone(rs.getString("phone"));
				tempCustomer.setJoinDate(rs.getDate("join_date"));
				
				customers.add(tempCustomer);
			}
		} catch (SQLException e) {
			logger.error("Unable to perform DB query", e);
			throw e;
		}
		
		logger.debug("Returning customer list: ", customers);
		return customers;
	}	
		
	
}
