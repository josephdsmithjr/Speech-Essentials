package com.speechessentials.speechessentials.Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by josephdsmithjr on 11/15/14.
 */

public class CategoryRetriever {
    Connection _connection;
    String _connectionString;
    Statement _statement;
    PreparedStatement _preparedStatement;
    ResultSet _resultSet1, _resultSet2;



    public void setConnection(){
        try {
            _connectionString = "jbc:mysql://p3nlmysql113plsk.secureserver.net:3306?" + "user=smartfingerinvoice&password=Qqyz0#33";
            _connection = DriverManager.getConnection(_connectionString);
        } catch (SQLException ex){
            System.out.println("SQLExeption: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public ArrayList<String> retrieveCategories(int businessKey){
        ArrayList<String> categoryList = new ArrayList<String>();
        setConnection();
        try {
            _statement = _connection.createStatement();
            _resultSet1 = _statement.executeQuery("select * from CategoryDim");
            _resultSet2 = _statement.executeQuery("select * from CategoryDim where CategoryDim.BusinessKey = " + businessKey);
        } catch (Exception e){

        }

        return categoryList;
    }

}
