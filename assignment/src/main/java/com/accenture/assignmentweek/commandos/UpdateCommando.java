package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.entities.Stock;
import com.accenture.assignmentweek.repositories.StockRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class UpdateCommando implements Commando {

    private StockRepository stockRepository;
    private Scanner scanner;

    public UpdateCommando(StockRepository stockRepository, Scanner scanner) {
        this.stockRepository = stockRepository;
        this.scanner = scanner;
    }

    @Override
    public void execute() {

        Stock stock = new Stock();

        System.out.println("Please enter a company ID.");
        String nextString = scanner.nextLine();
        stock.setCompanyID(Integer.parseInt(nextString));

        System.out.println("Please enter the new industry.");
        nextString = scanner.nextLine();
        stock.setIndustryName(nextString);

        try {
            stockRepository.updateIndustry(stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("The industry of the Company with ID " + stock.getCompanyID() + " has been updated to " + stock.getIndustryName() + ".");

    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "update".equalsIgnoreCase(commandoName);
    }
}
