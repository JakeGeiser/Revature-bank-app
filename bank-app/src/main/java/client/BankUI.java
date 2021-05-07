package client;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import business.BankManager;
import exceptions.ItemNotFoundException;
import model.Customer;
import model.Employee;

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
		
		logger.info("Application Stopped");
		System.out.println("Application closed");
		input.close();
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
				Customer tempCustomer = new Customer();
				Customer currentCustomer = new Customer();
				
				do {
					System.out.println("Enter email: ");
					String email = input.nextLine();
					
					if(isValidEmail(email)) { // if valid email then ask for password
						tempCustomer.setEmail(email);
						System.out.println("Enter password: ");
						tempCustomer.setPassword(input.nextLine());
						currentCustomer = manager.getCustomer(tempCustomer);
						if(currentCustomer.getId() < 1) {
							System.out.println("Invalid email or password...");
						}
					}
					else {
						System.out.println("Invalid Email");
					}
					
				}while(currentCustomer.getId() < 1);
				
				customerPortal(currentCustomer.getId(), manager, input);
				
				break;
			case 2: // register new user
				registrationPortal(manager, input);
				
				break;
			case 3: // employee login
				Employee tempEmployee = new Employee();
				Employee currentEmployee = new Employee();
				
				do {
					System.out.println("Enter email: ");
					String email = input.nextLine();
					
					if(isValidEmail(email)) { // if valid email then ask for password
						tempEmployee.setEmail(email);
						System.out.println("Enter password: ");
						tempEmployee.setPassword(input.nextLine());
						currentEmployee = manager.getEmployee(tempEmployee);
						if(currentEmployee.getId() < 1) {
							System.out.println("Invalid email or password...");
						}
					}
					else {
						System.out.println("Invalid Email");
					}
					
				}while(currentEmployee.getId() < 1);
				
				employeePortal(currentEmployee.getId(), manager, input);
				break;
			case 4: // exit application
				System.out.println("Exiting Application...");
				loginAction = 6;
				break;
			default:
				System.out.println("Invalid input");
				break;
			}
		}while(loginAction>0 && loginAction<5);
	}
	
	/* home page login options
	 *  //// implement customerPortal
	 *  //// implement accountPortal
	 *  //// implement registrationPortal
	 *  //// implement employeePortal
	 */
	
	//// implement customerPortal
	private static void customerPortal(int customerId, BankManager manager, Scanner input) {
		int customerOption = 0;
		do {
			customerOption = 0;
			showCustomerOptions();
			System.out.println("Select Option: ");
			customerOption = input.nextInt();
			input.nextLine();
			
			switch(customerOption) {
			case 1: // Select Account Portal
				accountPortal(customerId, manager, input);
				break;
			case 2: // Account Balance Transfer
				accountTransfer(customerId, manager, input);
				break;
			case 3: // Request New Account with Balance
				// TODO
				break;
			case 4: // Log Out of account
				customerOption = 5;
			default:
				// TODO
				break;
			}

		} while(customerOption > 0 && customerOption < 5);
		
	}
	
	private static void accountTransfer(int customerId, BankManager manager, Scanner input) {
		boolean loopOption = true;
		
		do {
			System.out.println("Select account you wish to transfer from: ");
			int accountId1 = input.nextInt();
			input.nextLine();
			System.out.println("Select account you wish to transfer to: ");
			int accountId2 = input.nextInt();
			input.nextLine();
			
			System.out.println("Enter amount you with to transfer from"
						+" Account("+accountId1+") to Account("+accountId2+"): ");
			double amount = input.nextDouble();
			
			try {
				
				double balance1 = accountBalance(accountId1, manager);
				
				if (isValidTransaction(balance1,amount)) {
					manager.transfer(customerId, accountId1, accountId2, amount);
					loopOption = false;
				}
			} catch (Exception e) {
				logger.debug("Transfer ERROR: ", e);
			}
			input.nextLine();
			// check if the amount will move into red
			
		}while(loopOption);
		
	}
	
	//// implement accountPortal
	private static void accountPortal(int accountId, BankManager manager, Scanner input) {
		
	}
	
	private static double accountBalance(int accountId, BankManager manager) throws Exception {
		
		return manager.getAccount(accountId).getBalance();
		
	}
	
	//// implement registrationPortal
	// registration
	private static void registrationPortal(BankManager manager, Scanner input) {
		int registrationOption = 0;
		do {
			showRegistrationOptions();
			
			System.out.println("Choose option: ");
			registrationOption = input.nextInt();
			input.nextLine();
			
			switch(registrationOption) {
			case 1: // register account
				boolean whileCondition = true;
				do {
					whileCondition = isValidCustomer(manager, input);
				}while(whileCondition);

				break;
			case 2: // exit application
				System.out.println("Exiting Application...");
				registrationOption = 4;
				break;
			default:
				System.out.println("Invalid input");
				continue;
			}
		}while(registrationOption>0 && registrationOption<3);
	}
	// check if customer is valid
	private static boolean isValidCustomer(BankManager manager, Scanner input) {
		int counter = 0;
		boolean whileCondition = true;
		Customer tempCustomer = new Customer();
		
		System.out.println("Enter name, email, phone, and password");
		System.out.println("First Name: ");
		String firstName = input.nextLine();
		if(isValidName(firstName)) {
			tempCustomer.setFirstName(firstName);
			counter++;
		}
		else {
			System.out.println("Invalid Name");
		}
		
		if(counter > 0) {
			System.out.println("Last Name: ");
			String lastName = input.nextLine();
			if(isValidName(lastName)) {
				tempCustomer.setLastName(lastName);
				counter++;
			}
			else {
				System.out.println("Invalid Name");
			}
		}
		
		if(counter > 1) {
			System.out.println("Email: ");
			String email = input.nextLine();
			if(isValidEmail(email)) {
				tempCustomer.setEmail(email);
				counter++;
			}
			else {
				System.out.println("Invalid Email");
			}
		}
		
		if(counter > 2) {
			System.out.println("Phone Number: ");
			String phone = input.nextLine();
			if(isValidPhoneNumber(phone)) {
				tempCustomer.setPhone(extractInt(phone));
				counter++;
			}
			else {
				System.out.println("Invalid Phone Number");
			}
		}
		
		if(counter > 3) {
			System.out.println("Password: ");
			tempCustomer.setPassword(input.nextLine());
		}
		
		try {
			whileCondition = (manager.insertCustomer(tempCustomer)==false);
		} catch (ItemNotFoundException e) {
			logger.error("Invalid Input ERROR: ", e);
			System.out.println("Email already in use");
			whileCondition = true;
		} catch (Exception e) {
			logger.error("Invalid Input ERROR: ", e);
			System.out.println("Email already in use");
			whileCondition = true;
		}
			
		
		return whileCondition;
	}

	//// implement employeePortal
	private static void employeePortal(int employeeId, BankManager manager, Scanner input) {
		int employeeOption = 0;
	}
	
	
	
	//// input checkers
	// email checker
	private static boolean isValidEmail(String email) {
		// taken from https://www.geeksforgeeks.org/check-email-address-valid-not-java/
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
                  
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}
	
	// name checker
	public static boolean isValidName(String str) {
		// taken from https://www.geeksforgeeks.org/check-if-a-string-contains-only-alphabets-in-java-using-regex/
	    return ((!str.equals(""))
	            && (str != null)
	            && (str.matches("^[a-zA-Z]*$")));
	}
	
	// phone number checker
	private static boolean isValidPhoneNumber(String phoneNumber) {
		// taken from https://www.javaprogramto.com/2020/04/java-phone-number-validation.html
		// validate phone numbers of format "1234567890"
		if (phoneNumber.matches("\\d{10}"))
			return true;
		// validating phone number with -, . or spaces
		else if (phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
		    return true;
		// validating phone number with extension length from 3 to 5
		else if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
		    return true;
		// validating phone number where area code is in braces ()
		else if (phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
			return true;
		  // Validation for India numbers
		else if (phoneNumber.matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}"))
			return true;
		else if (phoneNumber.matches("\\(\\d{5}\\)-\\d{3}-\\d{3}"))
			return true;
	
		else if (phoneNumber.matches("\\(\\d{4}\\)-\\d{3}-\\d{3}"))
			return true;
		  // return false if nothing matches the input
		else
			return false;
	}
	
	// turn phone number into only digits
    private static String extractInt(String str)
    {
        // Replacing every non-digit number with nothing("")
        str = str.replaceAll("[^\\d]", "");
        // Remove extra spaces from the outside of string
        str = str.trim();
  
        return str;
    }
	
    // transaction checker (no negative balances)
    // A - B > 0 -> true or false
    private static boolean isValidTransaction(double A, double B) {
    	if(A-B > 0) {
    		return true;
    	}
    	else {
    		return false;
    	}
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
		System.out.println("===================================");
		System.out.println("1. Select Account Portal");
		System.out.println("2. Account Balance Transfer");
		System.out.println("3. Request New Account with Balance");
		System.out.println("4. Log Out");
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
