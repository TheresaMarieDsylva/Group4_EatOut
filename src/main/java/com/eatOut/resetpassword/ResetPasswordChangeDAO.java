package com.eatOut.resetpassword;

import com.eatOut.database.IDatabaseConnection;
import com.eatOut.database.MySQLConnection;
import java.sql.*;

public class ResetPasswordChangeDAO implements IResetPasswordChangeDAO{
    IDatabaseConnection iDatabaseConnection = MySQLConnection.instance();
    Connection connection = null;
    Statement statement = null;

    @Override
    public Boolean writeNewPassword(String username, String password) {
        Boolean value= false;
        try {
            connection = iDatabaseConnection.getDBConnection();
            String updatePassword = "UPDATE customer SET  customer_login_password = '" + password + "' WHERE customer_login_email_id = '" + username + "'";
            statement = connection.createStatement();
            int count = statement.executeUpdate(updatePassword);
            if(count>=1){
                value = true;
            }
            else{
                value = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection();
        }
        return value;
    }
        private void closeConnection(){
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }

}
