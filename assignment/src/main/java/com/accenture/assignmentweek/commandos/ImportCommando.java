package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.Stock;
import com.accenture.assignmentweek.database.StockRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class ImportCommando implements Commando{

    private StockRepository stockRepository;
    private Scanner scanner;

    LocalDate simpleDateFormat;

    public ImportCommando (StockRepository stockRepository, Scanner scanner) {
        this.stockRepository = stockRepository;
        this.scanner = scanner;

    }

    @Override
    public void execute() throws FileNotFoundException {

        Scanner input = new Scanner(new File("C:\\Users\\maja.nyhuis\\OneDrive - Accenture\\Documents\\Jump Start\\Assignment\\STOCK_DATA_3.csv"));

        String ignoreLine = input.nextLine();

        while (input.hasNext()) {

            String test = input.nextLine(); // #Variable TEST umbenennen!!!!
            System.out.println(test);

            String[] split = test.split(";");

            Stock stock = new Stock();
            stock.setCompanyName(split[0]);

            // update price format:
            String priceFromFile = split[1];
            priceFromFile = priceFromFile.replace("€", "");
            priceFromFile= priceFromFile.replace(",",".");

            stock.setPrice(priceFromFile);
            // update price format end
            // -> Methode schreiben!!!


            // update date format:      Date Format atm: dd.mm.yy -> yyyy-mm-dd

            String dateFromFile = split[2];
            stock.setDate(simpleDateFormat);

            String[] split2 = dateFromFile.split("\\.");

            String day = split2[0];
            String month = split2[1];
            String year = split2[2];

            String dateNewFormat = "20" + year + "-" + month + "-" + day;

            LocalDate localDate = LocalDate.parse(dateNewFormat);
            stock.setDate(localDate);
            // update date format end.

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
