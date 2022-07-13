package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.Stock;
import com.accenture.assignmentweek.database.StockRepository;

import java.io.FileNotFoundException;
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

        try {
            stockRepository.showID(scanner);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "show".equalsIgnoreCase(commandoName);
    }
}
