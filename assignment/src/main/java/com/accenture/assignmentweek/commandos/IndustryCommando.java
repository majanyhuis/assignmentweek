package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.entities.Stock;
import com.accenture.assignmentweek.repositories.StockRepository;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class IndustryCommando implements Commando {

    private StockRepository stockRepository;
    private Scanner scanner;

    public IndustryCommando (StockRepository stockRepository, Scanner scanner) {
        this.stockRepository = stockRepository;
        this.scanner = scanner;
    }

    @Override
    public void execute() throws FileNotFoundException {

        Stock stock = new Stock();

        try {
            stockRepository.industryList(stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "industry".equalsIgnoreCase(commandoName);
    }
}
