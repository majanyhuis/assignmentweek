package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.Stock;
import com.accenture.assignmentweek.database.StockRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ImportCommando implements Commando{

    private StockRepository stockRepository;
    private Scanner scanner;

    public ImportCommando (StockRepository stockRepository, Scanner scanner) {
        this.stockRepository = stockRepository;
        this.scanner = scanner;
    }

    @Override
    public void execute() throws FileNotFoundException {

        Scanner input = new Scanner(new File("C:\\Users\\maja.nyhuis\\OneDrive - Accenture\\Documents\\Jump Start\\Assignment\\STOCK_DATA_3.csv"));

        String ignoreLine = input.nextLine();

        while (input.hasNext()) {

            String test = input.nextLine();
            System.out.println(test);

            String[] split = test.split(";");

            Stock stock = new Stock();
            stock.setCompanyName(split[0]);
            stock.setPrice(split[1]);
            stock.setDate(split[2]);
            stock.setIndustryName(split[3]);

            // zur Kontrolle:
            // kann später gelöscht werden!!!
            System.out.println(stock.getCompanyName() + stock.getPrice() + stock.getDate() + stock.getIndustryName());

            stockRepository.importStocks(stock);

        }
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "import".equalsIgnoreCase(commandoName);
    }
}
