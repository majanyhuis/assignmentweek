package com.accenture.assignmentweek.commandos;

import com.accenture.assignmentweek.entities.Stock;
import com.accenture.assignmentweek.repositories.StockRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
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

    public void execute() {

        Scanner input = null;
        try {
            input = new Scanner(new File("C:\\Users\\maja.nyhuis\\OneDrive - Accenture\\Documents\\Jump Start\\Assignment\\STOCK_DATA_3.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        input.nextLine();

        while (input.hasNext()) {

            String line = input.nextLine();
            System.out.println(line);
            String[] split = line.split(";");

            Stock stock = new Stock();
            stock.setCompanyName(split[0]);

            String priceFromFile = split[1];
            setPrice(priceFromFile, stock);

            String dateFromFile = split[2];
            setDate(dateFromFile, stock);

            stock.setIndustryName(split[3]);

            try {
                stockRepository.importStocks(stock);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setPrice(String priceFromFile, Stock stock) {
        priceFromFile = priceFromFile.replace("€", "");
        priceFromFile= priceFromFile.replace(",",".");

        stock.setPrice(Double.parseDouble(priceFromFile));
    }

    private void setDate(String dateFromFile, Stock stock) {
        stock.setDate(simpleDateFormat);

        String[] split2 = dateFromFile.split("\\.");

        String day = split2[0];
        String month = split2[1];
        String year = split2[2];

        String dateNewFormat = "20" + year + "-" + month + "-" + day;

        LocalDate localDate = LocalDate.parse(dateNewFormat);
        stock.setDate(localDate);
    }

    @Override
    public boolean shouldExecute(String commandoName) {
        return "import".equalsIgnoreCase(commandoName);
    }
}
