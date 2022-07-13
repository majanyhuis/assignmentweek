package com.accenture.assignmentweek.database;

import com.accenture.assignmentweek.Stock;

import java.sql.*;
import java.util.Scanner;

public class StockRepository {

    private Connection connection;

    public StockRepository(Connection connection) {
        this.connection = connection;
    }

    public void importStocks(Stock stock) {
        try {
            //Tabelle INDUSTRY
            // Erstellen und initialisieren der Variablen idIndustry, für die Schleife und für später...
            int idIndustry = 0; // muss die Variable hier wirklich initialisiert werden?

            // Abfrage, ob die Industry schon in der Tabelle drin ist, falls ja count > 0; falls nein count = 0
            String sql = "select count(*) as cnt from industries where industry = ? ";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, stock.getIndustryName());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("cnt");

            System.out.println("Industry Count" + count); // löschen, nur zur Kontrolle!!!

            if (count > 0) {
                // gib mir die ID von der INDUSTRY!!!
                sql = "SELECT * FROM industries WHERE industry = (?)";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, stock.getIndustryName());

                ResultSet generatedKeys = preparedStatement.executeQuery();
                generatedKeys.next();
                idIndustry = generatedKeys.getInt(1);
            } else {
                // count ist 0 -> neue Industry wird angelegt ...
                sql = "insert into industries (industry) values (?)";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, stock.getIndustryName());
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.next();
                idIndustry = generatedKeys.getInt(1);
            }

            // Tabelle COMPANIES
            int idCompany = 0;

            // Abfrage, ob die Industry schon in der Tabelle drin ist, falls ja count > 0; falls nein count 0
            sql = "select count(*) as cnt from companies where company = ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, stock.getCompanyName());

            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            count = resultSet.getInt("cnt");

            System.out.println("Company Count" + count); // löschen, nur zur Kontrolle!!!

            if (count > 0) {
                // gib mir die ID von der COMPANY!!!
                sql = "SELECT * FROM companies WHERE company = (?)";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, stock.getCompanyName());

                ResultSet generatedKeys = preparedStatement.executeQuery();
                generatedKeys.next();
                idCompany = generatedKeys.getInt(1);
            } else {
                // count ist 0 -> neue Company wird angelegt ...
                sql = "insert into companies (company, idindustry) values (?, ?)";
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, stock.getCompanyName());
                preparedStatement.setInt(2, idIndustry);
                preparedStatement.executeUpdate();

                ResultSet generatedKeys2 = preparedStatement.getGeneratedKeys();
                generatedKeys2.next();
                idCompany = generatedKeys2.getInt(1);
            }

            sql = "insert into stocks (price, date, idcompany) values (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, stock.getPrice());
            preparedStatement.setDate(2, Date.valueOf(stock.getDate()));
            preparedStatement.setInt(3, idCompany);
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

    public void truncateALL () throws SQLException {
        String sql = "TRUNCATE TABLE stocks";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();

        sql = "TRUNCATE TABLE companies";
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();

        sql = "TRUNCATE TABLE industries";
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.execute();
    }

    public void searchCompany (Scanner scanner) throws SQLException {

        System.out.println("Type a letter: ");
        String searchInput = scanner.nextLine();
        searchInput =  searchInput + "%";

        String sql = "SELECT * FROM companies WHERE company LIKE (?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, searchInput);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            int companyID = resultSet.getInt(1);
            String companyName = resultSet.getString(2);

            System.out.println("Company: " + companyName);
            System.out.println("ID: " + companyID);
        }
    }

    public void showID (Stock stock) throws SQLException {

        // hier EINMAL den Company Namen abfragen und einmal ausgeben!!! (übersichtlichkeit und so...)
        String sql = "SELECT * FROM companies WHERE idcompany = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        stock.setCompanyName(resultSet.getString(2));
        System.out.println(stock.getCompanyName());

        // Methode schreiben
        sql = "SELECT * FROM stocks WHERE idcompany = ?";
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Date date = resultSet.getDate(3);
            double price = resultSet.getDouble(2);
            int stocksid = resultSet.getInt(1);

            System.out.println("Date: " + date + " -> " + "Price: " + price + " € " + "(idStock: " + stocksid + ")" );
        }
    }

    public void addStock (Stock stock) throws SQLException {

        String sql = "insert into stocks (price, date, idcompany) values (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setDouble(1, stock.getPrice());
        preparedStatement.setDate(2, Date.valueOf(stock.getDate()));
        preparedStatement.setInt(3, stock.getCompanyID());
        preparedStatement.executeUpdate();

        System.out.println("Has been added to Database.");
    }

    public void maxStock (Stock stock) throws SQLException {

        String sql = "SELECT MAX(price) FROM stocks WHERE idcompany = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        stock.setPrice(String.valueOf(resultSet.getDouble(1)));
    }

    public void minStock (Stock stock) throws SQLException {

        String sql = "SELECT MIN(price) FROM stocks WHERE idcompany = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        stock.setPrice(String.valueOf(resultSet.getDouble(1)));
    }

    public void gapStock (Stock stock) throws SQLException {

        String sql = "SELECT MAX(price) FROM stocks WHERE idcompany = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        stock.setPrice(String.valueOf(resultSet.getDouble(1)));

        double MAX = stock.getPrice();

        sql = "SELECT MIN(price) FROM stocks WHERE idcompany = ?";
        preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, stock.getCompanyID());

        resultSet = preparedStatement.executeQuery();
        resultSet.next();

        double MIN = stock.getPrice();

        double priceGap = MAX - MIN;
        stock.setPrice(String.valueOf(priceGap));


    }

}
