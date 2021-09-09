package com.eatOut.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MySQLConnection implements IDatabaseConnection{
	
	private static Logger logger = LogManager.getLogger(MySQLConnection.class);
	
	private static IDatabaseConnection uniqueInstance=null;
	
	String dbURL;
	String dbUser;
	String dbPassword;
	String dbDriverClass;
	
	private MySQLConnection() {
		IDatabaseConfiguration databaseConfiguration=new MySQLConfiguration();
		dbURL=databaseConfiguration.getDbURL();
		dbUser=databaseConfiguration.getDbUser();
		dbPassword=databaseConfiguration.getDbPassword();
		dbDriverClass=databaseConfiguration.getDbDriverClass();
	}
	
	public static IDatabaseConnection instance() {
		if(null==uniqueInstance) {
			return new MySQLConnection();
		}
		return uniqueInstance;
	}
	
	@Override
	public Connection getDBConnection() throws SQLException {
		Connection connection=null;
		try {
			Class.forName(dbDriverClass);
			connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
			
		} catch (SQLException e) {
			logger.error("Exception while creating database connection",e);
			throw e;
		} catch (ClassNotFoundException e) {
			logger.error("Exception: MySql connector jar missing",e);
		}
        return connection;
	}

}
