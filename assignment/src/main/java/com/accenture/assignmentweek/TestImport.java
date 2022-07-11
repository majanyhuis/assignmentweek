package com.accenture.assignmentweek;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestImport {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner input = new Scanner(new File("C:\\Users\\maja.nyhuis\\OneDrive - Accenture\\Documents\\Jump Start\\Assignment\\Test1234.txt"));

        while (input.hasNext()) {

            String test = input.nextLine();
            System.out.println(test);

            String[] split = test.split(";");

            Stock stock = new Stock();
            stock.companyName = split[0];
            stock.price = split[1];
            stock.date = split[2];
            stock.industryName = split[3];

            System.out.println(stock.companyName + stock.price + stock.date + stock.industryName);
            //System.out.println(stock);
        }
    }

}
