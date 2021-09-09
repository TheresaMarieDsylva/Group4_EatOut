package com.eatOut.payment;

import com.eatOut.database.IDatabaseConnection;
import com.eatOut.database.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO implements  IPaymentDAO {
    IDatabaseConnection iDatabaseConnection = MySQLConnection.instance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement statement = null;


    @Override
    public List<String> getMembershipPercent(String customerID) {
        String response=null;
        List<String> memPercent = new ArrayList<>();
        try {
            connection = iDatabaseConnection.getDBConnection();
            String getMemValues = "select dining_percent, takeaway_percent from membership where membership_name=(select distinct membership from customer_wallet where customer_id='"+customerID+"')";
            preparedStatement = connection.prepareStatement(getMemValues);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                memPercent.add(resultSet.getString("dining_percent"));
                memPercent.add(resultSet.getString("takeaway_percent"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }

        return memPercent;
    }

    @Override
    public Boolean enterTransaction(String customerID, int billAmount) {
        Boolean value = false;
        try {
            connection = iDatabaseConnection.getDBConnection();
            String enterTransaction = "INSERT INTO customer_wallet_default VALUES ('"+customerID+"','"+billAmount+"','"+null+"')";
            statement = connection.createStatement();
            int count = statement.executeUpdate(enterTransaction);
            if(count>=1){
                value = true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            closeConnection();
        }
        return value;
    }


    private void closeConnection(){
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}