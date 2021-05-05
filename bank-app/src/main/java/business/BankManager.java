package business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.BankDao;
import exceptions.ItemNotFoundException;
import model.Customer;

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
	public boolean requestAccount(int customerId, String accountName, double balance) {
		logger.debug("Revieved Account request , customerId, accountName, balance");
		return dao.requestAccount(customerId, accountName, balance);
	}
	/**
	 * @param 
	 */
	// verify if user doesn't already exist and insert if available
	
	//// Employee
	// verify employee account - employee login method
	

}
