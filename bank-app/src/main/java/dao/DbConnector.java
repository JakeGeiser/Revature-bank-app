package dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utility.ConfigReader;

public class DbConnector {
	
	private static final Logger logger = LogManager.getLogger(DbConnector.class);
	
	private static DbConnector instance;
	private Connection conn = null;
	
	private DbConnector() throws Exception {
		
			String url = ConfigReader.getInstance().getProperty("DB_URL");
			String user = ConfigReader.getInstance().getProperty("DB_USER");
			String password = ConfigReader.getInstance().getProperty("DB_PASSWORD");
			
			this.conn = DriverManager.getConnection(url, user, password);
			
	}
	
	public static DbConnector getInstance() throws Exception {
		if(instance == null) {
			instance = new DbConnector();
		}
		return instance;
	}
	
	public Connection getConnection() {
		return conn;
	}

}
