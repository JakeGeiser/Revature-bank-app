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
	
	
	
	
	//// show methods for printing options
	// login - initial homepage options
	private static void showLoginOptions() {
		System.out.println();
		System.out.println("Bank Log In Options");
		System.out.println("====================");
		System.out.println("1. User Log In");
		System.out.println("2. Register New User");
		System.out.println("3. Employee Log In");
		System.out.println("4. Exit Application");
	}
	// show customer options once logged in
	private static void showCustomerOptions() {
		System.out.println();
		System.out.println("User Options");
		System.out.println("===========================");
		System.out.println("1. Select Account Portal");
		System.out.println("2. Account Balance Transfer");
		System.out.println("3. Log Out");
	}
	// show customer account options in account portal
	private static void showAccountOptions() {
		System.out.println();
		System.out.println("Account Options");
		System.out.println("===============================");
		System.out.println("1. Display Account Balance");
		System.out.println("2. Display Account Transactions");
		System.out.println("3. Deposit");
		System.out.println("4. Withdraw");
		System.out.println("5. Return to User Options Page");
	}
	// show employee options once logged in
	private static void showEmployeeOptions() {
		System.out.println();
		System.out.println("Employee Options");
		System.out.println("===============================");
		System.out.println("1. Display Customers");
		System.out.println("2. Show Customer Accounts by ID");
		System.out.println("3. Show All Transactions");
		System.out.println("4. Enter Account Requests Portal");
		System.out.println("5. Log Out");
	}
	// show all account request options in Account Requests Portal
	private static void showAccountRequestOptions() {
		System.out.println();
		System.out.println("Account Request Options");
		System.out.println("===============================");
		System.out.println("1. Approve Account Request");
		System.out.println("2. Deny Account Request");
		System.out.println("3. Show All Account Requests");
		System.out.println("4. Show All Pending Account Requests");
		System.out.println("5. Return to Employee Options");	
	}
}
