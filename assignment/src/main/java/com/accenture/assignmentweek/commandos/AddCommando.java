package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.entities.Stock;
import com.accenture.assignmentweek.repositories.StockRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class AddCommando implements Commando {

    private StockRepository stockRepository;
    private Scanner scanner;

    public AddCommando(StockRepository stockRepository, Scanner scanner) {
        this.scanner = scanner;
        this.stockRepository = stockRepository;
    }


    @Override
    public void execute() {

        Stock stock = new Stock();

        System.out.println("You want to enter a new stock. First type in the Company ID.");
        String nextString = scanner.nextLine();
        stock.setCompanyID(Integer.parseInt(nextString));

        System.out.println("Now Type in the date in format 'YYYY-MM-DD'");
        nextString = scanner.nextLine();
        stock.setDate(LocalDate.parse(nextString));

        System.out.println("Last enter the price in format 'XXXX.XX'");
        nextString = scanner.nextLine();
        stock.setPrice(Double.parseDouble(nextString));

        try {
            stockRepository.addStock(stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "add".equalsIgnoreCase(commandoName);
    }
}
