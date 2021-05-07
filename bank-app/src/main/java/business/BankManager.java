package business;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.BankDao;
import exceptions.ItemNotFoundException;
import model.Account;
import model.AccountRequest;
import model.Customer;
import model.Employee;
import model.Transaction;

/**
 * Object Manager for Bank App
 * @author Jake Geiser
 */
public class BankManager { // Business Layer
	// create logger
	private static final Logger logger = LogManager.getLogger(BankManager.class);
	
	// make BankDao placeholder that is replace upon BankManager being initialized
	private BankDao dao;
	
	// make BankManager a singleton
	private static BankManager instance;
	private BankManager() throws Exception{ // default constructor
		// Initialize DAO singleton
		try {
			logger.debug("Initializeing BankManager with BankDao instance...");
			this.dao = BankDao.getInstance();
		} catch (Exception e) {
			logger.error("Issue creating BankDao inside BankManager", e);
		}
	}
	
	public static BankManager getInstance() throws Exception {
		if(instance == null) {
			instance = new BankManager();
		}
		return instance;
	}
	
	
	
	//// Customer
	// get Customer
	public Customer getCustomer(Customer customer) throws ItemNotFoundException, Exception{
		logger.debug("Recieved get customer info: "+customer);
		return dao.getCustomer(customer.getEmail(), customer.getPassword());
	}
	
	// insertCustomer
	public boolean insertCustomer(Customer customer) throws Exception {
		logger.debug("Recieved insert customer request: "+customer);
		return dao.insertCustomer(customer);
	}
	
	// new Account Request
	public boolean requestAccount(int customerId, String accountName, double balance) throws Exception{
		logger.debug("Revieved Account request , customerId, accountName, balance");
		return dao.requestAccount(customerId, accountName, balance);
	}
	/** get all accounts for customerId
	 * @param customerId
	 * @return ArrayList<Account>
	 * @throws ItemNotFoundException
	 * @throws Exception
	 */
	public ArrayList<Account> allAccounts(int customerId) throws ItemNotFoundException, Exception {
		logger.debug("Recieved allAccounts request for customerId: "+customerId);
		return dao.allAccounts(customerId);
	}
	
	// deposit
	public boolean deposit(int customerId, int accountId, double amount) throws ItemNotFoundException, Exception{
		logger.debug("Recieved deposit request", customerId, accountId, amount);
		return dao.deposit(customerId, accountId, amount);
	}
	
	// withdraw
	public boolean withdraw(int customerId, int accountId, double amount) throws ItemNotFoundException, Exception{
		logger.debug("Recieved withdraw request", customerId, accountId, amount);
		return dao.withdraw(customerId, accountId, amount);
	}
	
	// transfer between accounts
	public boolean transfer(int customerId, int accountId1, int accountId2, double amount) throws ItemNotFoundException, Exception{
		logger.debug("Customer "+customerId+" request to transfer $"+amount+" from account "+accountId1+" --> "+accountId2);
		return dao.transfer(customerId, accountId1, accountId2, amount);
	}
	
	// all transactions of customerId
	public ArrayList<Transaction> allTransactions(int customerId, int accountId) throws ItemNotFoundException, Exception{
		logger.debug("Customer("+customerId+") request to view all transactions of account("+accountId+")");
		return dao.allTransactions(customerId, accountId);
	}
	
	// get Account info
	public Account getAccount(int accountId) throws Exception{
		logger.debug("Recieved get account info: "+accountId);
		return dao.getAccount(accountId);
	}
	
	//// Employee
	// get employee information
	public Employee getEmployee(Employee employee) throws Exception {
		logger.debug("Requesting employee with email="+employee.getEmail()+", password="+employee.getPassword());
		return dao.getEmployee(employee.getEmail(), employee.getPassword());
	}
	
	// get all account requests
	public ArrayList<AccountRequest> allAccountRequests() throws Exception{
		logger.debug("Recieved request to view all customer account requests");
		return dao.allAccountRequests();
	}
	
	// get all pending account requests
	public ArrayList<AccountRequest> allPendingAccountRequests() throws Exception{
		logger.debug("Recieved request to view all pending customer account requests");
		return dao.allPendingAccountRequests();
	}
	
	// approve account request
	public boolean approveRequest(int requestId, int employeeId) throws ItemNotFoundException, Exception{
		try {
			logger.debug("Employee "+employeeId+" requesting to approve account "+requestId);
			return dao.requestApproved(requestId, employeeId);
		} catch (ItemNotFoundException e) {
			logger.warn("Item entered is not correct", e);
			return false;
		}
	}

	// deny account request
	public boolean denyRequest(int requestId, int employeeId) throws ItemNotFoundException, Exception{
		try {
			logger.debug("Employee "+employeeId+" requesting to deny account "+requestId);
			return dao.requestDenied(requestId, employeeId);
		} catch (ItemNotFoundException e) {
			logger.warn("Item entered is not correct", e);
			return false;
		}
	}
	
	// get all transactions
	public ArrayList<Transaction> allTransactions() throws Exception{
		logger.debug("Employee request to view all transactions");
		return dao.allTransactions();
	}
	
	// view all registered customers
	public ArrayList<Customer> allCustomers() throws Exception{
		logger.debug("Employee request to view all transactions");
		return dao.allCustomers();
	}

	

}
