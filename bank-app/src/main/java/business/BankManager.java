package business;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.BankDao;
import exceptions.ItemNotFoundException;
import model.Account;
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
		return dao.deposit(customerId, accountId, amount);
	}
	
	// transfer between accounts
	public boolean transfer(int customerId, int accountId1, int accountId2, double amount) throws ItemNotFoundException, Exception{
		logger.debug("Customer "+customerId+" request to transfer $"+amount+" from account "+accountId1+" --> "+accountId2);
		return dao.transfer(customerId, accountId1, accountId2, amount);
	}
	
	// all transactions of customerId
	public ArrayList<Transaction> allTransactions(int customerId) throws ItemNotFoundException, Exception{
		logger.debug("Customer request to view all transactions: "+customerId);
		return dao.allTransactions(customerId);
	}
	
	
	//// Employee
	// get employee information
	public Employee getEmployee(Employee employee) throws Exception {
		logger.debug("Requesting employee with email="+employee.getEmail()+", password="+employee.getPassword());
		return dao.getEmployee(employee.getEmail(), employee.getPassword());
	}
	

}
