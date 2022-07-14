package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.entities.Stock;
import com.accenture.assignmentweek.repositories.StockRepository;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class MaxCommando implements Commando {

    private StockRepository stockRepository;
    private Scanner scanner;

    public MaxCommando(StockRepository stockRepository, Scanner scanner) {
        this.stockRepository = stockRepository;
        this.scanner = scanner;
    }

    @Override
    public void execute() throws FileNotFoundException {

        Stock stock = new Stock();

        System.out.println("Please enter a company ID.");
        String nextString = scanner.nextLine();
        stock.setCompanyID(Integer.parseInt(nextString));

        try {
            stockRepository.maxStock(stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Highest Price for the entered ID is: " + stock.getPrice() + " €");

    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "max".equalsIgnoreCase(commandoName);
    }
}
