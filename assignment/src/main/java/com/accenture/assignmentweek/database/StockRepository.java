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
            //Tabelle INDUSTRY
            String sql = "insert into industries (industry) values (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, stock.getIndustryName());
            preparedStatement.executeUpdate();

            // get id von der Industry
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            int idIndustry = generatedKeys.getInt(1);

            // Tabelle COMPANIES
            sql = "insert into companies (companyname, idindustry) values (?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, stock.getCompanyName());
            preparedStatement.setInt(2, idIndustry);
            preparedStatement.executeUpdate();

            // get id von der Company
            ResultSet generatedKeys2 = preparedStatement.getGeneratedKeys();
            generatedKeys2.next();
            int idcompany = generatedKeys2.getInt(1);

            sql = "insert into stocks (price, date, idcompany) values (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, stock.getPrice());
            preparedStatement.setDate(2, Date.valueOf(stock.getDate()));
            preparedStatement.setInt(3, idcompany);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteALL () throws SQLException {
        String sql = "DELETE FROM stocks";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();

        sql = "DELETE FROM companies";
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();

        sql = "DELETE FROM industries";
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();
    }
}
