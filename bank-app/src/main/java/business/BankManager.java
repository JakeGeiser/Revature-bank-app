package business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao.BankDao;

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
	// register user
	
	/**
	 * @param 
	 */
	// verify if user doesn't already exist and insert if available
	
	//// Employee
	// verify employee account - employee login method
	

}
