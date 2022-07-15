package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.repositories.StockRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class ExportCommando implements Commando{

    private StockRepository stockRepository;
    private Scanner scanner;

    public ExportCommando(StockRepository stockRepository, Scanner scanner) {
        this.stockRepository = stockRepository;
        this.scanner = scanner;
    }

    @Override
    public void execute() throws FileNotFoundException {

        System.out.println("Please enter the File-Path of the file you want to export the data to.");
        String csvFilePath = scanner.nextLine();
        csvFilePath = csvFilePath.replace("\"", "");

        try {
            stockRepository.exportAllDataFromDb(csvFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Your Data has been exported to the File with File-Path: " + csvFilePath);

    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "export".equalsIgnoreCase(commandoName);
    }
}
