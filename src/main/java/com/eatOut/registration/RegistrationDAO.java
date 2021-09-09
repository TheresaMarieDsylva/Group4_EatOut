package com.eatOut.registration;

import com.eatOut.database.IDatabaseConnection;
import com.eatOut.database.IStoredProcedure;
import com.eatOut.database.MySQLConnection;
import com.eatOut.database.StoredProcedure;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class RegistrationDAO implements IRegistrationDAO {
    IDatabaseConnection iDatabaseConnection = MySQLConnection.instance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement statement = null;

    @Override
    public Boolean checkAdminDBIfUserExists(String email) {
        Boolean value= false;
        try {
            connection = iDatabaseConnection.getDBConnection();
            String userExists = "SELECT * FROM admin WHERE admin_email = '" + email + "'";
            preparedStatement = connection.prepareStatement(userExists);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                value = true;
            }
            else{
                value = false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }

    @Override
    public Boolean createAdminDBUser(String email, String adminType, String phoneNum, String password, String city, String country, String dateTime) {
        Boolean value = false;
        try {
            connection = iDatabaseConnection.getDBConnection();
            String insertAdminUser = "INSERT INTO admin VALUES ('"+email+"','"+adminType+"','"+phoneNum+"','"+password+"','NULL', '"+city+"','"+country+"','"+dateTime+"')";
            statement = connection.createStatement();
            int count = statement.executeUpdate(insertAdminUser);
            if(count>=1){
                value = false;
            }
            else{
                value = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }

    @Override
    public Boolean checkRestaurantDBIfUserExists(String email) throws Exception {
        Map<String, Object> record = new HashMap<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("restaurantIfExists(?)");
            storedProcedure.setParameter(1, email);
            record = storedProcedure.getTableRecord();
        } catch (Exception ex) {
            throw new Exception("Not able to find restaurant user");
        }
        return checkRecordIfNull(record);
    }

    @Override
    public Boolean createRestaurantUserInDB(String restaurantName, String phoneNumber, String fullAddress, String city, String country, String email, String password, String restaurantStatus, String dateTime) throws Exception {
        int row=0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("restaurantRegister(?,?,?,?,?,?,?,?,?)");
            storedProcedure.setParameter(1, restaurantName);
            storedProcedure.setParameter(2, phoneNumber);
            storedProcedure.setParameter(3, fullAddress);
            storedProcedure.setParameter(4, city);
            storedProcedure.setParameter(5, country);
            storedProcedure.setParameter(6, email);
            storedProcedure.setParameter(7, password);
            storedProcedure.setParameter(8, restaurantStatus);
            storedProcedure.setParameter(9, dateTime);
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Not able to create restaurant user");
        }
        return findValueByRowCount(row);
    }

    @Override
    public Boolean checkCustomerDBIfUserExists(String email) throws Exception {
        Map<String, Object> record = new HashMap<>();
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("customerIfExists(?)");
            storedProcedure.setParameter(1, email);
            record = storedProcedure.getTableRecord();
        } catch (Exception ex) {
            throw new Exception("Not able to find customer user");
        }
        return checkRecordIfNull(record);
    }

    @Override
    public Boolean createCustomerUserInDB(String firstName, String lastName, String phoneNumber, String city, String country, String email, String password, String dateTime) throws Exception {
        int row=0;
        try {
            IStoredProcedure storedProcedure = new StoredProcedure("customerRegister(?,?,?,?,?,?,?,?)");
            storedProcedure.setParameter(1, firstName);
            storedProcedure.setParameter(2, lastName);
            storedProcedure.setParameter(3, phoneNumber);
            storedProcedure.setParameter(4, city);
            storedProcedure.setParameter(5, country);
            storedProcedure.setParameter(6, email);
            storedProcedure.setParameter(7, password);
            storedProcedure.setParameter(8, dateTime);
            row = storedProcedure.executeWithResult();
        } catch (Exception ex) {
            throw new Exception("Not able to create customer user");
        }
        return findValueByRowCount(row);
    }

    Boolean checkRecordIfNull(Map<String, Object> record) {
        Boolean value;
        if (record == null || record.size() == 0 ) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

    Boolean findValueByRowCount(int row) {
        Boolean value;
        if (row > 0) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }
}
