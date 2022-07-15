package com.accenture.assignmentweek.repositories;

import com.accenture.assignmentweek.entities.Stock;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class StockRepository {

    private Connection connection;

    public StockRepository(Connection connection) {
        this.connection = connection;
    }


    public void importStocks(Stock stock) throws SQLException {

        String sql = "select count(*) as cnt from industries where industry = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, stock.getIndustryName());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt("cnt");

        if (count > 0) {
            sql = "SELECT * FROM industries WHERE industry = (?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, stock.getIndustryName());

            ResultSet generatedKeys = preparedStatement.executeQuery();
            generatedKeys.next();
            stock.setIndustryID(generatedKeys.getInt(1));
        } else {
            sql = "insert into industries (industry) values (?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, stock.getIndustryName());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            stock.setIndustryID(generatedKeys.getInt(1));
        }

        sql = "select count(*) as cnt from companies where company = ? ";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, stock.getCompanyName());

        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        count = resultSet.getInt("cnt");

        if (count > 0) {
            sql = "SELECT * FROM companies WHERE company = (?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, stock.getCompanyName());

            ResultSet generatedKeys = preparedStatement.executeQuery();
            generatedKeys.next();
            stock.setCompanyID(generatedKeys.getInt(1));
        } else {
            sql = "insert into companies (company, idindustry) values (?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, stock.getCompanyName());
            preparedStatement.setInt(2, stock.getIndustryID());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            stock.setCompanyID(generatedKeys.getInt(1));
        }

        insertStockIntoDb(stock);
    }

    private void insertStockIntoDb(Stock stock) throws SQLException {
        String sql = "insert into stocks (price, date, idcompany) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setDouble(1, stock.getPrice());
        preparedStatement.setDate(2, Date.valueOf(stock.getDate()));
        preparedStatement.setInt(3, stock.getCompanyID());
        preparedStatement.executeUpdate();
    }

    public void deleteALL() throws SQLException {
        String sql = "DELETE FROM stocks";
        executeQuery(sql);

        sql = "DELETE FROM companies";
        executeQuery(sql);

        sql = "DELETE FROM industries";
        executeQuery(sql);
    }

    public void deleteAllWithAutoIncrement() throws SQLException {
        String sql = "DELETE FROM stocks";
        executeQuery(sql);

        sql = "ALTER TABLE stocks AUTO_INCREMENT = 1";
        executeQuery(sql);

        sql = "DELETE FROM companies";
        executeQuery(sql);

        sql = "ALTER TABLE companies AUTO_INCREMENT = 1";
        executeQuery(sql);

        sql = "DELETE FROM industries";
        executeQuery(sql);

        sql = "ALTER TABLE industries AUTO_INCREMENT = 1;";
        executeQuery(sql);
    }

    private void executeQuery(String sql) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();
    }


    public void searchCompany(String searchInput) throws SQLException {

        String sql = "SELECT * FROM companies WHERE company LIKE (?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, searchInput);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int companyID = resultSet.getInt(1);
            String companyName = resultSet.getString(2);

            System.out.println("Company: " + companyName);
            System.out.println("ID: " + companyID);
        }
    }

    public void showID(Stock stock) throws SQLException {

        showIdPrintCompany(stock);
        showIdPrintDateAndPrice(stock);

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
            stock.setPrice(resultSet.getDouble(2));
            stock.setIdStock(resultSet.getInt(1));

            System.out.println("Date: " + date + " -> " + "Price: " + stock.getPrice() + " € " + "(id: " + stock.getIdStock() + ")");
        }
    }


    public void addStock(Stock stock) throws SQLException {

        insertStockIntoDb(stock);

        System.out.println("Has been added to Database.");

    }

    public void maxStock(Stock stock) throws SQLException {

        String sql = "SELECT MAX(price) FROM stocks WHERE idcompany = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        stock.setPrice(resultSet.getDouble(1));
    }

    public void minStock(Stock stock) throws SQLException {

        String sql = "SELECT MIN(price) FROM stocks WHERE idcompany = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        stock.setPrice(resultSet.getDouble(1));
    }

    public void updateIndustry(Stock stock) throws SQLException {

        String sql = "SELECT * FROM industries WHERE industry = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, stock.getIndustryName());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        stock.setIndustryID(resultSet.getInt(1));

        sql = "UPDATE companies SET idindustry = ? WHERE idcompany = ?";
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getIndustryID());
        preparedStatement.setInt(2, stock.getCompanyID());
        preparedStatement.executeUpdate();
    }

    public void industryList(Stock stock) throws SQLException {

        String sql = "SELECT * FROM industries";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSetIndustry = preparedStatement.executeQuery();

        while (resultSetIndustry.next()) {

            stock.setIndustryName(resultSetIndustry.getString(2));
            stock.setIndustryID(resultSetIndustry.getInt(1));

            sql = "select count(*) as cnt from companies where idindustry = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, stock.getIndustryID());
            ResultSet resultSetCompany = preparedStatement.executeQuery();
            resultSetCompany.next();
            int count = resultSetCompany.getInt("cnt");

            System.out.println("Industry: " + stock.getIndustryName() + ", ID: " + stock.getIndustryID() + ", -> " + count);
        }
    }

    public void exportAllDataFromDb(String csvFilePath) throws IOException, SQLException {

        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(csvFilePath));

        fileWriter.write("stockname; price; price_date; industry");

        Stock stock = new Stock();


        String sql = "SELECT * FROM stocks";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            stock.setPrice(resultSet.getDouble(2));
            stock.setDateDate(resultSet.getDate(3));

            stock.setCompanyID(resultSet.getInt(4));

            sql = "SELECT * FROM companies WHERE idcompany = ?";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, stock.getCompanyID());
            ResultSet resultSetCompany = preparedStatement.executeQuery();
            resultSetCompany.next();

            stock.setCompanyName(resultSetCompany.getString(2));
            stock.setIndustryID(resultSetCompany.getInt(3));

            sql = "SELECT * FROM industries WHERE idindustry = ?";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, stock.getIndustryID());
            ResultSet resultSetIndustry = preparedStatement.executeQuery();
            resultSetIndustry.next();

            stock.setIndustryName(resultSetIndustry.getString(2));

            String s = stock.getCompanyName() + "; " + stock.getPrice() + "; " + stock.getDateDate() + "; " + stock.getIndustryName();
            fileWriter.newLine();
            fileWriter.write(s);
        }

        fileWriter.close();

    }
}
