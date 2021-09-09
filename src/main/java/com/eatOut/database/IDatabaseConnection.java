package com.eatOut.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDatabaseConnection {

	public Connection getDBConnection() throws SQLException;

}
