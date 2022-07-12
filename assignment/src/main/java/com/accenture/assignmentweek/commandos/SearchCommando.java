package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.database.StockRepository;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class SearchCommando implements Commando{

    private StockRepository stockRepository;
    private Scanner scanner;

    public SearchCommando (StockRepository stockRepository, Scanner scanner) {
        this.stockRepository = stockRepository;
        this.scanner = scanner;
    }



    @Override
    public void execute() {

        try {
            stockRepository.searchCompany(scanner);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "search".equalsIgnoreCase(commandoName);
    }
}
