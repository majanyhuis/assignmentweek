package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.entities.Stock;
import com.accenture.assignmentweek.repositories.StockRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class MinCommando implements Commando {

    private StockRepository stockRepository;
    private Scanner scanner;

    public MinCommando(StockRepository stockRepository, Scanner scanner) {
        this.stockRepository = stockRepository;
        this.scanner = scanner;
    }

    @Override
    public void execute() {

        Stock stock = new Stock();

        System.out.println("Please enter a company ID.");
        String nextString = scanner.nextLine();
        stock.setCompanyID(Integer.parseInt(nextString));

        try {
            stockRepository.minStock(stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Lowest Price for the entered ID is: " + stock.getPrice() + " €");
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "min".equalsIgnoreCase(commandoName);
    }
}
