package interfaces;

import java.util.Scanner;

import business.BankManager;

public interface UIRequirements {
	
	void registrationPortal(BankManager manager, Scanner input);
	void customerPortal(int customerId, BankManager manager, Scanner input);
	void employeePortal(int employeeId, BankManager manager, Scanner input);	
	
}
