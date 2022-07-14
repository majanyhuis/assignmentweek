package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.entities.Stock;
import com.accenture.assignmentweek.repositories.StockRepository;
import com.accenture.assignmentweek.services.ShowService;

import java.sql.SQLException;
import java.util.Scanner;

public class ShowCommando implements Commando {

    private Scanner scanner;
    private StockRepository stockRepository;

    public ShowCommando(StockRepository stockRepository, Scanner scanner) {
        this.scanner = scanner;
        this.stockRepository = stockRepository;
    }

    @Override
    public void execute() {

        Stock stock = new Stock();

        System.out.println("Please enter the company ID");
        String nextString = scanner.nextLine();
        stock.setCompanyID(Integer.parseInt(nextString));

        try {
            stockRepository.showID(stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "show".equalsIgnoreCase(commandoName);
    }
}
