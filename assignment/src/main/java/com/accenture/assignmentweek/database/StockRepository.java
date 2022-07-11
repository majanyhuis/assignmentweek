package com.accenture.assignmentweek.database;

import com.accenture.assignmentweek.Stock;

import java.sql.*;

public class StockRepository {

    private Connection connection;

    public StockRepository(Connection connection) {
        this.connection = connection;
    }

    public void importStocks(Stock stock) {
        try {
            String sql = "insert into companies (companyname) values (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, stock.getCompanyName());
            preparedStatement.executeUpdate();

            sql = "insert into industries (industry) values (?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, stock.getIndustryName());
            preparedStatement.executeUpdate();

            sql = "insert into stocks (price, date) values(?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, stock.getPrice());
            preparedStatement.setDate(2, Date.valueOf(stock.getDate()));
            preparedStatement.executeUpdate();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteALL () throws SQLException {
        String sql = "DELETE FROM companies";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();

        sql = "DELETE FROM industries";
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();

        sql = "DELETE FROM stocks";
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();
    }
}
