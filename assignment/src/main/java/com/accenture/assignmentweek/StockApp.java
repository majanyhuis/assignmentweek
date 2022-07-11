package com.accenture.assignmentweek;

import com.accenture.assignmentweek.commandos.Commando;
import com.accenture.assignmentweek.commandos.DeleteCommando;
import com.accenture.assignmentweek.commandos.ExitCommando;
import com.accenture.assignmentweek.commandos.ImportCommando;
import com.accenture.assignmentweek.connector.Connector;
import com.accenture.assignmentweek.database.StockRepository;
//import com.sun.jdi.connect.Connector;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class StockApp {

    public void run() throws SQLException, FileNotFoundException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Stock Application!");

        Connection connection = new Connector().getConnection();
        StockRepository stockRepository = new StockRepository(connection);

        // neue Kommandos!
        Commando exitCommando = new ExitCommando();
        Commando importCommando = new ImportCommando(stockRepository, scanner);
        Commando deleteCommando = new DeleteCommando(stockRepository);

        // Liste mit den Kommandos
        ArrayList<Commando> commandos = new ArrayList<>();
        commandos.add(exitCommando);
        commandos.add(importCommando);
        commandos.add(deleteCommando);

        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("Choose from the following commands: exit, import");
            String userInput = scanner.nextLine();

            for (Commando commando : commandos) {
                if (commando.shouldExecute(userInput)) {
                    commando.execute();
                }
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException, SQLException {
        StockApp stockApp = new StockApp();
        stockApp.run();

        Scanner input = new Scanner(new File("STOCKDATA:txt"));

        while (input.hasNext()) {

            String test = input.nextLine();
            System.out.println(test);

        }
    }

    public SimpleDateFormat prepareDate (String dateFromFile) {

        String[] split2 = dateFromFile.split("\\.");

        String day = split2[0];
        String month = split2[1];
        String year = split2[2];

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String dateNewFormat = "20" + year + "-" + month + "-" + day;
        try {
            simpleDateFormat.parse(dateNewFormat);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return simpleDateFormat;

    }

}
