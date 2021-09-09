package com.eatOut.customerHomepage;


import com.eatOut.database.IDatabaseConnection;
import com.eatOut.database.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerMembershipDAO implements ICustomerMembershipDAO{
    IDatabaseConnection iDatabaseConnection = MySQLConnection.instance();
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Statement statement = null;

    @Override
    public Boolean addCustomerToMembership(int tempCusID, String membershipName) {
        Boolean response= false;
        try {
            connection = iDatabaseConnection.getDBConnection();
            String alreadyExists = "SELECT * FROM customer_wallet_default where customer_id = '"+tempCusID+"'";
            preparedStatement = connection.prepareStatement(alreadyExists);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String mName = resultSet.getString("membership").toLowerCase();
                if(mName.matches(membershipName)){
                    response = false;
                }
                else{
                    response = true;
                }
            }
            if(response){
                String createBookingEntry = "UPDATE customer_wallet set membership='"+membershipName+"' where customer_id='"+tempCusID+"'";
                statement = connection.createStatement();
                int count = statement.executeUpdate(createBookingEntry);
                if(count>=1){
                    response = true;
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }

        return response;
    }

    @Override
    public List<CustomerMembership> displayMembershipDetails() {
        List<CustomerMembership> list=new ArrayList<CustomerMembership>();
        try {
            connection = iDatabaseConnection.getDBConnection();
            String displayMembership = "SELECT * FROM membership where membership_status !='INACTIVE'";
            preparedStatement = connection.prepareStatement(displayMembership);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CustomerMembership customerMembership = new CustomerMembership();
                customerMembership.setMembershipName(resultSet.getString("membership_name"));
                customerMembership.setDiningPercent(resultSet.getString("dining_percent"));
                customerMembership.setTakeawayPercent(resultSet.getString("takeaway_percent"));
                list.add(customerMembership);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return list;
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
