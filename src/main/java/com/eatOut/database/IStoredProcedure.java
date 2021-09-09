package com.eatOut.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface IStoredProcedure {

    void setParameter(int index, String value) throws SQLException;

    void setParameter(int index, int value) throws SQLException;

    void setParameter(int index, boolean value) throws SQLException;

    void setParameter(int index, double value) throws SQLException;

    void setParameter(int index, Time value) throws SQLException;

    void setParameter(int index, Timestamp value) throws SQLException;

    void executeProcedure() throws SQLException;

    List<Map<String, Object>> executeAndFetchTable() throws SQLException;

    Map<String, Object> getTableRecord() throws SQLException;

    int executeWithResult() throws SQLException;

}
