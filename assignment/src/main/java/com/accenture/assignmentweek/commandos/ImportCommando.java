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

    public void execute() throws FileNotFoundException {

        Scanner input = new Scanner(new File("C:\\Users\\maja.nyhuis\\OneDrive - Accenture\\Documents\\Jump Start\\Assignment\\STOCK_DATA_3.csv"));
                input.nextLine();

        while (input.hasNext()) {

            String line = input.nextLine();
            System.out.println(line);
            String[] split = line.split(";");

            Stock stock = new Stock();
            stock.setCompanyName(split[0]);

            // update price format:
            String priceFromFile = split[1];
            priceFromFile = priceFromFile.replace("€", "");
            priceFromFile= priceFromFile.replace(",",".");

            stock.setPrice(Double.parseDouble(priceFromFile));
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

            stockRepository.importStocks(stock);
        }
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "import".equalsIgnoreCase(commandoName);
    }
}
