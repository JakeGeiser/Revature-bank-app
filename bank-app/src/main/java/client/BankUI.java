package client;

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
		} catch (ItemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// method to handle user input and handle interactions with BankManger
	private static void run(Scanner input) throws ItemNotFoundException, Exception{
		
	}

}
