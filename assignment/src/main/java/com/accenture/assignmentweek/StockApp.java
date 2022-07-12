package com.accenture.assignmentweek;

import com.accenture.assignmentweek.commandos.*;
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
        Commando truncateCommando = new TruncateCommando(stockRepository);
        Commando searchCommando = new SearchCommando(stockRepository, scanner);
        Commando showCommando = new ShowCommando();

        // Liste mit den Kommandos
        ArrayList<Commando> commandos = new ArrayList<>();
        commandos.add(exitCommando);
        commandos.add(importCommando);
        commandos.add(deleteCommando);
        commandos.add(truncateCommando);
        commandos.add(searchCommando);
        commandos.add(showCommando);

        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("Choose from the following commands: exit, import, delete? Do not use: truncate.");
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
        }
    }

