package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.entities.Stock;
import com.accenture.assignmentweek.repositories.StockRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class GapCommando implements Commando {

    private StockRepository stockRepository;
    private Scanner scanner;

    public GapCommando(StockRepository stockRepository, Scanner scanner) {
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
            stockRepository.maxStock(stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        double maxPrice = stock.getPrice();

        try {
            stockRepository.minStock(stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        double minPrice = stock.getPrice();

        double priceGap = maxPrice - minPrice;
        stock.setPrice(priceGap);

        System.out.println("The difference between the highest and lowest price for the entered ID is: " + stock.getPrice() + " €.");
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "gap".equalsIgnoreCase(commandoName);
    }
}
