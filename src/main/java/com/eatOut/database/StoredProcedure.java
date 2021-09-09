package com.eatOut.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StoredProcedure implements IStoredProcedure {

    IDatabaseConnection databaseConnection = MySQLConnection.instance();

    private Connection connection;
    private CallableStatement statement;


    public StoredProcedure(String procedure) throws SQLException {
        connection = databaseConnection.getDBConnection();
        statement = connection.prepareCall("{call " + procedure + "}");
    }

    @Override
    public void setParameter(int index, String value) throws SQLException {
        statement.setString(index, value);
    }

    @Override
    public void setParameter(int index, int value) throws SQLException {
        statement.setInt(index, value);
    }

    @Override
    public void setParameter(int index, boolean value) throws SQLException {
        statement.setBoolean(index, value);
    }

    @Override
    public void setParameter(int index, double value) throws SQLException {
        statement.setDouble(index, value);
    }

    @Override
    public void setParameter(int index, Time value) throws SQLException {
        statement.setTime(index, value);
    }

    @Override
    public void setParameter(int index, Timestamp value) throws SQLException {
        statement.setTimestamp(index, value);
    }

    @Override
    public void executeProcedure() throws SQLException {
        try {
			statement.execute();
		} catch (Exception e) {
			throw new SQLException(e);
		}finally {
			close();
		}
    }

    @Override
    public List<Map<String, Object>> executeAndFetchTable() throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
			boolean result = statement.execute();
			if (result) {
			    ResultSet resultSet = statement.getResultSet();
			    ResultSetMetaData tableData = resultSet.getMetaData();
			    int colIndex = tableData.getColumnCount();
			    list = new ArrayList<>();
			    while (resultSet.next()) {
			        Map<String, Object> map = new HashMap<>();
			        for (int i = 1; i <= colIndex; i++) {
			            String columnName = tableData.getColumnName(i);
			            Object columnValue = resultSet.getObject(columnName);
			            map.put(columnName, columnValue);
			        }
			        list.add(map);
			    }
			}
		} catch (Exception e) {
			throw new SQLException(e);
		}finally {
			close();
		}
        return list;
    }

    @Override
    public Map<String, Object> getTableRecord() throws SQLException {
        Map<String, Object> map = new HashMap<>();
        try {
			boolean res = statement.execute();
			if (res) {
			    ResultSet resultSet = statement.getResultSet();
			    ResultSetMetaData tableData = resultSet.getMetaData();
			    while (resultSet.next()) {
			        map = new HashMap<>();
			        String columnName = tableData.getColumnName(1);
			        Object columnValue = resultSet.getObject(columnName);
			        map.put(columnName, columnValue);
			    }
			}
		} catch (Exception e) {
			throw new SQLException(e);
		}finally {
			close();
		}
        return map;
    }

    @Override
    public int executeWithResult() throws SQLException {
    	int row = 0;
        try {
			boolean res = statement.execute();
			if (res) {
			    ResultSet resultSet = statement.getResultSet();
			    while (resultSet.next()) {
			        row = resultSet.getInt("row_affected");
			    }
			}
		} catch (Exception e) {
			throw new SQLException(e);
		}finally{
			close();
		}
        return row;
    }

    private void close() throws SQLException {
        statement.close();
        connection.close();
    }
}
