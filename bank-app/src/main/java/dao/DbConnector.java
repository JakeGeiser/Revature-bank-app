package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utility.ConfigReader;

public class DbConnector {
	
	private static final Logger logger = LogManager.getLogger(DbConnector.class);
	
	private static DbConnector instance;
	private Connection conn = null;
	
	private DbConnector() throws Exception {
			
			logger.info("Establishing Connection...");
			
			String url = ConfigReader.getInstance().getProperty("DB_URL");
			String user = ConfigReader.getInstance().getProperty("DB_USER");
			String password = ConfigReader.getInstance().getProperty("DB_PASSWORD");
			
			try {
				this.conn = DriverManager.getConnection(url, user, password);
				logger.info("Connection Established!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("Unable to connect to Database!", e);
			} catch (Exception e1) {
				logger.error("ConfigReader error", e1);
			}
			
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
