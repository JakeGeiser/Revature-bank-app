package interfaces;

import business.BankManager;

public interface UIRequirements {
	
	void registrationPortal(BankManager manager);
	void customerPortal(int customerId, BankManager manager);
	void employeePortal(int employeeId, BankManager manager);	
	
}
