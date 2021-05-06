package interfaces;

import business.BankManager;

public interface UIRequirements {
	
	void registrationPortal(BankManager manager);
	void customerPortal(int CustomerId, BankManager manager);
	void employeePortal(int EmployeeId, BankManager manager);	
	
}
