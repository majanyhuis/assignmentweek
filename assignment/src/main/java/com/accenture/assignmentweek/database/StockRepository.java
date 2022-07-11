package com.accenture.assignmentweek.database;

import com.accenture.assignmentweek.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
            //preparedStatement.setString(2, stock.getDate());
            preparedStatement.executeUpdate();

            sql = "insert into industries (industry) values (?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, stock.getIndustryName());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteALL () throws SQLException {
        String sql = "DELETE FROM companies" + "Delete FROM industries" + "Delete FROM stocks";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();
    }

}
