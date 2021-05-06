package client;

import java.sql.SQLException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import business.BankManager;
import exceptions.ItemNotFoundException;
import interfaces.UIRequirements;

/**
 * User Interface for Bank Application
 * @author Jake Geiser
 */

public class BankUI implements UIRequirements { // Customer Layer
	
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
		
		logger.info("Application Stopped");
	}
	
	// method to handle user input and handle interactions with BankManger
	private static void run(Scanner input) throws ItemNotFoundException, Exception{
		
		// get instance of manager
		BankManager manager = BankManager.getInstance();
		
		int loginAction = 0;
		do {
			showLoginOptions();
			
			System.out.println("Chose option: ");
			loginAction = input.nextInt();
			input.nextLine();
			
			switch (loginAction) {
			case 1: // user login
				//TODO
			case 2: // register new user
				//TODO
			case 3: // employee login
				//TODO
			case 4: // exit application
				//TODO
			}
		}while(loginAction>0 && loginAction<4);
	}
	
	//// home page login options
	// implement customerPortal
	public void customerPortal(int customerId, BankManager manager) {
		
	}
	
	// implement registrationPortal
	public void registrationPortal(BankManager manager) {
		
	}
	
	// implement employeePortal
	public void employeePortal(int employeeId, BankManager manager) {
		
	}
	
	
	//// input checkers
	private static boolean checkEmail(String email) {
		boolean result = false;
		
		
		return result;
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
	// show customer registration options
	private static void showRegistrationOptions() {
		System.out.println();
		System.out.println("User Registration Options");
		System.out.println("===========================");
		System.out.println("1. Register New User Account");
		System.out.println("2. Return to Login");
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
