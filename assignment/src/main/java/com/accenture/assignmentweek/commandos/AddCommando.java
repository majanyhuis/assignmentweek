package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.Stock;
import com.accenture.assignmentweek.database.StockRepository;

import java.io.FileNotFoundException;
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
    public void execute() throws FileNotFoundException {

        Stock stock = new Stock();

        System.out.println("You want to enter a new stock. First type in the Company ID.");
        String nextString = scanner.nextLine();
        stock.setIndustryID(Integer.parseInt(nextString));

        System.out.println("now Type in the date in format 'YYYY-DD-MM'");
        nextString = scanner.nextLine();
        stock.setDate(LocalDate.parse(nextString));

        System.out.println("Last enter the price in format 'XXXX.XX'"); // vielelicht eher enter price und wenn nicht das format, dann sagen in welchem??
        nextString = scanner.nextLine();
        stock.setPrice(String.valueOf(Double.parseDouble(nextString)));

    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "add".equalsIgnoreCase(commandoName);
    }
}
