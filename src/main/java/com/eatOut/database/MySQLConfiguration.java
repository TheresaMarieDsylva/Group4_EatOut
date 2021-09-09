package com.eatOut.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MySQLConfiguration implements IDatabaseConfiguration{

	private static Logger logger = LogManager.getLogger(MySQLConfiguration.class);

    private String dbURL;
    private String dbUser;
    private String dbPassword;
    private String dbDriverClass;


    public MySQLConfiguration() {
        getDatabaseConfig();
    }

    private void getDatabaseConfig() {
        try {
            String header = System.getenv("MYSQL_HEADER");
            String hostname = System.getenv("MYSQL_HOST");
            String port = System.getenv("MYSQL_PORT");
            String schema = System.getenv("MYSQL_SCHEMA");
            String additionalHeaders = System.getenv("MY_ADDTNL_HEADERS");
            dbURL = header + "://" + hostname + ":" + port + "/" + schema + "?" + additionalHeaders;
            dbUser = System.getenv("MYSQL_USER");
            dbPassword = System.getenv("MYSQL_PWD");
            dbDriverClass = System.getenv("MYSQL_DRIVER");
        } catch (Exception e) {
        	logger.error("Exception occured while loading MySQL configurations");
        }
    }

    public String getDbURL() {
        return dbURL;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbDriverClass() {
        return dbDriverClass;
    }
}
