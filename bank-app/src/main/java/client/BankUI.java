package client;

import java.sql.SQLException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exceptions.ItemNotFoundException;

/**
 * User Interface for Bank Application
 * @author Jake Geiser
 */

public class BankUI { // Customer Layer
	
	private static final Logger logger = LogManager.getLogger(BankUI.class);

	public static void main(String[] args) {
		
		logger.info("Application Started");
		
		System.out.println("Welcome to Revature Banking");
		
		Scanner input = new Scanner(System.in);
		
		try {
			run(input);
		} catch (ItemNotFoundException e1) {
			logger.debug("ItemNotFount: ", e1);
		} catch (Exception e2) {
			if (e2 instanceof SQLException) {
				logger.debug("SQL ERROR: " + e2.getLocalizedMessage());
				System.out.println("SQL ERROR: " + e2.getLocalizedMessage());
				System.exit(0);
			}
		}
	}
	
	// method to handle user input and handle interactions with BankManger
	private static void run(Scanner input) throws ItemNotFoundException, Exception{
		
	}

}
