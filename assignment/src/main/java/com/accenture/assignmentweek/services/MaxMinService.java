package com.accenture.assignmentweek.services;

import com.accenture.assignmentweek.entities.Stock;

import java.sql.*;

public class MaxMinService {

    private Connection connection;

    public MaxMinService(Connection connection) {
        this.connection = connection;
    }

    public void getMax(Stock stock) throws SQLException {
        String sql = "SELECT MAX(price) FROM stocks WHERE idcompany = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        stock.setPrice(resultSet.getDouble(1));
    }

    public void getMin(Stock stock) throws SQLException {

        String sql = "SELECT MIN(price) FROM stocks WHERE idcompany = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        stock.setPrice(resultSet.getDouble(1));
    }


}
