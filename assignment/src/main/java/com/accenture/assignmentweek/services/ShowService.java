package com.accenture.assignmentweek.services;

import com.accenture.assignmentweek.entities.Stock;

import java.sql.*;

public class ShowService {

    private Connection connection;

    public ShowService(Connection connection) {
        this.connection = connection;
    }

    public void showIdPrintCompany(Stock stock) throws SQLException {
        String sql = "SELECT * FROM companies WHERE idcompany = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        stock.setCompanyName(resultSet.getString(2));
        System.out.println("The last ten prices for company " + stock.getCompanyName() + " (ID: " + stock.getCompanyID() + "):");
    }

    public void showIdPrintDateAndPrice(Stock stock) throws SQLException {
        String sql = "SELECT * FROM stocks WHERE idcompany = ? ORDER BY date DESC";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());
        ResultSet resultSet = preparedStatement.executeQuery();


        for (int i = 0; i < 10 && resultSet.next(); i++) {
            Date date = resultSet.getDate(3);
            double price = resultSet.getDouble(2);
            int stocksid = resultSet.getInt(1);

            System.out.println("Date: " + date + " -> " + "Price: " + price + " € " + "(id: " + stocksid + ")");
        }
    }
}
