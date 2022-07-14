package com.accenture.assignmentweek;

import com.accenture.assignmentweek.commandos.*;
import com.accenture.assignmentweek.connector.Connector;
import com.accenture.assignmentweek.repositories.StockRepository;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class StockApp {

    public void run() throws SQLException, FileNotFoundException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Stock Application!");

        Connection connection = new Connector().getConnection();
        StockRepository stockRepository = new StockRepository(connection);

        Commando exitCommando = new ExitCommando();
        Commando importCommando = new ImportCommando(stockRepository, scanner);
        Commando deleteCommando = new DeleteCommando(stockRepository);
        Commando deleteCommandoWithAI = new DeleteCommandoWithAI(stockRepository);
        Commando searchCommando = new SearchCommando(stockRepository, scanner);
        Commando showCommando = new ShowCommando(stockRepository, scanner);
        Commando addCommando = new AddCommando(stockRepository, scanner);
        Commando maxCommando = new MaxCommando(stockRepository, scanner);
        Commando minCommando = new MinCommando(stockRepository, scanner);
        Commando gapCommando = new GapCommando(stockRepository, scanner);
        Commando updateCommando = new UpdateCommando(stockRepository, scanner);
        Commando industryCommando = new IndustryCommando(stockRepository, scanner);

        ArrayList<Commando> commandos = new ArrayList<>();
        commandos.add(exitCommando);
        commandos.add(importCommando);
        commandos.add(deleteCommando);
        commandos.add(searchCommando);
        commandos.add(showCommando);
        commandos.add(addCommando);
        commandos.add(maxCommando);
        commandos.add(minCommando);
        commandos.add(gapCommando);
        commandos.add(updateCommando);
        commandos.add(industryCommando);
        commandos.add(deleteCommandoWithAI);

        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("Choose from the following commands: exit, import, delete, delete with AI, search, show, " +
                    "add, max, min, gap, update, industry? ");
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

