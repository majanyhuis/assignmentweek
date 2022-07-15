package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.repositories.StockRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class SearchCommando implements Commando {

    private StockRepository stockRepository;
    private Scanner scanner;

    public SearchCommando(StockRepository stockRepository, Scanner scanner) {
        this.stockRepository = stockRepository;
        this.scanner = scanner;
    }

    @Override
    public void execute() {

        System.out.println("Type a letter: ");
        String searchInput = scanner.nextLine();
        searchInput = searchInput + "%";

        try {
            stockRepository.searchCompany(searchInput);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "search".equalsIgnoreCase(commandoName);
    }
}
