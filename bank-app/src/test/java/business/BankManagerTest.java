package business;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import dao.BankDao;
import model.AccountRequest;

public class BankManagerTest {
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void allAccountRequests() {
		BankManager manager = new BankManager();
		
		BankDao daoMock = mock(BankDao.class);
		
		ArrayList<AccountRequest> mockReturnObjs = new ArrayList<AccountRequest>();
		
		try {
			when(daoMock.allAccountRequests()).thenReturn(mockReturnObjs);
			
			manager.setDao(daoMock);
			
			ArrayList<AccountRequest> accountRequests = manager.allAccountRequests();
			
			assertNotNull(accountRequests);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void withdraw() {
		BankManager manager = new BankManager();
		
		BankDao daoMock = mock(BankDao.class);
		
		int a = (int) Math.random()*20;
		int b = (int) Math.random()*20;
		double c = (double) Math.random()*400;
		try {
			when(daoMock.withdraw(a,b,c)).thenReturn(Boolean.TRUE);
			
			manager.setDao(daoMock);
			
			boolean withdrawn = manager.withdraw(a, b, c);
			
			assertTrue(withdrawn);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deposit() {
		BankManager manager = new BankManager();
		
		BankDao daoMock = mock(BankDao.class);
		
		int a = (int) Math.random()*20;
		int b = (int) Math.random()*20;
		double c = (double) Math.random()*400;
		try {
			when(daoMock.deposit(a,b,c)).thenReturn(Boolean.TRUE);
			
			manager.setDao(daoMock);
			
			boolean deposited = manager.deposit(a, b, c);
			
			assertTrue(deposited);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void transfer() {
		BankManager manager = new BankManager();
		
		BankDao daoMock = mock(BankDao.class);
		
		int a = (int) Math.random()*20;
		int b = (int) Math.random()*40;
		int c = (int) Math.random()*40;
		double d = (double) Math.random()*400;
		try {
			when(daoMock.transfer(a,b,c,d)).thenReturn(Boolean.TRUE);
			
			manager.setDao(daoMock);
			
			boolean transfered = manager.transfer(a, b, c, d);
			
			assertTrue(transfered);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

}
